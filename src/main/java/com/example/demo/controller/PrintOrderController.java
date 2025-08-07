package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.PrintOrder;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PrintOrderService;
import com.example.demo.service.impl.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class PrintOrderController {

    public PrintOrderController(PrintOrderService printOrderService, UserRepository userRepository) {
		super();
		this.printOrderService = printOrderService;
		this.userRepository = userRepository;
	}

	private final PrintOrderService printOrderService;
    private final UserRepository userRepository;

    @GetMapping("/upload-order")
    public String showUploadForm() {
        return "upload_order";
    }
    @GetMapping("/my-orders")
    public String viewMyOrders(@AuthenticationPrincipal com.example.demo.service.impl.CustomUserDetails userDetails,
                               Model model) {
        if (userDetails == null) {
            model.addAttribute("error", "Session expired. Please login again.");
            return "error";
        }

        User user = userDetails.getUser();
        List<PrintOrder> orders = printOrderService.getOrdersByUser(user);
        model.addAttribute("orders", orders);
        return "student_orders";
    }


    @PostMapping("/upload-order")
    public String handleUpload(@RequestParam("file") MultipartFile file,
                               @RequestParam("printType") String printType,
                               @RequestParam("copies") int copies,
                               @AuthenticationPrincipal CustomUserDetails userDetails,
                               Model model) {
        if (userDetails == null) {
            System.out.println("❌ Authenticated userDetails is null! Session expired or not authenticated.");
            model.addAttribute("error", "You must be logged in.");
            return "error";
        }

        // ✅ Fetch managed user entity from DB
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found in database"));

        PrintOrder order = printOrderService.createOrder(user, file, printType, copies);
        model.addAttribute("orderId", order.getOrderId());
        return "order_success";
    }
}
