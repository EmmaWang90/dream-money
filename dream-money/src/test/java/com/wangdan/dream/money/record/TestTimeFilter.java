package com.wangdan.dream.money.record;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.wangdan.dream.money.rest.DreamMoneyRestServiceImpl.getMaxDateInMonth;
import static com.wangdan.dream.money.rest.DreamMoneyRestServiceImpl.getMinDateInMonth;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTimeFilter {
    @Test
    public void test() {
        Date minDate = getMinDateInMonth(2019, 3);
//        assertEquals(minDate.getYear(), 2019);
        assertEquals(minDate.getMonth(), 2);
        assertEquals(minDate.getDate(), 1);
        assertEquals(minDate.getHours(), 0);
        assertEquals(minDate.getMinutes(), 0);
        assertEquals(minDate.getSeconds(), 0);
        Date maxDate = getMaxDateInMonth(2019, 3);
        Calendar calendar = new GregorianCalendar();
        calendar.set(2019, 2, 31);
        Date expected = calendar.getTime();
        assertEquals(minDate, expected);
    }
}
