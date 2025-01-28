package com.intuit.bidding.enums;

public enum ProductCategory {
    APPLIANCE(0),
    BOOK(1);

    public int value;

    ProductCategory(int value) {
        this.value = value;
    }
}
