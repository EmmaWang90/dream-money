package com.wangdan.dream.money.load;

import com.wangdan.dream.commons.serviceProperties.BeanUtils;
import com.wangdan.dream.commons.serviceProperties.document.CvsDocumentUtil;
import com.wangdan.dream.commons.serviceProperties.document.name;
import com.wangdan.dream.framework.ServiceBase;
import com.wangdan.dream.money.LoadRecordService;
import com.wangdan.dream.money.domain.Record;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadRecordServiceImpl extends ServiceBase implements LoadRecordService {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    public LoadRecordServiceImpl(ServiceBase parent) {
        super(parent);
    }

    public Map<String, Field> getNameFieldMap() {
        Field[] fields = Record.class.getDeclaredFields();
        Map<String, Field> nameFieldMap = new HashMap<>();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Annotation annotation = field.getAnnotation(name.class);
            if (annotation != null)
                nameFieldMap.put(((name) annotation).value(), field);
        }
        return nameFieldMap;
    }

    @Override
    public List<Record> load(String fileName) {
        List<String[]> dataList = CvsDocumentUtil.load(fileName);
        String[] columnNameArray = dataList.get(0);
        columnNameArray[7] = "子内容";
        columnNameArray[10] = null;
        List<Record> recordList = new ArrayList<>();
        for (int i = 1; i < dataList.size(); i++)
            recordList.add(new Record());
        Map<String, Field> nameFieldMap = getNameFieldMap();
        for (int i = 0; i < columnNameArray.length; i++) {
            String columnName = columnNameArray[i];
            Field field = nameFieldMap.get(columnName);
            if (field != null) {
                field.setAccessible(true);
                Class fieldType = field.getType();
                if (fieldType != String.class) {
                    if (fieldType == Integer.class
                            || fieldType == Double.class
                            || fieldType == Long.class) {
                        for (int j = 1; j < dataList.size(); j++) {
                            String valueString = dataList.get(j)[i];
                            valueString = valueString.replace(",", "");
                            Object value = BeanUtils.invoke(fieldType, "valueOf", valueString);
                            try {
                                field.set(recordList.get(j - 1), value);
                            } catch (IllegalAccessException e) {
                                logger.error(e.getMessage());
                            }
                        }
                    } else if (fieldType == Date.class) {
                        for (int j = 1; j < dataList.size(); j++) {
                            String valueString = dataList.get(j)[i];
                            try {
                                Date date = simpleDateFormat.parse(valueString);
                                field.set(recordList.get(j - 1), date);
                            } catch (Exception e) {
                                logger.error(e.getMessage());
                            }
                        }
                    } else if (Enum.class.isAssignableFrom(fieldType)) {
                        for (int j = 1; j < dataList.size(); j++) {
                            String valueString = dataList.get(j)[i];
                            try {
                                field.set(recordList.get(j - 1), BeanUtils.invoke(fieldType, "parseValue", valueString));
                            } catch (IllegalAccessException e) {
                                logger.error(e.getMessage());
                            }
                        }
                    }
                } else {
                    for (int j = 1; j < dataList.size(); j++) {
                        {
                            String valueString = dataList.get(j)[i];
                            try {
                                field.set(recordList.get(j - 1), valueString);
                            } catch (IllegalAccessException e) {
                                logger.error(e.getMessage());
                            }
                        }
                    }
                }
            } else
                logger.error("failed to get field for {}", columnName);
        }
        return recordList;
    }
}
