package com.wangdan.dream.persistence.orm.table;

import com.google.common.base.Joiner;

import java.util.*;

public class TableNameUtil {
    private static String SEPERATE = "_";
    private static TableNameUtil instance = new TableNameUtil();

    private TableNameUtil() {

    }

    public static TableNameUtil getInstance() {
        return instance;
    }

    public String getTableName(String period, String tableName) {
        if (period == null)
            return tableName;
        String suffix = calculateSuffix(period);
        return tableName + suffix;
    }

    private String calculateSuffix(String period) {
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
}
