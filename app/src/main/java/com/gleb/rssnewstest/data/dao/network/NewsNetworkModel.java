package com.gleb.rssnewstest.data.dao.network;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class NewsNetworkModel {
    @Element(name = "title")
    private String title;

    @Element(name = "link")
    private String link;

    @Element(name = "description")
    private String image;

    @Element(name = "pubDate")
    private String pubDate;
    private String mode;
    private Long id;

    public NewsNetworkModel() {
    }

    public NewsNetworkModel(Long id, String title,
                            String link, String image,
                            String mode) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.image = image;
        this.mode = mode;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getMode() {
        return mode;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "NewsNetworkModel{" +
                "image='" + image + '\'' +
                '}';
    }
}
