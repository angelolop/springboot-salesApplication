package angelo.com.sales.service.impl;

import angelo.com.sales.domain.enums.OrderStatus;
import angelo.com.sales.exception.OrderNotFoundException;
import angelo.com.sales.rest.dto.GetOrderDTO;
import angelo.com.sales.rest.dto.GetOrderItemDTO;
import angelo.com.sales.rest.dto.PostOrderDTO;
import angelo.com.sales.rest.dto.PostOrderItemDTO;
import angelo.com.sales.domain.entity.Client;
import angelo.com.sales.domain.entity.Order;
import angelo.com.sales.domain.entity.OrderItem;
import angelo.com.sales.domain.entity.Product;
import angelo.com.sales.exception.BusinessException;
import angelo.com.sales.domain.repository.ClientRepository;
import angelo.com.sales.domain.repository.OrderItemRepository;
import angelo.com.sales.domain.repository.OrderRepository;
import angelo.com.sales.domain.repository.ProductRepository;
import angelo.com.sales.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

   private final OrderRepository orderRepository;

   private final ClientRepository clientRepository;

   private final ProductRepository productRepository;

   private final OrderItemRepository orderItemRepository;

   @Override
   public Optional<Order> findOrderById(Long id) {
      return orderRepository.findByIdFetchItems(id);
   }

   @Override
   @Transactional
   public Order saveDtoOrderToOrder(PostOrderDTO postOrderDto) {
      Long clientId = postOrderDto.getClient();
      Client client = clientRepository.findById(clientId)
              .orElseThrow(() -> new BusinessException("Client code invalid."));

      Order order = new Order();
      order.setTotal(postOrderDto.getTotal());
      order.setOrderDate(new Date());
      order.setClient(client);
      order.setOrderStatus(OrderStatus.DONE);

      List<OrderItem> orderItems = convertToOrderItems(order, postOrderDto.getPostItemsDto());
      orderRepository.save(order);
      orderItemRepository.saveAll(orderItems);
      order.setOrderItems(orderItems);
      return order;
   }

   @Override
   @Transactional
   public void updateStatus(Long id, OrderStatus orderStatus) {
      orderRepository.findById(id)
              .map(order -> {
                 order.setOrderStatus(orderStatus);
                 return orderRepository.save(order);
              }).orElseThrow( () -> new OrderNotFoundException("Order not found"));
   }

   @Override
   public List<OrderItem> convertToOrderItems(Order order, List<PostOrderItemDTO> items) {
      if(items.isEmpty()) {
         throw new BusinessException("No items");
      }

      return items.stream()
                  .map(itemsDto -> {
                     Product product = productRepository
                                       .findById(itemsDto.getProduct())
                                       .orElseThrow(() -> new BusinessException("Product code invalid: " + itemsDto.getProduct()));

                     OrderItem orderItem = new OrderItem();
                     orderItem.setQuantity(itemsDto.getQuantity());
                     orderItem.setOrder(order);
                     orderItem.setProduct(product);
                     return orderItem;
                  }).toList();
   }

   @Override
   public GetOrderDTO convertToDtoOrder(Order order) {
      return GetOrderDTO
              .builder()
              .id(order.getId())
              .date(order.getOrderDate())
              .cpf(order.getClient().getCpf())
              .name(order.getClient().getName())
              .total(order.getTotal())
              .status(order.getOrderStatus().name())
              .getItemsDto(convertOrderItemsToDto(order.getOrderItems()))
              .build();
   }

   @Override
   public List<GetOrderItemDTO> convertOrderItemsToDto(List<OrderItem> items) {
      if(CollectionUtils.isEmpty(items)) {
         return Collections.emptyList();
      }
      return items.stream().map(item -> GetOrderItemDTO
              .builder()
              .description(item.getProduct().getDescription())
              .price(item.getProduct().getPrice())
              .quantity(item.getQuantity())
              .build()).toList();
   }
}
