package com.vk.purchasetime.repositories;

import com.vk.purchasetime.models.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Integer> {
    public List<Product> findAllByOrderByDiscountDesc();
    public List<Product> findTop3ByOrderByDiscountDesc();
    public List<Product> findAll(Pageable pageable);
}
