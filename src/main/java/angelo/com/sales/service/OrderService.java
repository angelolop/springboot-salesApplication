package angelo.com.sales.service;

import angelo.com.sales.domain.entity.OrderItem;
import angelo.com.sales.domain.enums.OrderStatus;
import angelo.com.sales.rest.dto.GetOrderDTO;
import angelo.com.sales.rest.dto.GetOrderItemDTO;
import angelo.com.sales.rest.dto.PostOrderDTO;
import angelo.com.sales.domain.entity.Order;
import angelo.com.sales.rest.dto.PostOrderItemDTO;

import java.util.List;
import java.util.Optional;

public interface OrderService {

   Order saveDtoOrderToOrder(PostOrderDTO postOrderDto);

   Optional<Order> findOrderById(Long id);

   void updateStatus(Long id, OrderStatus orderStatus);

   GetOrderDTO convertToDtoOrder(Order order);

   List<GetOrderItemDTO> convertOrderItemsToDto(List<OrderItem> items);

   List<OrderItem> convertToOrderItems(Order order, List<PostOrderItemDTO> items);

}
