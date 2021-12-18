package com.vk.purchasetime.repositories;

import com.vk.purchasetime.models.InvoicePrimary;
import com.vk.purchasetime.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface InvoicePrimaryRepository extends CrudRepository<InvoicePrimary,Integer> {
    public List<InvoicePrimary> findByUser(User user);
    public InvoicePrimary findByInvoiceId(long id);
    public List<InvoicePrimary> findAllByInvoiceDateBetween(Date from, Date to);
}
