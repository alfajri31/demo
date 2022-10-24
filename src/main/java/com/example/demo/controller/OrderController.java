package com.example.demo.controller;

import com.example.demo.entity.CheckoutEntity;
import com.example.demo.entity.TransactionDetailEntity;
import com.example.demo.entity.TransactionHeaderEntity;
import com.example.demo.model.TokenModel;
import com.example.demo.repository.CheckoutRepository;
import com.example.demo.repository.TransactionDetailRepository;
import com.example.demo.repository.TransactionHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping
public class OrderController {

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Autowired
    private TransactionHeaderRepository transactionHeaderRepository;

    @PostMapping(value="product/order")
    public RedirectView order(RedirectAttributes redirectAttributes,@RequestBody TokenModel tokenModel) {
//        List<CheckoutEntity> checkoutEntityList = checkoutRepository.findAllByToken(tokenModel.getToken());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<CheckoutEntity> checkoutEntityList = checkoutRepository.findAllByUsername(authentication.getName());
        AtomicReference<Integer> total = new AtomicReference<>(0);
        checkoutEntityList.forEach( o -> {
            o.setStatus(true);
            Optional<CheckoutEntity> checkoutEntityOptional = checkoutRepository.findByTokenAndProductCodeAndStatusIsNull(o.getToken(),o.getProductCode());
            if(checkoutEntityOptional.isPresent()) {
                checkoutRepository.save(checkoutEntityOptional.get());
                total.updateAndGet(v -> v + checkoutEntityOptional.get().getProductEntity().getPrice() * checkoutEntityOptional.get().getQuantity());
            }
        });
        checkoutEntityList.forEach( o -> {
            TransactionHeaderEntity transactionHeaderEntity = new TransactionHeaderEntity();
            transactionHeaderEntity.setTotal(total.get());
            transactionHeaderEntity.setDate(new Date());
            TransactionHeaderEntity result = transactionHeaderRepository.save(transactionHeaderEntity);
            TransactionHeaderEntity transactionHeaderEntity1 = new TransactionHeaderEntity();
            transactionHeaderEntity1.setId(result.getId());
            TransactionDetailEntity transactionDetailEntity = new TransactionDetailEntity();
            transactionDetailEntity.setProductCode(o.getProductCode());
            transactionDetailEntity.setUnit(o.getProductEntity().getUnit());
            transactionDetailEntity.setSubTotal(total.get());
            transactionDetailEntity.setQuantity(o.getQuantity());
            transactionDetailEntity.setCurrency(o.getProductEntity().getCurrency());
            transactionDetailEntity.setTransactionHeaderEntity(transactionHeaderEntity1);
            transactionDetailRepository.save(transactionDetailEntity);
        });
        return new RedirectView("/product");
    }

}
