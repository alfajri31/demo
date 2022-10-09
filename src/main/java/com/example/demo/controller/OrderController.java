package com.example.demo.controller;

import com.example.demo.entity.CheckoutEntity;
import com.example.demo.entity.TransactionDetailEntity;
import com.example.demo.entity.TransactionHeaderEntity;
import com.example.demo.model.TokenModel;
import com.example.demo.repository.CheckoutRepository;
import com.example.demo.repository.TransactionDetailRepository;
import com.example.demo.repository.TransactionHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
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
    public void order(RedirectAttributes redirectAttributes,@RequestBody TokenModel tokenModel) {
        List<CheckoutEntity> checkoutEntityList = checkoutRepository.findAllByToken(tokenModel.getToken());
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
    }

}
