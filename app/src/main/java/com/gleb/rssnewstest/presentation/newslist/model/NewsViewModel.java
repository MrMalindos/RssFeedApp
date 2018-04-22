package com.gleb.rssnewstest.presentation.newslist.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsViewModel implements Parcelable {
    private Long id;
    private String title;
    private String link;
    private String image;
    private String mode;

    public NewsViewModel(Long id, String title,
                         String link, String image,
                         String mode) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.image = image;
        this.mode = mode;
    }

    protected NewsViewModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        title = in.readString();
        link = in.readString();
        image = in.readString();
        mode = in.readString();
    }

    public static final Creator<NewsViewModel> CREATOR = new Creator<NewsViewModel>() {
        @Override
        public NewsViewModel createFromParcel(Parcel in) {
            return new NewsViewModel(in);
        }

        @Override
        public NewsViewModel[] newArray(int size) {
            return new NewsViewModel[size];
        }
    };

    public Long getId() {
        return id;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(image);
        dest.writeString(mode);
    }

    @Override
    public String toString() {
        return "NewsViewModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", image='" + image + '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }
}
