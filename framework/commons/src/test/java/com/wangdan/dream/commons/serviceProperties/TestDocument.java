package com.wangdan.dream.commons.serviceProperties;

import com.wangdan.dream.commons.serviceProperties.document.CvsDocumentUtil;
import com.wangdan.dream.commons.serviceProperties.file.FileUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDocument {
    @Test
    public void test() {
        String filePath = "./src/test/resources/2019-01-01 _ 12-31.xls";
        String fileContent = FileUtils.read(filePath);
        System.out.println(fileContent);
        List<String[]> dataList = CvsDocumentUtil.load(filePath);
        assertEquals(dataList.size(), 536);
    }
}
