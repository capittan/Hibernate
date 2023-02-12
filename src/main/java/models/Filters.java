package models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tbl_filters")
public class Filters implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "filterNames_id", nullable = false)
    private FilterNames filterName;

    @Id
    @ManyToOne
    @JoinColumn(name = "filterValues_id", nullable = false)
    private FilterValues filterValues;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
