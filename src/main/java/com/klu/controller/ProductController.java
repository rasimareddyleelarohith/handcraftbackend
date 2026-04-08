package com.klu.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klu.model.Products;
import com.klu.service.ProductService;

@RestController
@RequestMapping("/Product")
@CrossOrigin(originPatterns = { "http://localhost:*", "http://127.0.0.1:*" }, allowedHeaders = "*")
public class ProductController {

    @Autowired
    ProductService pr;

    @PostMapping("/saveProduct")
    public ResponseEntity<Products> saveProduct(@RequestBody Products p) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pr.saveProduct(p));
    }

    @PostMapping
    public ResponseEntity<Products> createProduct(@RequestBody Products p) {
        return saveProduct(p);
    }

    @GetMapping
    public List<Products> getProducts(@RequestParam(required = false) String search) {
        return pr.searchProducts(search);
    }

    @GetMapping("/all")
    public List<Products> getAllProducts() {
        return pr.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Products> getProduct(@PathVariable Long id) {
        return pr.getProduct(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return pr.deleteProduct(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        String result = pr.deleteProduct(id);
        if ("product not found".equals(result)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Products p) {
        return pr.updateProduct(id, p)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable Long id, @RequestBody Products p) {
        return updateProduct(id, p);
    }
}
