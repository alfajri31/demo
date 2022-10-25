package com.example.demo;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.util.*;

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

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

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
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setId(1L);
            roleEntity.setName("ROLE_ADMIN");
            List<LoginEntity> loginEntities = new ArrayList<>();
            loginEntity.setId(1L);
            loginEntities.add(loginEntity);
            roleEntity.setLogins(loginEntities);
            roleRepository.save(roleEntity);

            LoginEntity loginEntity2 = new LoginEntity();
            loginEntity2.setUsername("user");
            loginEntity2.setPassword("user");
            loginRepository.save(loginEntity2);
            RoleEntity roleEntity2 = new RoleEntity();
            roleEntity2.setId(3L);
            roleEntity2.setName("ROLE_USER");
            List<LoginEntity> loginEntities2 = new ArrayList<>();
            loginEntity2.setId(3L);
            loginEntities2.add(loginEntity2);
            roleEntity2.setLogins(loginEntities2);
            roleRepository.save(roleEntity2);

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
            productEntity.setQuantity(1);
            LoginEntity loginEntity = new LoginEntity();
            loginEntity.setUsername("smith");
            loginEntity.setPassword("sm1t_OK");
            loginEntity.setId(1L);
            productEntity.setLoginEntity(loginEntity);
            productRepository.save(productEntity);
        }

        if(transactionHeaderRepository.count()==0) {
            TransactionHeaderEntity transactionHeaderEntity = new TransactionHeaderEntity();
            transactionHeaderEntity.setDocumentCode("TRX");
            transactionHeaderEntity.setDocumentNumber("001");
            transactionHeaderEntity.setUsername("Smit");
            transactionHeaderEntity.setTotal(67500);
            transactionHeaderEntity.setDate(new Date());
            transactionHeaderRepository.save(transactionHeaderEntity);

            TransactionHeaderEntity transactionHeaderEntity2 = new TransactionHeaderEntity();
            transactionHeaderEntity2.setDocumentCode("TRJ");
            transactionHeaderEntity2.setDocumentNumber("002");
            transactionHeaderEntity2.setUsername("user");
            transactionHeaderEntity2.setTotal(67500);
            transactionHeaderEntity2.setDate(new Date());
            transactionHeaderRepository.save(transactionHeaderEntity2);
            List<TransactionHeaderEntity> transactionHeaderEntityList = transactionHeaderRepository.findAll();
            System.out.println(transactionHeaderEntityList);
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
            TransactionHeaderEntity transactionHeaderEntity = new TransactionHeaderEntity();
            transactionHeaderEntity.setId(5L);
            transactionDetailEntity.setTransactionHeaderEntity(transactionHeaderEntity);
            transactionDetailRepository.save(transactionDetailEntity);
//
            TransactionDetailEntity transactionDetailEntity2 = new TransactionDetailEntity();
            transactionDetailEntity2.setDocumentCode("TRJ");
            transactionDetailEntity2.setDocumentNumber("001");
            transactionDetailEntity2.setProductCode("SKUSKILNP");
            transactionDetailEntity2.setPrice(13500);
            transactionDetailEntity2.setQuantity(5);
            transactionDetailEntity2.setUnit("PCS");
            transactionDetailEntity2.setSubTotal(67500);
            transactionDetailEntity2.setCurrency("IDR");
            TransactionHeaderEntity transactionHeaderEntity2 = new TransactionHeaderEntity();
            transactionHeaderEntity2.setId(6L);
            transactionDetailEntity2.setTransactionHeaderEntity(transactionHeaderEntity2);
            transactionDetailRepository.save(transactionDetailEntity2);
        }

        Optional<LoginEntity> loginEntityOptional = loginRepository.findById(1L);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(2L);
        List<LoginEntity> loginEntities = new ArrayList<>();
        loginEntities.add(loginEntityOptional.get());
        roleEntity.setLogins(loginEntities);
        List<RoleEntity> roleEntityList = new ArrayList<>();
        roleEntityList.add(roleEntity);
        loginEntityOptional.get().setRoleEntities(roleEntityList);
        loginEntityOptional.get().setRolesId(2L);
        loginRepository.save(loginEntityOptional.get());

        Optional<LoginEntity> loginEntityOptional2 = loginRepository.findById(3L);
        RoleEntity roleEntity2 = new RoleEntity();
        roleEntity2.setId(4L);
        List<LoginEntity> loginEntities2 = new ArrayList<>();
        loginEntities2.add(loginEntityOptional2.get());
        roleEntity2.setLogins(loginEntities2);
        List<RoleEntity> roleEntityList2 = new ArrayList<>();
        roleEntityList2.add(roleEntity2);
        loginEntityOptional2.get().setRoleEntities(roleEntityList2);
        loginEntityOptional2.get().setRolesId(4L);
        loginRepository.save(loginEntityOptional2.get());

        PrivilegeEntity privilegeEntity = new PrivilegeEntity();
        privilegeEntity.setName("READ_PRIVILEGE");
        privilegeRepository.save(privilegeEntity);

        PrivilegeEntity privilegeEntity2 = new PrivilegeEntity();
        privilegeEntity2.setName("WRITE_PRIVILEGE");
        privilegeRepository.save(privilegeEntity2);

        List<PrivilegeEntity> privilegeEntityList = new ArrayList<>();
        privilegeEntityList.add(privilegeEntity);
        privilegeEntityList.add(privilegeEntity2);
        Optional<RoleEntity> roleEntityPrivilege1 = roleRepository.findById(2L);
        roleEntityPrivilege1.get().setPrivileges(privilegeEntityList);
        roleRepository.save(roleEntityPrivilege1.get());

        List<PrivilegeEntity> privilegeEntityList2 = new ArrayList<>();
        privilegeEntityList2.add(privilegeEntity);
        Optional<RoleEntity> roleEntityPrivilege2 = roleRepository.findById(4L);
        roleEntityPrivilege2.get().setPrivileges(privilegeEntityList2);
        roleRepository.save(roleEntityPrivilege2.get());


    }

    private void loadRoleData() {

    }
}
