package models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 500, nullable = false)
    private String name;

    @Column(length = 200)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product")
    private List<Basket> baskets;

    public Product() {
        productImages = new ArrayList<>();
        baskets = new ArrayList<>();
    }

    public Product(String name, String description, Date date, boolean isDelete, Category category) {
        super();
        this.name = name;
        this.description = description;
        this.date = date;
        this.isDelete = isDelete;
        this.category = category;
    }
}
