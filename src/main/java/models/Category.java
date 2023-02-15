package models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 500, nullable = false)
    private String name;

    @Column(length = 200)
    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    private boolean isDelete;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public Category() {
        products = new ArrayList<>();
    }

    public Category(String name, String image, Date dateCreate, boolean isDelete) {
        super();
        this.name = name;
        this.image = image;
        this.dateCreate = dateCreate;
        this.isDelete = isDelete;
    }

}
