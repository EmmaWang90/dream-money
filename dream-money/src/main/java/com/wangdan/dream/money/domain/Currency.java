package com.wangdan.dream.money.domain;

public enum Currency {
    CNY;


    public static Currency parse(String value) {
        for (Currency currency : Currency.values()) {
            if (currency.name().equals(value))
                return currency;
        }
        throw new IllegalArgumentException();
    }
}
