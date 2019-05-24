package com.wangdan.dream.money.money;

import com.wangdan.dream.framework.InjectService;
import com.wangdan.dream.framework.Service;
import com.wangdan.dream.framework.test.ServiceTestBase;
import com.wangdan.dream.money.Person;
import com.wangdan.dream.persistence.orm.DataBaseType;
import com.wangdan.dream.persistence.orm.EntityManager;
import com.wangdan.dream.persistence.orm.EntityTableManager;
import com.wangdan.dream.persistence.orm.filter.Condition;
import com.wangdan.dream.persistence.orm.impl.EntityManagerImpl;
import com.wangdan.dream.persistence.orm.impl.connection.DatabaseConnectionFactory;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@InjectService(DatabaseConnectionFactory.class)
@InjectService(accessClass = EntityManager.class, value = EntityManagerImpl.class)
public class TestEntityManager extends ServiceTestBase {
    @Service
    private DatabaseConnectionFactory databaseConnectionFactory;
    @Service
    private EntityManager entityManager;

    @Before
    public void before() {
        EntityManagerImpl entityManagerImpl = (EntityManagerImpl) entityManager;
        EntityTableManager entityTableManager = (EntityTableManager) entityManagerImpl.getService(EntityTableManager.class);
        entityTableManager.clearTable(DataBaseType.POSTGRESQL, Person.class);
    }
    @Test
    public void test() throws SQLException {
        before();
        Person person = new Person();
        person.setId(2);
        person.setName("aa");
        person.setMoney(5.0);
        entityManager.save(person);
        List<Person> personList = entityManager.query(Person.class, new Condition());
        assertNotNull(personList);
        assertEquals(personList.size(), 1);
        assertEquals(person.getId(), personList.iterator().next().getId());
        entityManager.modify(person);
    }
}
