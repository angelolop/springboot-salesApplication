package angelo.com.sales.domain.entity;

import angelo.com.sales.domain.enums.OrderStatus;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_db")
public class Order implements Serializable {

   @Serial
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @ManyToOne
   @JoinColumn(name = "client_id")
   private Client client;

   private Date orderDate;

   private Double total;

   @Enumerated(EnumType.STRING)
   private OrderStatus orderStatus;

   @OneToMany(mappedBy = "order")
   private List<OrderItem> orderItems;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      Order order = (Order) o;
      return id != null && Objects.equals(id, order.id);
   }

   @Override
   public int hashCode() {
      return getClass().hashCode();
   }
}
