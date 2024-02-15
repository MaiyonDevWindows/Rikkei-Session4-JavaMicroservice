package org.maiyon.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.maiyon.model.enums.ActiveStatus;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(unique = true, nullable = false)
    private String sku;
    @Column(nullable = false)
    private String productName;
    private String description;
    @Column(name = "unit_price", columnDefinition = "DECIMAL(10, 2) DEFAULT(1)")
    private Double unitPrice = 1D;
    @Column(name = "stock_quantity", columnDefinition = "INT DEFAULT(0)")
    private Integer stockQuantity = 0;
    private String image;
    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    @UpdateTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date updatedAt;
    @Enumerated(EnumType.ORDINAL)
    @ColumnDefault(value = "true")
    @Column(name = "status", columnDefinition = "BIT(1)")
    private ActiveStatus status;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;
    @PrePersist
    private void prePersist(){
        if(this.sku == null){
            this.sku = UUID.randomUUID().toString();
        }
        this.status = (this.status == null || this.status == ActiveStatus.ACTIVE) ?
                ActiveStatus.ACTIVE : ActiveStatus.INACTIVE;
    }
    @PreUpdate
    private void preUpdate(){
        if(this.sku == null){
            this.sku = UUID.randomUUID().toString();
        }
    }
}
