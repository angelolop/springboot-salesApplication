package angelo.com.sales.rest.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class GetOrderDTO {

   private Long id;
   private String cpf;
   private String name;
   private Double total;
   private Date date;
   private String status;
   private List<GetOrderItemDTO> getItemsDto;

}
