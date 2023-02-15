package models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "tbl_orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean isDelete;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @ManyToOne
    @JoinColumn(name = "orderStatuses_id", nullable = false)
    private OrderStatuses orderStatuses;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
