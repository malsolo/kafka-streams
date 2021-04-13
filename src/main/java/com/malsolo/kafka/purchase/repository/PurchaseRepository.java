package com.malsolo.kafka.purchase.repository;

import com.malsolo.kafka.purchase.model.avro.Purchase;

public interface PurchaseRepository {
    void save(Purchase purchase);
}

