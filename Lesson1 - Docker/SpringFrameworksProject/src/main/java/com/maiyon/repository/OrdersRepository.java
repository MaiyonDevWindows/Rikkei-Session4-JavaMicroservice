package com.maiyon.repository;

import com.maiyon.model.entity.Order;
import com.maiyon.model.entity.User;
import com.maiyon.model.entity.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByUser(User user, Pageable pageable);

    @Query(value = "SELECT o.* from orders o where serial_number LIKE CONCAT('%', ?1, '%') and user_id = ?2", nativeQuery = true)
    List<Order> findAllBySerialNumberContainingIgnoreCase(String serialNumber, Long userId);

    @Query(value = "SELECT o.* from orders o where order_status LIKE CONCAT('%', ?1, '%') and user_id = ?2", nativeQuery = true)
    List<Order> findAllByStatusOrdersContainingIgnoreCaseForUser(String status, Long userId);

    @Query(value = "SELECT o.* from orders o where order_status LIKE CONCAT('%', ?1, '%')", nativeQuery = true)
    List<Order> findAllByStatusOrdersContainingIgnoreCase(String status);

    @Query(value = "SELECT sum(total_price) from orders where created_at between ?1 and ?2", nativeQuery = true)
    Double totalPriceByTime(Date startDate, Date endDate);

    Order findByOrderIdAndUserAndOrderStatus(Long id, User user, OrderStatus orderStatus);
}