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
    public FilterGroup getFilterGroup() {
        return filterGroup;
    }

    public void setFilterGroup(FilterGroup filterGroup) {
        this.filterGroup = filterGroup;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    private FilterGroup filterGroup;
    private Order order;
    private Range range;

    public boolean check(Object instance) {
        return filterGroup.isValid(instance);
    }

    public String toSql(Class entityClass) throws NoSuchFieldException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(filterGroup.toSql(entityClass));
        if (order != null)
            stringBuilder.append(order.toSql());
        if (range != null)
            stringBuilder.append(range.toSql());
        return stringBuilder.toString();
    }
}
