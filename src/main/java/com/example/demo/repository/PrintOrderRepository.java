package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.PrintOrder;
import com.example.demo.entity.User;

public interface PrintOrderRepository extends JpaRepository<PrintOrder, Long> {
    List<PrintOrder> findByUser(User user);
    PrintOrder findByOrderId(String orderId);
}