package models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "tbl_filter_values")
public class FilterValues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean isDelete;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @Column(length = 500, nullable = false)
    private String name;
}
