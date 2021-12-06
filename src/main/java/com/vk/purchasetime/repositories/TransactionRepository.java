package com.vk.purchasetime.repositories;

import com.vk.purchasetime.models.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction,Integer> {
}
