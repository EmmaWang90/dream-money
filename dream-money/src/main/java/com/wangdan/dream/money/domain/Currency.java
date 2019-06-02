package com.wangdan.dream.money.domain;

public enum Currency {
    CNY;

    public static Currency parseName(String value) {
        for (Currency currency : Currency.values()) {
            if (currency.name().equals(value))
                return currency;
        }
        throw new IllegalArgumentException();
    }

    public static Currency parseValue(String value) {
        for (Currency currency : Currency.values()) {
            if (currency.name().equals(value))
                return currency;
        }
        throw new IllegalArgumentException();
    }
}
