package com.malsolo.kafka.purchase.joiner;

import com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase;
import com.malsolo.kafka.purchase.model.avro.Purchase;
import java.util.ArrayList;
import java.util.List;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class PurchaseJoiner implements ValueJoiner<Purchase, Purchase, CorrelatedPurchase> {

    @Override
    public CorrelatedPurchase apply(Purchase purchase, Purchase otherPurchase) {
        var purchaseDate = purchase != null ? purchase.getPurchaseDate() : null;
        var price = purchase != null ? purchase.getPrice() : 0.0;
        var itemPurchased = purchase != null ? purchase.getItemPurchased() : null;

        var otherPurchaseDate = otherPurchase != null ? otherPurchase.getPurchaseDate() : null;
        var otherPrice = otherPurchase != null ? otherPurchase.getPrice() : 0.0;
        var otherItemPurchased = otherPurchase != null ? otherPurchase.getItemPurchased() : null;

        List<String> purchasedItems = new ArrayList<>();

        if (itemPurchased != null) {
            purchasedItems.add(itemPurchased);
        }

        if (otherItemPurchased != null) {
            purchasedItems.add(otherItemPurchased);
        }

        var customerId = purchase != null ? purchase.getCustomerId() : null;
        var otherCustomerId = otherPurchase != null ? otherPurchase.getCustomerId() : null;

        return CorrelatedPurchase.newBuilder()
            .setCustomerId(customerId != null ? customerId : otherCustomerId)
            .setItemsPurchased(purchasedItems)
            .setTotalAmount(price + otherPrice)
            .setFirstPurchaseTime(purchaseDate)
            .setSecondPurchaseTime(otherPurchaseDate)
            .build();
    }
}