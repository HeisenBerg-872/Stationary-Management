package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;


import com.example.demo.entity.PrintOrder;
import com.example.demo.repository.PrintOrderRepository;
import com.example.demo.service.PrintOrderService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final PrintOrderRepository printOrderRepository;
    private final PrintOrderService printOrderService;

    
	public AdminController(PrintOrderRepository printOrderRepository, PrintOrderService printOrderService) {
		super();
		this.printOrderRepository = printOrderRepository;
		this.printOrderService = printOrderService;
	}
	@GetMapping("/orders")
    public String viewAllOrders(Model model) {
        List<PrintOrder> orders = printOrderRepository.findAll();
        model.addAttribute("orders", orders);
        return "admin_orders";
    }
    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin_dashboard";
    }

    @PostMapping("/update-status")
    public String updateOrderStatus(@RequestParam("orderId") Long orderId,
                                    @RequestParam("status") String status) {
        PrintOrder order = printOrderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            printOrderRepository.save(order);
        }
        return "redirect:/admin/orders";
    }
    @PostMapping("/admin/delete-order")
    public String deleteOrder(@RequestParam Long orderId) {
        printOrderService.deleteOrder(orderId);
        return "redirect:/admin/orders";
    }
//    @GetMapping("/admin/download")
//    public ResponseEntity<Resource> downloadFile(@RequestParam("fileName") String fileName) {
//        Resource resource = printOrderService.loadFileAsResource(fileName);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//    }
    @GetMapping("/admin/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) {
        try {
            Resource resource = printOrderService.loadFileAsResource(fileName);
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
        } catch (Exception e) {
            System.out.println("❌ Error during download: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }


}
