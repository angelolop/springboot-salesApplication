package angelo.com.sales.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_item_db")
public class OrderItem implements Serializable {

   @Serial
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @JsonIgnore
   @ManyToOne
   @JoinColumn(name = "order_id")
   private Order order;

   @ManyToOne
   @JoinColumn(name = "product_id")
   private Product product;

   private Integer quantity;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      OrderItem orderItem = (OrderItem) o;
      return id != null && Objects.equals(id, orderItem.id);
   }

   @Override
   public int hashCode() {
      return getClass().hashCode();
   }
}
