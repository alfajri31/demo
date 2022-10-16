package com.example.demo.controller;

import com.example.demo.entity.CheckoutEntity;
import com.example.demo.entity.LoginEntity;
import com.example.demo.repository.CheckoutRepository;
import com.example.demo.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class CheckoutController {

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private LoginRepository loginRepository;

    @GetMapping(value = "checkout")
    public String viewCheckout(Model model, HttpSession httpSession, HttpServletRequest request) {
        //hitung quantity based productCode
//        HttpSession session = request.getSession(true);
//        Object token = session.getAttribute("token");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<LoginEntity> loginEntityOptional = loginRepository.findByUsername(authentication.getName());
        List<CheckoutEntity> checkoutEntityList = checkoutRepository.findAllByUsername(loginEntityOptional.get().getUsername());
        model.addAttribute("checkoutList",checkoutEntityList);
        return "checkout";
    }
}


