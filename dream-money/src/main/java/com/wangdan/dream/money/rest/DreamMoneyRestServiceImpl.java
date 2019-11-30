package com.wangdan.dream.money.rest;

import com.google.inject.spi.LinkedKeyBinding;
import com.wangdan.dream.commons.serviceProperties.RestServer;
import com.wangdan.dream.commons.serviceProperties.file.FileUtils;
import com.wangdan.dream.framework.ApplicationBase;
import com.wangdan.dream.framework.Service;
import com.wangdan.dream.framework.ServiceBase;
import com.wangdan.dream.money.DreamMoneyApplication;
import com.wangdan.dream.money.DreamMoneyRestService;
import com.wangdan.dream.money.LoadRecordService;
import com.wangdan.dream.money.domain.Record;
import com.wangdan.dream.persistence.orm.EntityManager;
import com.wangdan.dream.persistence.orm.filter.Condition;
import com.wangdan.dream.persistence.orm.filter.FilterExpress;
import com.wangdan.dream.persistence.orm.filter.FilterGroup;
import com.wangdan.dream.persistence.orm.filter.FilterType;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestServer(serverName = "default")
public class DreamMoneyRestServiceImpl extends ApplicationBase implements DreamMoneyRestService {
    @Service
    private LoadRecordService loadRecordService;
    @Service
    private EntityManager entityManager;

    public DreamMoneyRestServiceImpl() {
        super(null);
        injectApplicationService();
        start();
    }

    private void injectApplicationService() {
        DreamMoneyApplication.injector.getBindings().forEach(((key, binding) -> {
            if (binding instanceof LinkedKeyBinding) {
                Object instance = DreamMoneyApplication.injector.getInstance(key);
                logger.info("try to inject {} to serviceLocator", instance);
                if (instance instanceof ServiceBase) {
                    ((ServiceBase) instance).start();
                    this.addService(key.getTypeLiteral().getRawType(), (ServiceBase) instance);
                }
                logger.info("succeeded to inject {} to serviceLocator", instance);
            }
        }));
    }

    @Override
    public String getAccounts() {
        return "aa";
    }


    @Override
    public Map<String, Object> loadFromFile(InputStream fileInputStream, FormDataContentDisposition dataContentDisposition) throws Exception {
        logger.info("filePath : {}", dataContentDisposition.getFileName());
        String filePath = "./temp/" + dataContentDisposition.getFileName();
        FileUtils.save(fileInputStream, filePath);
        List<Record> recordList = loadRecordService.load(filePath);
        entityManager.save(recordList.toArray());

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("msg", "ok");
        resultMap.put("code", 200);
        return resultMap;
    }

    public static Date getMaxDateInMonth(int year, int month) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Date(calendar.getTimeInMillis());
    }

    public static Date getMinDateInMonth(int year, int month) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month - 1, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTimeInMillis());
    }

    @Override
    public Map<String, Object> loadRecordDetails(int page, int limit) {
        int year = 2019;
        int month = 3;
        Date maxDate = getMaxDateInMonth(year, month);
        Date minDate = getMinDateInMonth(year, month);

        FilterExpress maxFilterExpress = new FilterExpress("time", FilterType.LESS_OR_EQUAL, maxDate.getTime());
        FilterExpress minFilterExpress = new FilterExpress("time", FilterType.LARGER_OR_EQUAL, minDate.getTime());

        FilterGroup filterGroup = new FilterGroup(maxFilterExpress, minFilterExpress);
        Condition condition = new Condition();
        condition.setFilterGroup(filterGroup);
        List<Record> recordList = entityManager.query(Record.class, condition);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 0);
        resultMap.put("msg", "OK");
        resultMap.put("count", recordList.size());
        resultMap.put("data", recordList);
        return resultMap;
    }
}
