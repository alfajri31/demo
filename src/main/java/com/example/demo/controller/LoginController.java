package com.example.demo.controller;

import com.example.demo.model.ErrorMessages;
import com.example.demo.repository.LoginRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.LoginService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping
public class LoginController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginRepository loginRepository;

    @GetMapping(value = "login")
    public String viewLogin(Model model) {
        return "login";
    }

    @PostMapping(value="login")
    public RedirectView viewProduct(Model model,RedirectAttributes redirectAttributes, @RequestParam HashMap<String, String> formData, HttpSession session) {
        try {
            if(formData.get("username")!="") {
                UserDetails userDetails = loginService.loadUserByUsername(formData.get("username"));
                if (!userDetails.isEnabled()) {
                    return new RedirectView("login");
                }
                String token = Jwts.builder().setSubject(String.valueOf(userDetails.getUsername())).setExpiration(
                        new Date(System.currentTimeMillis() + Long.parseLong("864000000"))).signWith(SignatureAlgorithm.HS512,"fajrifajri").compact();
//                session.setAttribute("token",token);
                redirectAttributes.addFlashAttribute("token",token);
                return new RedirectView("product");
            }
            return new RedirectView("login");
        }
        catch(UsernameNotFoundException e) {
            ErrorMessages errorMessages = new ErrorMessages();
            errorMessages.setMessage("wrong credential please use smith with password sm1t_OK");
            redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
            return new RedirectView("login");
        }

    }
}
