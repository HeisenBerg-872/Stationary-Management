package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.PrintOrder;
import com.example.demo.entity.User;
import org.springframework.core.io.Resource;

public interface PrintOrderService {
    PrintOrder createOrder(User user, MultipartFile file, String printType, int copies);
    List<PrintOrder> getOrdersByUser(User user);
    void deleteOrder(Long orderId);
    Resource loadFileAsResource(String fileName);

}
