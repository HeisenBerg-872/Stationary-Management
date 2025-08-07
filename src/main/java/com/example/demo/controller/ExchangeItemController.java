package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.ExchangeItem;
import com.example.demo.repository.ExchangeItemRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class ExchangeItemController {

    private final ExchangeItemRepository exchangeItemRepository;

    public ExchangeItemController(ExchangeItemRepository exchangeItemRepository) {
		super();
		this.exchangeItemRepository = exchangeItemRepository;
	}

	@GetMapping("/post-item")
    public String showPostItemForm(Model model) {
        model.addAttribute("item", new ExchangeItem());
        return "post_item";
    }

    @PostMapping("/post-item")
    public String handleItemPost(@ModelAttribute ExchangeItem item,
                                 @AuthenticationPrincipal com.example.demo.service.impl.CustomUserDetails userDetails) {
        item.setSeller(userDetails.getUser());
        item.setAvailable(true);
        exchangeItemRepository.save(item);
        return "redirect:/student/items";
    }

    @GetMapping("/items")
    public String viewItems(Model model) {
        List<ExchangeItem> availableItems = exchangeItemRepository.findByIsAvailableTrue();
        model.addAttribute("items", availableItems);
        return "browse_items";
    }

    @GetMapping("/interest/{id}")
    public String expressInterest(@PathVariable Long id, Model model) {
        ExchangeItem item = exchangeItemRepository.findById(id).orElse(null);
        if (item == null) {
            return "redirect:/student/items";
        }
        model.addAttribute("item", item);
        model.addAttribute("seller", item.getSeller());
        return "interest_contact";
    }
    @GetMapping("/dashboard")
    public String studentDashboard() {
        return "student_dashboard";
    }

    @PostMapping("/mark-sold/{id}")
    public String markItemAsSold(@PathVariable Long id) {
        exchangeItemRepository.deleteById(id);  // Remove from DB if sold
        return "redirect:/student/items";
    }
}
