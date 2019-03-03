package com.wangdan.dream.persistence.orm.sql;

import com.wangdan.dream.persistence.orm.annotations.Column;
import com.wangdan.dream.persistence.orm.annotations.Table;
import com.wangdan.dream.persistence.orm.table.DynamicTableName;
import com.wangdan.dream.persistence.orm.table.TableNameUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityMetaData {
    private Class<?> entityClass;
    private List<EntityField> entityFieldList = new ArrayList<>();
    private EntityTable entityTable;
    private List<EntityField> primaryFieldList = new ArrayList<>();

    public EntityMetaData(Class<?> clazz) {
        this.entityClass = clazz;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public List<EntityField> getEntityFieldList() {
        return entityFieldList;
    }

    public EntityTable getEntityTable() {
        return entityTable;
    }

    public List<EntityField> getPrimaryFieldList() {
        return primaryFieldList;
    }

    public String getTableName() {
        return entityTable.getTableName();
    }

    public void initialize() {
        initializeTable();
        initializeFields();

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
                entityFieldList.add(entityField);
                if (column.isPrimaryKey())
                    primaryFieldList.add(entityField);
            }
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
}
