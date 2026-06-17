package service;

import client.CustomerClient;
import client.ProductClient;
import dto.CustomerDTO;
import dto.OrderDTO;
import entity.OrderEntity;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    private OrderRepository orderRepository;

    @Inject
    @RestClient
    private CustomerClient customerClient;

    @Inject
    @RestClient
    private ProductClient productClient;

    public List<OrderDTO> getAllOrders(){
        List<OrderDTO> orders = new ArrayList<>();

        orderRepository.findAll().stream().forEach(item-> {
            orders.add(mapEntityToDTO(item));
        });

        return orders;
    }

    public void saveNewOrder(OrderDTO orderDTO){
        CustomerDTO customerDTO = customerClient.findCustomerById(orderDTO.getCustomerId());

        if(customerDTO.getName().equals(orderDTO.getCustomerName()) && productClient.getCustomerById(orderDTO.getProductId()) != null){
            orderRepository.persist(mapDTOtoEntity(orderDTO));
            Log.info("Order saved");
        } else {
            throw new NotFoundException();
        }
    }

    private OrderDTO mapEntityToDTO(OrderEntity order){
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setOrderValue(order.getOrderValue());
        orderDTO.setCustomerId(order.getCustomerId());
        orderDTO.setProductId(order.getProductId());
        orderDTO.setCustomerName(order.getCustomerName());

        return orderDTO;
    }

    private OrderEntity mapDTOtoEntity(OrderDTO orderDTO){
        OrderEntity order = new OrderEntity();

        order.setCustomerId(orderDTO.getCustomerId());
        order.setOrderValue(orderDTO.getOrderValue());
        order.setProductId(orderDTO.getProductId());
        order.setCustomerName(orderDTO.getCustomerName());

        return order;
    }
}
