package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping
public class LogoutController {

    @GetMapping(value = "/logout")
    public RedirectView viewLogin(Model model, HttpServletRequest request, HttpServletResponse response) {

        return new RedirectView("/login");
    }
}
