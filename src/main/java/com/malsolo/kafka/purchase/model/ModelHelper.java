package com.malsolo.kafka.purchase.model;

import com.malsolo.kafka.purchase.model.avro.Purchase;
import java.util.Objects;

public class ModelHelper {

    private static final String CC_NUMBER_REPLACEMENT="xxxx-xxxx-xxxx-";

    public static Purchase maskCreditCard(Purchase purchase) {
        Objects.requireNonNull(purchase.getCreditCardNumber(), "Credit Card can't be null");

        String[] parts = purchase.getCreditCardNumber().split("-");
        if (parts.length < 4 ) {
            purchase.setCreditCardNumber("xxxx");
        } else {
            String last4Digits = purchase.getCreditCardNumber().split("-")[3];
            purchase.setCreditCardNumber(CC_NUMBER_REPLACEMENT + last4Digits);
        }

        return purchase;
    }

}
