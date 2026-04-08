package com.klu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klu.model.Payment;
import com.klu.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(originPatterns = { "http://localhost:*", "http://127.0.0.1:*" }, allowedHeaders = "*")
public class Paymentcontroller {

    @Autowired
    private PaymentService py;

    @PostMapping("/make")
    public Payment makePayment(@RequestBody Payment p) {
        return py.processPayment(p);
    }

    @PostMapping
    public Payment createPayment(@RequestBody Payment p) {
        return makePayment(p);
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return py.getAllPayments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
        return py.getPayment(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updatePaymentStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        if (status == null || status.isBlank()) {
            return ResponseEntity.badRequest().body("status is required");
        }

        return py.updatePaymentStatus(id, status)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("payment not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        if (!py.deletePayment(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("payment not found");
        }

        return ResponseEntity.ok("delete success");
    }
}
