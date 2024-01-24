package com.maiyon.model.entity;

import com.maiyon.model.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders") // Do order là 1 keyword nhạy cảm, dễ trùng.
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @GeneratedValue(strategy = GenerationType.UUID)
    private String serialNumber;
    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @Column(name = "note")
    private String orderNote;
    private String receiveName;
    private String receiveAddress;
    private String receivePhone;
    private Date createdAt;
    private Date receivedAt;
    // User - Order: 1 - N.
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}