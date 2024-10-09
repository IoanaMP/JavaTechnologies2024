/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package info.uaic.miss.lab1.GraphServlet;
import info.uaic.miss.lab1.Helper.RequestLog;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
/**
 *
 * @author ioana
 */
@WebServlet("/adjacency-matrix")
public class AdjacencyMatrixServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String numVerticesParam = request.getParameter("numVertices");
        String numEdgesParam = request.getParameter("numEdges");
        String[] someMultiValues = request.getParameterValues("multiSelectParam");

        StringBuilder params = new StringBuilder("numVertices = " + numVerticesParam + " numEdges = " + numEdgesParam);
        if (someMultiValues != null) {
            params.append(" multiSelectParam = ");
            for (String value : someMultiValues) {
                params.append(value).append(" ");
            }
        }
        RequestLog.logRequest(request, getServletContext(), params.toString());

        if (numVerticesParam == null || numVerticesParam.isEmpty() ||
            numEdgesParam == null || numEdgesParam.isEmpty()) {
            response.getWriter().println(generateErrorHTML("Error: Please provide valid inputs for vertices and edges."));
            return;
        }

        int numVertices;
        int numEdges;
        try {
            numVertices = Integer.parseInt(numVerticesParam);
            numEdges = Integer.parseInt(numEdgesParam);
        } catch (NumberFormatException e) {
            response.getWriter().println(generateErrorHTML("Error: Invalid input. Please enter valid numbers for vertices and edges."));
            return;
        }

        if (numVertices <= 0 || numEdges < 0) {
            response.getWriter().println(generateErrorHTML("Error: Number of vertices must be greater than 0, and number of edges must be non-negative."));
            return;
        }

        int maxEdges = numVertices * (numVertices - 1) / 2;
        if (numEdges > maxEdges) {
            numEdges = maxEdges;
        }

        int[][] adjacencyMatrix = new int[numVertices][numVertices];
        generateRandomGraph(adjacencyMatrix, numVertices, numEdges);

        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null) {
            if (userAgent.contains("Python") || userAgent.contains("requests")) {
            response.setContentType("text/plain");
            String matrixString = matrixToString(adjacencyMatrix);
            response.getWriter().println(matrixString);
            } else {
                String htmlResponse = generateHTML(adjacencyMatrix, numVertices);
                response.getWriter().println(htmlResponse);
            }
        }
    }

    private String generateHTML(int[][] adjacencyMatrix, int numVertices) {
        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("<head><title>Random Graph Adjacency Matrix</title></head>");
        html.append("<body>");
        html.append("<h1>Adjacency Matrix of the Random Graph</h1>");
        html.append("<table border='1' cellpadding='5'>");

        html.append("<tr>");
        html.append("<th></th>"); 
        for (int i = 0; i < numVertices; i++) {
            html.append("<th>").append(i + 1).append("</th>");
        }
        html.append("</tr>");


        for (int i = 0; i < numVertices; i++) {
            html.append("<tr>");
            html.append("<th>").append(i + 1).append("</th>"); 
            for (int j = 0; j < numVertices; j++) {
                html.append("<td>").append(adjacencyMatrix[i][j]).append("</td>");
            }
            html.append("</tr>");
        }

        html.append("</table>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    private String generateErrorHTML(String errorMessage) {
        return "<html><body><h2>" + errorMessage + "</h2></body></html>";
    }

    public static String matrixToString(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return "";
        }

        StringBuilder stringMatrix = new StringBuilder();

        for (int[] row : matrix) {
            for (int i : row) {
                stringMatrix.append(i).append(" ");
            }
            stringMatrix.append("\n");
        }

        return stringMatrix.toString();
    }

    private void generateRandomGraph(int[][] adjacencyMatrix, int numVertices, int numEdges) {

        ArrayList<int[]> possibleEdges = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                possibleEdges.add(new int[]{i, j});
            }
        }

        Collections.shuffle(possibleEdges, new Random());

        for (int i = 0; i < numEdges; i++) {
            int[] edge = possibleEdges.get(i);
            adjacencyMatrix[edge[0]][edge[1]] = 1;
            adjacencyMatrix[edge[1]][edge[0]] = 1;
        }
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
