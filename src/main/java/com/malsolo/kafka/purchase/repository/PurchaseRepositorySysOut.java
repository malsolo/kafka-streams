package com.malsolo.kafka.purchase.repository;

import com.malsolo.kafka.purchase.model.avro.Purchase;

public class PurchaseRepositorySysOut implements PurchaseRepository {
    @Override
    public void save(Purchase purchase) {
        System.out.printf("Saving transaction on %tB %<te,  %<tY for %2$s, item: %3$s\n",
            purchase.getPurchaseDate(),
            purchase.getEmployeeId(),
            purchase.getItemPurchased());
    }

}
