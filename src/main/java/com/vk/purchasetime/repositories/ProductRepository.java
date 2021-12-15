package com.vk.purchasetime.repositories;

import com.vk.purchasetime.models.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Integer> {
    public List<Product> findTop4ByOrderByDiscountDesc();
    public List<Product> findTop4ByOrderBySoldDesc();
    public List<Product> findAll(Pageable pageable);
    public Product findByProductId(int id);
}
