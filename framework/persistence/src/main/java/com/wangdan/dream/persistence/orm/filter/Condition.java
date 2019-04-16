package com.wangdan.dream.persistence.orm.filter;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Condition {
    private FilterGroup filterGroup;
    private Order order;
    private Range range;

    public boolean check(Object instance) {
        return filterGroup.isValid(instance);
    }
}
