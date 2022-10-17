package com.example.demo.controller;

import com.example.demo.entity.CheckoutEntity;
import com.example.demo.entity.LoginEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.model.TokenModel;
import com.example.demo.model.Total;
import com.example.demo.repository.*;
import com.example.demo.service.IAuthenticationFacade;
import com.example.demo.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


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

    @Autowired
    private ILoginService iLoginService;

    @Autowired
    private LoginRepository loginRepository;

//    @ModelAttribute("products")
//    public List<ProductEntity> messages() {
//        return productRepository.findAll();
//    }

    @GetMapping(value = "product")
    public String viewProduct(Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<LoginEntity> loginEntityOptional2 = loginRepository.findByUsername(authentication.getName());
        List<ProductEntity> productEntityList = productRepository.findAllByLoginEntity(loginEntityOptional2.get());
        model.addAttribute("products", productEntityList);
        Optional<LoginEntity> loginEntityOptional = loginRepository.findByUsername(authentication.getName());
        model.addAttribute("token",loginEntityOptional.get().getToken());
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<LoginEntity> loginEntityOptional = loginRepository.findByUsername(authentication.getName());
        Optional<ProductEntity> productEntity = productRepository.findById(productCode);
        if(productEntity.isPresent()) {
            CheckoutEntity checkoutEntity = new CheckoutEntity();
            checkoutEntity.setProductCode(productEntity.get().getProductCode());
            checkoutEntity.setDate(new Date());
            checkoutEntity.setUsername(loginEntityOptional.get().getUsername());
            checkoutEntity.setQuantity(1);
            ProductEntity productEntity1 = new ProductEntity();
            productEntity1.setId(productEntity.get().getId());
            productEntity1.setProductCode(productEntity.get().getProductCode());
            checkoutEntity.setProductEntity(productEntity1);
            Optional<CheckoutEntity> checkoutEntityOptional = checkoutRepository.findByUsernameAndProductCodeAndStatusIsNull(loginEntityOptional.get().getUsername(),productEntity.get().getProductCode());
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
    public RedirectView viewProduct(Model model, @RequestParam HashMap<String, String> formData, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(formData.get("productName"));
        productEntity.setProductCode(formData.get("productCode"));
        productEntity.setPrice(Integer.parseInt(formData.get("price")));
        Optional<LoginEntity> loginEntityOptional = loginRepository.findByUsername(authentication.getName());
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setUsername(loginEntityOptional.get().getUsername());
        loginEntity.setPassword(loginEntityOptional.get().getPassword());
        loginEntity.setId(loginEntityOptional.get().getId());
        productEntity.setLoginEntity(loginEntity);
        productRepository.save(productEntity);
        List<ProductEntity> productEntityList = productRepository.findAll();
        model.addAttribute("products", productEntityList);
        return new RedirectView("/product");
    }

    @PostMapping(value = "product/total")
    public ResponseEntity<Total> total(@RequestBody TokenModel token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<CheckoutEntity> productEntityList = checkoutRepository.findAllByUsernameAndStatusIsNull(authentication.getName());
        AtomicReference<Integer> total = new AtomicReference<>(0);
        if(productEntityList.toArray().length>0) {
            productEntityList.forEach( v -> {
                total.updateAndGet(v1 -> v1 + v.getProductEntity().getPrice() * v.getQuantity());
            });
        }
        Total total1 = new Total();
        total1.setTotal(total);
        return ResponseEntity.status(200).body(total1);
    }
}
