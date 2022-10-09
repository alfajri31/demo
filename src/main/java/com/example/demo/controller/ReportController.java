package com.example.demo.controller;

import com.example.demo.entity.TransactionHeaderEntity;
import com.example.demo.repository.TransactionDetailRepository;
import com.example.demo.repository.TransactionHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping
public class ReportController {

    @Autowired
    private TransactionHeaderRepository transactionHeaderRepository;

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @GetMapping("/report")
    public String  report(Model model,RedirectAttributes redirectAttributes) {
        List<TransactionHeaderEntity> transactionHeaderEntityList = transactionHeaderRepository.findAll();
        model.addAttribute("transactions",transactionHeaderEntityList);
        return "report";
    }
}
