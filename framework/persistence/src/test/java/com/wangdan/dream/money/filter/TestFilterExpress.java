package com.wangdan.dream.money.filter;

import com.wangdan.dream.money.Person;
import com.wangdan.dream.persistence.orm.filter.FilterExpress;
import com.wangdan.dream.persistence.orm.filter.FilterType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFilterExpress {
    @Test
    public void test() throws NoSuchFieldException {
        FilterExpress filterExpress = new FilterExpress("id", FilterType.EQUAL, "11");
        assertEquals(filterExpress.toSql(Person.class), "\"id\"=11");
        filterExpress = new FilterExpress("name", FilterType.EQUAL, "11");
        assertEquals(filterExpress.toSql(Person.class).trim(), "\"name\"=\'11\'");

    }
}
