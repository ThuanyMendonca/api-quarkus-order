package controller;

import dto.OrderDTO;
import io.quarkus.logging.Log;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.OrderService;

import java.util.List;

@Path("/api/order")
public class OrderController {
    @Inject
    private OrderService orderService;

    @GET
    @RolesAllowed({ "user", "admin" })
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderDTO> getAllOrders(){
        return orderService.getAllOrders();
    }

    @POST
    @Transactional
    public Response saveNewOrder(OrderDTO order){
        try {
            orderService.saveNewOrder(order);
            return Response.ok().build();
        } catch (Exception e){
            Log.error(e);
            return Response.serverError().build();
        }
    }
}
