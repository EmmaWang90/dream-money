package com.wangdan.dream.persistence.orm.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterGroup {
    private List<FilterExpress> filterExpressList;
    private FilterGroupType filterGroupType;

    public boolean isValid(Object instance) {
        boolean result = true;
        for (FilterExpress filterExpress : filterExpressList) {
            boolean tempResult = filterExpress.isValid(instance);
            result = filterGroupType.calculate(result, tempResult);
        }
        return result;
    }

    public String toSql() {
        StringBuilder stringBuilder = new StringBuilder();
        for (FilterExpress filterExpress : filterExpressList) {
            stringBuilder.append(" ");
            stringBuilder.append(filterExpress.toSql());
        }
        return stringBuilder.toString();
    }
}
