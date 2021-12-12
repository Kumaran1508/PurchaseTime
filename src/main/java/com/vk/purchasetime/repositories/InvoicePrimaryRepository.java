package com.vk.purchasetime.repositories;

import com.vk.purchasetime.models.InvoicePrimary;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvoicePrimaryRepository extends CrudRepository<InvoicePrimary,Integer> {
}
