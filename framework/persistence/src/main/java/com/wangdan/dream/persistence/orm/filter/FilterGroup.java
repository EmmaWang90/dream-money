package com.wangdan.dream.persistence.orm.filter;

import com.google.common.base.Joiner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterGroup {
    private List<FilterExpress> filterExpressList;
    private FilterGroupType filterGroupType = FilterGroupType.AND;

    public FilterGroup(List<FilterExpress> filterExpressList) {
        this.filterExpressList = filterExpressList;
    }

    public FilterGroup(FilterExpress... filterExpressList) {
        this.filterExpressList = Arrays.asList(filterExpressList);
    }

    public boolean isValid(Object instance) {
        boolean result = true;
        for (FilterExpress filterExpress : filterExpressList) {
            boolean tempResult = filterExpress.isValid(instance);
            result = filterGroupType.calculate(result, tempResult);
        }
        return result;
    }

    public String toSql(Class entityClass) throws NoSuchFieldException {
        StringBuilder stringBuilder = new StringBuilder("");
        List<String> sqlList = new ArrayList<>();
        for (FilterExpress filterExpress : filterExpressList) {
            sqlList.add(filterExpress.toSql(entityClass));
        }
        stringBuilder.append(Joiner.on(" " + filterGroupType.name() + "  ").join(sqlList));
        return stringBuilder.toString();
    }
}
