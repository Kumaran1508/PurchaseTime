package com.vk.purchasetime.repositories;

import com.vk.purchasetime.models.InvoiceTransaction;
import com.vk.purchasetime.models.Product;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvoiceTransactionRepository extends CrudRepository<InvoiceTransaction,Integer> {

}
