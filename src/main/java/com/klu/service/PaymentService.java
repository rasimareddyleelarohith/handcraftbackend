package com.klu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.model.Payment;
import com.klu.repo.Paymentrepo;

@Service
public class PaymentService {

    @Autowired
    private Paymentrepo repo;

    public Payment processPayment(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment details are required");
        }

        if (payment.getAmount() == null || payment.getAmount() <= 0) {
            payment.setStatus("FAILED - VALID AMOUNT REQUIRED");
            return repo.save(payment);
        }

        String method = payment.getMethod();
        if (method == null || method.isBlank()) {
            payment.setStatus("FAILED - PAYMENT METHOD REQUIRED");
            return repo.save(payment);
        }

        String normalizedMethod = normalizeMethod(method);
        payment.setMethod(normalizedMethod);

        if (normalizedMethod.equals("UPI")) {
            if (isBlank(payment.getUpiId())) {
                payment.setStatus("FAILED - UPI ID REQUIRED");
                return repo.save(payment);
            }
        }

        else if (normalizedMethod.equals("Credit Card")) {
            if (isBlank(payment.getCardNumber()) || isBlank(payment.getCvv())
                    || isBlank(payment.getCardHolderName()) || isBlank(payment.getExpiryDate())) {
                payment.setStatus("FAILED - CARD DETAILS REQUIRED");
                return repo.save(payment);
            }
        }

        else if (normalizedMethod.equals("Bank")) {
            if (isBlank(payment.getAccountNumber()) || isBlank(payment.getIfscCode())) {
                payment.setStatus("FAILED - BANK DETAILS REQUIRED");
                return repo.save(payment);
            }
        }

        else if (normalizedMethod.equals("PayPal")) {
            if (isBlank(payment.getPaypalEmail())) {
                payment.setStatus("FAILED - EMAIL REQUIRED");
                return repo.save(payment);
            }
        }

        else {
            payment.setStatus("FAILED - UNSUPPORTED PAYMENT METHOD");
            return repo.save(payment);
        }

        payment.setStatus("SUCCESS");
        return repo.save(payment);
    }

    public List<Payment> getAllPayments() {
        return repo.findAll();
    }

    public Optional<Payment> getPayment(Long id) {
        return repo.findById(id);
    }

    public Optional<Payment> updatePaymentStatus(Long id, String status) {
        return repo.findById(id).map(payment -> {
            payment.setStatus(status);
            return repo.save(payment);
        });
    }

    public boolean deletePayment(Long id) {
        if (!repo.existsById(id)) {
            return false;
        }

        repo.deleteById(id);
        return true;
    }

    private String normalizeMethod(String method) {
        if (method.equalsIgnoreCase("card") || method.equalsIgnoreCase("credit card")) {
            return "Credit Card";
        }
        if (method.equalsIgnoreCase("upi")) {
            return "UPI";
        }
        if (method.equalsIgnoreCase("bank") || method.equalsIgnoreCase("bank transfer")) {
            return "Bank";
        }
        if (method.equalsIgnoreCase("paypal") || method.equalsIgnoreCase("pay pal")) {
            return "PayPal";
        }

        return method;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
