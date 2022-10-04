package com.example.demo.controller;

import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;


/**
 * IN SERVICE EXAMPLE
 */
@Controller
@RequestMapping
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @ModelAttribute("products")
    public List<ProductEntity> messages() {
        return productRepository.findAll();
    }

    @GetMapping(value = "product")
    public String viewProduct(Model model) {
        List<ProductEntity> productEntityList = productRepository.findAll();
        model.addAttribute("products", productEntityList);
        return "product";
    }

    @GetMapping(value = "product-detail/{id}")
    public String viewProductDetail(Model model, @PathVariable Integer id) {
        Optional<ProductEntity> productOptional = productRepository.findById(id);
        model.addAttribute("productDetail",productOptional.get());
        return "product-detail";
    }



}
