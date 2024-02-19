package com.ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ra.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @GeneratedValue(strategy = GenerationType.UUID)
    private String serialNumber;
    private Long userId;
    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String note;
    private Date createdAt;
    private Date receivedAt;

    @OneToMany(mappedBy = "orders")
    @JsonIgnore
    private List<OrderDetail> listOrderDetail;


}
