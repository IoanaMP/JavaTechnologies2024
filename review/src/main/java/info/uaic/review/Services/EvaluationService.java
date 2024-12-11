/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.Services;
import info.uaic.review.entities.EvaluationEntity;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import javax.ws.rs.core.MediaType;
/**
 *
 * @author ioana
 */
@Path("/evaluations")
public class EvaluationService {

    @Inject
    protected EntityManager entityManager;

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEvaluation(EvaluationEntity evaluation) {
        if (evaluation == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid evaluation data").build();
        }

        entityManager.persist(evaluation);
        return Response.status(Response.Status.CREATED).entity(evaluation).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEvaluation(@PathParam("id") Integer id, EvaluationEntity updatedEvaluation) {
        EvaluationEntity existingEvaluation = entityManager.find(EvaluationEntity.class, id);

        if (existingEvaluation == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Evaluation not found").build();
        }

        existingEvaluation.setActivityName(updatedEvaluation.getActivityName());
        existingEvaluation.setActivityType(updatedEvaluation.getActivityType());
        existingEvaluation.setGrade(updatedEvaluation.getGrade());
        existingEvaluation.setComment(updatedEvaluation.getComment());
        existingEvaluation.setTimestamp(LocalDateTime.now());

        entityManager.merge(existingEvaluation);
        return Response.ok(existingEvaluation).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteEvaluation(@PathParam("id") Integer id) {
        EvaluationEntity evaluation = entityManager.find(EvaluationEntity.class, id);
        if (evaluation == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Evaluation not found").build();
        }

        entityManager.remove(evaluation);
        return Response.noContent().build();
    }

    @GET
    public Response getEvaluations(@QueryParam("studentId") Integer student,
                                    @QueryParam("teacherId") Integer teacher) {
        StringBuilder queryBuilder = new StringBuilder("SELECT e FROM EvaluationEntity e WHERE 1=1");
        if (student != null) queryBuilder.append(" AND e.student_id = :studentId");
        if (teacher != null) queryBuilder.append(" AND e.teacher_id = :teacherId");

        TypedQuery<EvaluationEntity> query = entityManager.createQuery(queryBuilder.toString(), EvaluationEntity.class);
        if (student != null) query.setParameter("student", student);
        if (teacher != null) query.setParameter("teacher", teacher);

        List<EvaluationEntity> evaluations = query.getResultList();
        return Response.ok(evaluations).build();
    }

}
