package com.example.demo;

import com.example.demo.entity.LoginEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.TransactionDetailEntity;
import com.example.demo.entity.TransactionHeaderEntity;
import com.example.demo.repository.LoginRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.TransactionDetailRepository;
import com.example.demo.repository.TransactionHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LoadData implements CommandLineRunner {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Autowired
    private TransactionHeaderRepository transactionHeaderRepository;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {
        if (loginRepository.count() == 0) {
            LoginEntity loginEntity = new LoginEntity();
            loginEntity.setUsername("smith");
            loginEntity.setPassword("sm1t_OK");
            loginRepository.save(loginEntity);
        }

        if(productRepository.count()==0) {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setProductCode("SKUSKILNP");
            productEntity.setProductName("So Klin Pewangi");
            productEntity.setPrice(15000);
            productEntity.setCurrency("IDR");
            productEntity.setDiscount(10);
            productEntity.setDimension("13xm x 10cm");
            productEntity.setUnit("PCS");
            productRepository.save(productEntity);
        }

        if(transactionDetailRepository.count()==0) {
            TransactionDetailEntity transactionDetailEntity = new TransactionDetailEntity();
            transactionDetailEntity.setDocumentCode("TRX");
            transactionDetailEntity.setDocumentNumber("001");
            transactionDetailEntity.setProductCode("SKUSKILNP");
            transactionDetailEntity.setPrice(13500);
            transactionDetailEntity.setQuantity(5);
            transactionDetailEntity.setUnit("PCS");
            transactionDetailEntity.setSubTotal(67500);
            transactionDetailEntity.setCurrency("IDR");
            transactionDetailRepository.save(transactionDetailEntity);
        }

        if(transactionHeaderRepository.count()==0) {
            TransactionHeaderEntity transactionHeaderEntity = new TransactionHeaderEntity();
            transactionHeaderEntity.setDocumentCode("TRX");
            transactionHeaderEntity.setDocumentNumber("001");
            transactionHeaderEntity.setUsername("Smit");
            transactionHeaderEntity.setTotal(67500);
            transactionHeaderEntity.setDate(new Date());
            transactionHeaderRepository.save(transactionHeaderEntity);
        }
    }
}
