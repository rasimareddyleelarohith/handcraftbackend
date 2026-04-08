package com.klu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klu.model.Products;

public interface Productrepo extends JpaRepository<Products, Long> {

    List<Products> findByProductnameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrArtisanContainingIgnoreCase(
            String productname,
            String category,
            String artisan
    );
}
