package com.klu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.model.Products;
import com.klu.repo.Productrepo;

@Service
public class ProductService {

    @Autowired
    Productrepo pr;

    public Products saveProduct(Products p) {
        normalizeProduct(p);
        return pr.save(p);
    }

    public List<Products> getAllProducts() {
        return pr.findAll();
    }

    public Optional<Products> getProduct(Long id) {
        return pr.findById(id);
    }

    public String deleteProduct(Long id) {
        if (!pr.existsById(id)) {
            return "product not found";
        }

        pr.deleteById(id);
        return "delete success";
    }

    public Optional<Products> updateProduct(Long id, Products updatedProduct) {
        return pr.findById(id).map(existingProduct -> {
            if (updatedProduct.getProductname() != null) {
                existingProduct.setProductname(updatedProduct.getProductname());
            }
            if (updatedProduct.getCategory() != null) {
                existingProduct.setCategory(updatedProduct.getCategory());
            }
            if (updatedProduct.getArtisan() != null) {
                existingProduct.setArtisan(updatedProduct.getArtisan());
            }
            if (updatedProduct.getPrice() != null) {
                existingProduct.setPrice(updatedProduct.getPrice());
            }
            if (updatedProduct.getQuantity() != null) {
                existingProduct.setQuantity(updatedProduct.getQuantity());
            }
            if (updatedProduct.getStock() != null) {
                existingProduct.setStock(updatedProduct.getStock());
            }
            if (updatedProduct.getImage() != null) {
                existingProduct.setImage(updatedProduct.getImage());
            }
            if (updatedProduct.getDescription() != null) {
                existingProduct.setDescription(updatedProduct.getDescription());
            }
            if (updatedProduct.getCulturalNotes() != null) {
                existingProduct.setCulturalNotes(updatedProduct.getCulturalNotes());
            }
            if (updatedProduct.getRating() != null) {
                existingProduct.setRating(updatedProduct.getRating());
            }

            normalizeProduct(existingProduct);
            return pr.save(existingProduct);
        });
    }

    public List<Products> searchProducts(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllProducts();
        }

        return pr.findByProductnameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrArtisanContainingIgnoreCase(
                keyword,
                keyword,
                keyword
        );
    }

    private void normalizeProduct(Products product) {
        if (product.getStock() != null) {
            product.setQuantity(product.getStock());
        } else if (product.getQuantity() != null) {
            product.setStock(product.getQuantity());
        }
    }
}
