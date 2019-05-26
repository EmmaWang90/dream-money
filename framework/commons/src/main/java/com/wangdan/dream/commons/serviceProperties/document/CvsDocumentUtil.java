package com.wangdan.dream.commons.serviceProperties.document;

import com.wangdan.dream.commons.serviceProperties.file.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CvsDocumentUtil {
    public static final Logger logger = LoggerFactory.getLogger(CvsDocumentUtil.class);

    private static Element getTBody(Element element) {
        Elements tbodyElement = element.getElementsByTag("tbody");
        if (tbodyElement != null)
            return tbodyElement.first();
        Elements elements = element.children();
        for (Element child : elements) {
            Element tempElement = getTBody(child);
            if (tempElement != null)
                return tempElement;
        }
        return null;
    }

    public static <T> List<String[]> load(String filePath) {
        String fileContent = FileUtils.read(filePath);
        Document document = Jsoup.parse(fileContent);
        Element bodyElement = document.body();
        Element tbodyElement = getTBody(bodyElement);
        Node node = tbodyElement.child(0);
        int columnSize = ((Element) node).children().size();
        List<String[]> dataList = new ArrayList<>();
        for (int i = 0; i < tbodyElement.children().size(); i++) {
            String[] columnContentArray = new String[columnSize];
            node = tbodyElement.child(i);
            for (int j = 0; j < ((Element) node).children().size(); j++) {
                Element element = ((Element) node).child(j);
                if (element.childNodeSize() > 0)
                    columnContentArray[j] = ((Element) node).child(j).childNode(0).toString();
            }
            dataList.add(columnContentArray);
        }
        return dataList;
    }
}
