package com.wangdan.dream.persistence.orm.sql;

import com.wangdan.dream.persistence.orm.annotations.Column;
import com.wangdan.dream.persistence.orm.annotations.Index;
import com.wangdan.dream.persistence.orm.annotations.IndexList;
import com.wangdan.dream.persistence.orm.annotations.Table;
import com.wangdan.dream.persistence.orm.table.DynamicTableName;
import com.wangdan.dream.persistence.orm.table.TableNameUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class EntityMetaData<T> {
    private Class<T> entityClass;
    private List<String> columnNameList = new ArrayList<>();
    private EntityTable entityTable;
    private Map<String, EntityField> entityFieldMap = new HashMap<>();
    private List<String> indexStringList = null;
    private Map<String, EntityField> primaryFieldMap = new HashMap<>();

    public List<String> getColumnNameList() {
        return columnNameList;
    }

    public EntityMetaData(Class<T> clazz) {
        this.entityClass = clazz;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public Map<String, EntityField> getEntityFieldMap() {
        return entityFieldMap;
    }

    public List<String> getEntityFieldStringList() {
        return entityFieldMap.values().stream().map(EntityField::getCreateFieldString).collect(toList());
    }

    public List<String> getIndexStringList() {
        return indexStringList;
    }

    public String getTableName() {
        return entityTable.getTableName().toLowerCase();
    }

    public void initialize() {
        initializeTable();
        initializeFields();
        initializeIndexList();
    }

    private void initializeFields() {
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            Column column = field.getDeclaredAnnotation(Column.class);
            if (column != null) {
                EntityField entityField = new EntityField();
                entityField.setFieldName(column.value().isEmpty() ? field.getName() : column.value());
                entityField.setClazz(field.getType());
                entityField.setField(field);
                entityField.setColumn(column);
                entityFieldMap.put(entityField.getFieldName(), entityField);
                columnNameList.add(entityField.getFieldName());
                if (column.isPrimaryKey())
                    primaryFieldMap.put(entityField.getFieldName(), entityField);
            }
        }
    }

    private void initializeIndexList() {
        List<String> indexStringList = new ArrayList<>();
        IndexList indexList = entityClass.getAnnotation(IndexList.class);
        if (indexList != null && indexList.value() != null)
            indexStringList.addAll(Arrays.<String>asList(indexList.value()));
        for (Field field : entityClass.getDeclaredFields()) {
            Index index = field.getDeclaredAnnotation(Index.class);
            if (index != null && index.value() != null)
                indexStringList.add(index.value());
        }

    }

    private void initializeTable() {
        Table table = entityClass.getDeclaredAnnotation(Table.class);
        String tableName = !table.value().isEmpty() ? table.value() : entityClass.getSimpleName();
        Class<?> tableNameClass = table.tableClass();
        entityTable = new EntityTable(tableName);
        if (tableNameClass.equals(DynamicTableName.class))
            entityTable.setGetNameFunction((period, name) -> TableNameUtil.getInstance().getTableName(period, name));
    }

    public T newInstance() throws IllegalAccessException, InstantiationException {
        return entityClass.newInstance();
    }
}
