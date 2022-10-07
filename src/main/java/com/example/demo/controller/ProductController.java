package com.example.demo.controller;

import com.example.demo.entity.CheckoutEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.model.ErrorMessages;
import com.example.demo.repository.CheckoutRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.TransactionDetailRepository;
import com.example.demo.repository.TransactionHeaderRepository;
import com.example.demo.service.IAuthenticationFacade;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
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

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Autowired
    private TransactionHeaderRepository transactionHeaderRepository;

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private IAuthenticationFacade iAuthenticationFacade;

//    @ModelAttribute("products")
//    public List<ProductEntity> messages() {
//        return productRepository.findAll();
//    }

    @GetMapping(value = "product")
    public String viewProduct(Model model) {
        List<ProductEntity> productEntityList = productRepository.findAll();
        model.addAttribute("products", productEntityList);
        return "product";
    }

    @GetMapping(value = "product-detail/{productCode}")
    public String viewProductDetail(Model model, @PathVariable String productCode) {
        Optional<ProductEntity> productOptional = productRepository.findById(productCode);
        model.addAttribute("productDetail",productOptional.get());
        return "product-detail";
    }

    @GetMapping(value = "single-product/{productCode}")
    public ResponseEntity<Optional<ProductEntity>> singleProduct(@PathVariable String productCode) {
        Optional<ProductEntity> productEntity = productRepository.findById(productCode);
        if(productEntity.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(productEntity);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productEntity);
    }

    @PostMapping(value = "single-product/{productCode}")
    public ResponseEntity<ProductEntity> buyProduct(@PathVariable String productCode, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Object token = session.getAttribute("token");
        Optional<ProductEntity> productEntity = productRepository.findById(productCode);
        if(productEntity.isPresent()) {
            CheckoutEntity checkoutEntity = new CheckoutEntity();
            checkoutEntity.setProductCode(productEntity.get().getProductCode());
            checkoutEntity.setDate(new Date());
            checkoutEntity.setToken(token.toString());
            checkoutEntity.setQuantity(1);
            ProductEntity productEntity1 = new ProductEntity();
            productEntity1.setId(productEntity.get().getId());
            productEntity1.setProductCode(productEntity.get().getProductCode());
            checkoutEntity.setProductEntity(productEntity1);
            Optional<CheckoutEntity> checkoutEntityOptional = checkoutRepository.findByTokenAndProductCode(token.toString(),productEntity.get().getProductCode());
            if(checkoutEntityOptional.isPresent()) {
                checkoutEntityOptional.get().setQuantity(checkoutEntityOptional.get().getQuantity()+1);
                checkoutRepository.save(checkoutEntityOptional.get());
            }
            else {
                checkoutRepository.save(checkoutEntity);
            }
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(200).build();
    }
    @GetMapping(value = "product/add")
    public String addProduct() {
        return "add-product";
    }

    @PostMapping(value="product/add")
    public String viewProduct(Model model, @RequestParam HashMap<String, String> formData, HttpSession session) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(formData.get("productName"));
        productEntity.setProductCode(formData.get("productCode"));
        productEntity.setPrice(Integer.parseInt(formData.get("price")));
        productRepository.save(productEntity);
        List<ProductEntity> productEntityList = productRepository.findAll();
        model.addAttribute("products", productEntityList);
        return "product";
    }
}
