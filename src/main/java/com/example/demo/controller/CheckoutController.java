package com.example.demo.controller;

import com.example.demo.entity.CheckoutEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.CheckoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping
public class CheckoutController {

    @Autowired
    private CheckoutRepository checkoutRepository;
    @GetMapping(value = "checkout")
    public String viewCheckout(Model model, HttpSession httpSession, HttpServletRequest request) {
        //hitung quantity based productCode
        HttpSession session = request.getSession(true);
        Object token = session.getAttribute("token");
        List<CheckoutEntity> checkoutEntityList = checkoutRepository.findAllByToken(token.toString());
        model.addAttribute("checkoutList",checkoutEntityList);
        return "checkout";
    }
}


