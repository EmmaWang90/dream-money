package com.wangdan.dream.money.money;

import com.wangdan.dream.framework.InjectService;
import com.wangdan.dream.framework.Service;
import com.wangdan.dream.framework.test.ServiceTestBase;
import com.wangdan.dream.money.Person;
import com.wangdan.dream.persistence.orm.EntityManager;
import com.wangdan.dream.persistence.orm.filter.Condition;
import com.wangdan.dream.persistence.orm.impl.EntityManagerImpl;
import com.wangdan.dream.persistence.orm.impl.connection.DatabaseConnectionFactory;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

@InjectService(DatabaseConnectionFactory.class)
@InjectService(accessClass = EntityManager.class, value = EntityManagerImpl.class)
public class TestEntityManager extends ServiceTestBase {
    @Service
    private DatabaseConnectionFactory databaseConnectionFactory;
    @Service
    private EntityManager entityManager;
    @Test
    public void test() throws SQLException {
        Person person = new Person();
        person.setId(2);
        person.setName("aa");
        person.setMoney(5.0);
        entityManager.save(person);
        entityManager.save(person);
        entityManager.query(Person.class, new Condition());
    }
}
