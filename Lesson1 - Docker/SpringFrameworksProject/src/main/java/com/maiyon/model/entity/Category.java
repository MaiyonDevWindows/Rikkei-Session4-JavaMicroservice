package com.maiyon.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maiyon.model.entity.enums.ActiveStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@DynamicInsert
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    @Column(nullable = false)
    private String categoryName;
    private String description;
    @Enumerated(EnumType.ORDINAL)
    @ColumnDefault(value = "true")
    @Column(name = "category_status", columnDefinition = "BIT(1)")
    private ActiveStatus categoryStatus;
    // Category - Product: 1 - N.
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Product> products;
    @PrePersist
    public void prePersist(){
        this.categoryStatus = (this.categoryStatus == null || this.categoryStatus == ActiveStatus.ACTIVE) ?
                ActiveStatus.ACTIVE : ActiveStatus.INACTIVE;
    }
}