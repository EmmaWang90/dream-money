package com.wangdan.dream.money.rest;

import com.wangdan.dream.commons.serviceProperties.RestServer;
import com.wangdan.dream.framework.ServiceBase;
import com.wangdan.dream.money.DreamMoneyRestService;

@RestServer(serverName = "default")
public class DreamMoneyRestServiceImpl extends ServiceBase implements DreamMoneyRestService {

    public DreamMoneyRestServiceImpl() {
        super(null);
    }

    @Override
    public String getAccounts() {
        return "aa";
    }
}
