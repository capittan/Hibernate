package models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tbl_filter_name_groups")
@IdClass(FilterNameGroups.class)
public class FilterNameGroups implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "filterNames_id", nullable = false)
    private FilterNames filterNames;

    @Id
    @ManyToOne
    @JoinColumn(name = "filterValues_id", nullable = false)
    private FilterValues filterValues;
}

