package com.wangdan.dream.persistence.orm.table;


import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class DynamicTableName extends TableName {
    private static String SEPERATE = "_";

    private static String calculateSuffix(String period) {
        Calendar calendar = new GregorianCalendar();
        List<Integer> numbers = new ArrayList();
        switch (period) {
            case "MINUTE":
                numbers.add(calendar.get(Calendar.MINUTE));
            case "HOUR":
                numbers.add(calendar.get(Calendar.HOUR_OF_DAY));
            case "DAY":
                numbers.add(calendar.get(Calendar.DAY_OF_MONTH));
            case "WEEK":
                if (period.equals("WEEK"))
                    numbers.add(calendar.get(Calendar.WEEK_OF_MONTH));
            case "MONTH":
                numbers.add(calendar.get(Calendar.MONTH));
            case "YEAR":
                numbers.add(calendar.get(Calendar.YEAR));
                break;
            default:
                break;

        }
        Collections.reverse(numbers);
        return Joiner.on(SEPERATE).join(numbers);
    }

    public static String getTableName(String period, String tableName) {
        if (period == null)
            return tableName;
        String suffix = calculateSuffix(period);
        return tableName + suffix;
    }
}
