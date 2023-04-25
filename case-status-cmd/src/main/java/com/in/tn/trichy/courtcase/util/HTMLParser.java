package com.in.tn.trichy.courtcase.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class HTMLParser {
    private Logger log = LoggerFactory.getLogger(HTMLParser.class);
    private String data;
    private File file;
    protected HTMLParser(String data){
        this.data =data;
    }
    protected HTMLParser(File file){
        this.file=file;
    }
    public Document getHTMLDoc(){
        Document doc=null;
            try {
                if(data ==null|| data.isEmpty()) {
                    doc = Jsoup.parse(file, "UTF-8");
                }
                else
                    doc=Jsoup.parse(data, "UTF-8");
            } catch (IOException e) {

                log.error("IO Exception Occured on parsing HTMl Doc");
                e.printStackTrace();

            }
        return doc;
    }
    public Elements cssSelect(String cssQuery){
        return getHTMLDoc().select(cssQuery);
    }
    public Elements xpathSelect(String xpath){
        return getHTMLDoc().selectXpath(xpath);
    }
    protected abstract Object parseHTMLResponse(ArrayList<String> paramList);
}
