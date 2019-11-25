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
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.InputStream;
import java.util.List;

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
    public void loadFromFile(InputStream fileInputStream, FormDataContentDisposition dataContentDisposition) throws Exception {
        logger.info("filePath : {}", dataContentDisposition.getFileName());
        String filePath = "./temp/" + dataContentDisposition.getFileName();
        FileUtils.save(fileInputStream, filePath);
        List<Record> recordList = loadRecordService.load(filePath);
        entityManager.save(recordList.toArray());
    }
}
