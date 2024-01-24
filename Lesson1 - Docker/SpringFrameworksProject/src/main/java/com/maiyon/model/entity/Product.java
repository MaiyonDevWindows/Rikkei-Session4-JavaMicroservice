package com.maiyon.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "products")
@DynamicInsert
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    // Cần dùng prePersist để UUID có thể tự sinh.
    // Cần dùng preUpdate để UUID có thể tự sinh.
    private String sku;
    @Column(nullable = false)
    private String productName;
    private String description;
    @Column(name = "unit_price", columnDefinition = "DECIMAL(10,2) DEFAULT(1)")
    private Double unitPrice;
    @Column(name = "stock_quantity", columnDefinition = "INT DEFAULT(0)")
    private Integer stockQuantity;
    private String image;
    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    @UpdateTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date updatedAt;
    // Category - Product: 1 - N.
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    // Cần phải in ra để người dùng biết nếu CategoryId không có sẵn.
    private Category category;
    // Product - User => ShoppingCart: N - N.
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ShoppingCart> shoppingCarts;
    // Product - User => WishList: N - N.
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<WishList> wishLists;
    @PrePersist
    private void prePersist(){
        if(this.sku == null ){
            this.sku = UUID.randomUUID().toString();
        }
    }
    @PreUpdate
    private void preUpdate(){
        if(this.sku == null ){
            this.sku = UUID.randomUUID().toString();
        }
    }
}