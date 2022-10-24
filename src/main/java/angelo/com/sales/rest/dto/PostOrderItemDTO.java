package angelo.com.sales.rest.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PostOrderItemDTO {

   private Long product;
   private Integer quantity;
}
