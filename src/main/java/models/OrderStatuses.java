package models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "tbl_order_statuses")
public class OrderStatuses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean isDelete;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @Column(length = 500, nullable = false)
    private String name;
}
