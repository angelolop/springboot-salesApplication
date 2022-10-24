package angelo.com.sales.rest.controller;

import angelo.com.sales.domain.enums.OrderStatus;
import angelo.com.sales.rest.dto.GetOrderDTO;
import angelo.com.sales.rest.dto.PostOrderDTO;
import angelo.com.sales.domain.entity.Order;
import angelo.com.sales.rest.dto.PostStatusOrderDTO;
import angelo.com.sales.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

   @Autowired
   private OrderService orderService;

   @GetMapping("/{id}")
   public GetOrderDTO findById(Long id) {
      return orderService
              .findOrderById(id)
              .map(p -> orderService.convertToDtoOrder(p))
              .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Order not found"));
   }

   @PostMapping
   @ResponseStatus(CREATED)
   public Long save(@RequestBody @Valid PostOrderDTO postOrderDto) {
      Order order = orderService.saveDtoOrderToOrder(postOrderDto);
      return order.getId();
   }

   @PatchMapping("/{id}")
   @ResponseStatus(NO_CONTENT)
   public void updateStatus(@RequestBody PostStatusOrderDTO postStatusOrderDTO, @PathVariable Long id) {
      orderService.updateStatus(id, OrderStatus.valueOf(postStatusOrderDTO.getNewStatus()));
   }


}
