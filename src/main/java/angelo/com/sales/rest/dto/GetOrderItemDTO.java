package angelo.com.sales.rest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class GetOrderItemDTO {

   private String description;
   private Double price;
   private Integer quantity;
}
