package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.PrintOrder;
import com.example.demo.entity.User;
import com.example.demo.repository.PrintOrderRepository;
import com.example.demo.service.FileStorageService;
import com.example.demo.service.PrintOrderService;

import org.springframework.core.io.Resource;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrintOrderServiceImpl implements PrintOrderService {

    public PrintOrderServiceImpl(PrintOrderRepository printOrderRepository, FileStorageService fileStorageService) {
		super();
		this.printOrderRepository = printOrderRepository;
		this.fileStorageService = fileStorageService;
	}

	private final PrintOrderRepository printOrderRepository;
    private final FileStorageService fileStorageService;
    @Override
    public List<PrintOrder> getOrdersByUser(User user) {
        return printOrderRepository.findByUser(user);
    }
    @Override
    public void deleteOrder(Long orderId) {
        printOrderRepository.deleteById(orderId);
    }
    
    @Override
    public Resource loadFileAsResource(String fileName) {
        return fileStorageService.loadFileAsResource(fileName);
    }


    @Override
    public PrintOrder createOrder(User user, MultipartFile file, String printType, int copies) {
        String savedFile = fileStorageService.storeFile(file);
        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

//        PrintOrder order = PrintOrder.builder()
//                .fileName(savedFile)
//                .printType(printType)
//                .numberOfCopies(copies)
//                .status("Pending")
//                .orderId(orderId)
//                .createdAt(LocalDateTime.now())
//                .user(user)
//                .build();
        PrintOrder order = new PrintOrder();
        order.setFileName(savedFile);
        order.setPrintType(printType);
        order.setNumberOfCopies(copies);
        order.setStatus("Pending");
        order.setOrderId(orderId);
        order.setCreatedAt(LocalDateTime.now());
        order.setUser(user);


        return printOrderRepository.save(order);
    }
}
