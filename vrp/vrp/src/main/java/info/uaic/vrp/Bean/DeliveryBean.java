/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Bean;

import info.uaic.vrp.Entities.ClientOrderDetails;
import info.uaic.vrp.Entities.DeliverySchedule;
import info.uaic.vrp.Entities.Product;
import info.uaic.vrp.Services.ClientService;
import info.uaic.vrp.Services.OrderService;
import info.uaic.vrp.Services.ProductService;
import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author ioana
 */
@Named("deliveryBean")
@SessionScoped
public class DeliveryBean implements Serializable {

    private List<ClientOrderDetails> orders;
    private List<DeliverySchedule> schedule;
    private ScheduleModel eventModel;
    private ScheduleEvent<?> selectedEvent;

    @Inject
    private ClientService clientService;

    @PostConstruct
    public void init() {
        orders = clientService.getAllClientOrders();
        schedule = createDeliverySchedule(LocalDateTime.now().withHour(8).withMinute(0));
        createEventModel(); 
    }
    
    private void createEventModel() {
        eventModel = new DefaultScheduleModel();
        for (DeliverySchedule delivery : schedule) {
            LocalDateTime startDate =delivery.getStartDateTime();
            LocalDateTime endDate = delivery.getArrivalDateTime();

            ScheduleEvent<?> event = DefaultScheduleEvent.builder()
                    .title("Delivery to " + delivery.getClientName())
                    .startDate(startDate)
                    .endDate(endDate)
                    .description("Distance: " + delivery.getDistance() + " | Location: (" + delivery.getX()+ ", " + delivery.getY() + ")")
                    .build();

            eventModel.addEvent(event);
        }
    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
           selectedEvent = selectEvent.getObject(); 
       }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleEvent<?> getSelectedEvent() {
        return selectedEvent; 
    }
     private double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private boolean isClientAvailable(ClientOrderDetails client, LocalDateTime arrivalDateTime) {
        return (arrivalDateTime.isEqual(client.getAvailabilityStart()) || arrivalDateTime.isAfter(client.getAvailabilityStart())) &&
               (arrivalDateTime.isEqual(client.getAvailabilityEnd()) || arrivalDateTime.isBefore(client.getAvailabilityEnd()));
    }

    private ClientOrderDetails getNextClient(int currentX, int currentY, List<ClientOrderDetails> clients, LocalDateTime currentDateTime) {
        ClientOrderDetails nearestClient = null;
        double minDistance = Double.MAX_VALUE;

        for (ClientOrderDetails client : clients) {
            double distance = calculateDistance(currentX, currentY, client.getXCoord(), client.getYCoord());

            LocalDateTime arrivalDateTime = currentDateTime.plus((long) (distance * 60), ChronoUnit.MINUTES);

            if (isClientAvailable(client, arrivalDateTime) && distance < minDistance) {
                minDistance = distance;
                nearestClient = client;
            }
        }
        return nearestClient;
    }

    private List<DeliverySchedule> createDeliverySchedule(LocalDateTime startDateTime) {
        List<DeliverySchedule> deliverySchedule = new ArrayList<>();
        int currentX = 0, currentY = 0; 
        LocalDateTime currentDateTime = startDateTime;
        List<ClientOrderDetails> remainingClients = new ArrayList<>(orders);

        while (!remainingClients.isEmpty()) {
            ClientOrderDetails nextClient = getNextClient(currentX, currentY, remainingClients, currentDateTime);

            if (nextClient == null) {
                currentDateTime = currentDateTime.plusDays(1).withHour(8).withMinute(0);
                continue;
            }

            double distance = calculateDistance(currentX, currentY, nextClient.getXCoord(), nextClient.getYCoord());
            LocalDateTime arrivalDateTime = currentDateTime.plus((long) (distance * 60), ChronoUnit.MINUTES);

            deliverySchedule.add(new DeliverySchedule(
                    nextClient.getClientName(),
                    nextClient.getClientId(),
                    currentDateTime, 
                    arrivalDateTime,
                    distance,
                    nextClient.getXCoord(),
                    nextClient.getYCoord()
            ));

            currentX = nextClient.getXCoord();
            currentY = nextClient.getYCoord();
            currentDateTime = arrivalDateTime.plusMinutes(10);
            remainingClients.remove(nextClient);
        }

        return deliverySchedule;
    }

    public List<DeliverySchedule> getSchedule() {
        return schedule;
    }
    
}