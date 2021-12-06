package com.vk.purchasetime.repositories;

import com.vk.purchasetime.models.InvoiceTransaction;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceTransactionRepository extends CrudRepository<InvoiceTransaction,Integer> {
}
