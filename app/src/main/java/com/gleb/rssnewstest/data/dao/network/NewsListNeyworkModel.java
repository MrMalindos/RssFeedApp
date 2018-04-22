package com.gleb.rssnewstest.data.dao.network;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "rss", strict = false)
public class NewsListNeyworkModel {
    @Element(name = "title")
    @Path("channel")
    private String channelTitle;

    @ElementList(inline = true, required = false)
    @Path("channel")
    private List<NewsNetworkModel> newsNetworkModelList;

    public NewsListNeyworkModel() {
    }

    public NewsListNeyworkModel(String channelTitle, List<NewsNetworkModel> newsNetworkModelList) {
        this.channelTitle = channelTitle;
        this.newsNetworkModelList = newsNetworkModelList;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public List<NewsNetworkModel> getNewsNetworkModelList() {
        if (newsNetworkModelList == null) {
            return new ArrayList<>();
        }
        return newsNetworkModelList;
    }

    public void setNewsNetworkModelList(List<NewsNetworkModel> newsNetworkModelList) {
        this.newsNetworkModelList = newsNetworkModelList;
    }
}
