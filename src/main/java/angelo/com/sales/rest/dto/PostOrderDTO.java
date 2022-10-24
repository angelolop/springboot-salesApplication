package angelo.com.sales.rest.dto;

import angelo.com.sales.validation.NotEmptyList;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PostOrderDTO {

   @NotNull(message = "{field.client-id.necessary}")
   private Long client;

   @NotNull(message = "{field.order-total.necessary}")
   private Double total;

   @NotEmptyList(message = "{field.order-items.necessary}")
   private List<PostOrderItemDTO> postItemsDto;

}
