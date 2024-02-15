package org.maiyon.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.maiyon.model.enums.ActiveStatus;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@DynamicInsert
@DynamicUpdate
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    @Column(nullable = false, unique = true)
    private String categoryName;
    private String description;
    @Enumerated(EnumType.ORDINAL)
    @ColumnDefault(value = "true")
    @Column(name = "active_status", columnDefinition = "BIT(1)")
    private ActiveStatus activeStatus;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Product> products = new ArrayList<>();
    public void prePersist(){
        this.activeStatus = (this.activeStatus == null || this.activeStatus == ActiveStatus.ACTIVE) ?
                ActiveStatus.ACTIVE : ActiveStatus.INACTIVE;
    }
}
