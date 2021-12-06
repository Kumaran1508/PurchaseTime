package com.vk.purchasetime.repositories;

import com.vk.purchasetime.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Integer> {
}
