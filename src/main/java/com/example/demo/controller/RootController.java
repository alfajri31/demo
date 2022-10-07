package com.example.demo.controller;

import com.example.demo.entity.ProductEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class RootController {
    @GetMapping
    public RedirectView viewLogin(Model model) {
        return new RedirectView("login");
    }
}
