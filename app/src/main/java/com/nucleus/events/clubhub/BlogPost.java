package com.nucleus.events.clubhub;


import java.util.Date;

public class BlogPost {

    public String club_id,image_uri, desc,image_thumb,BlogPostId;
    public Date timeStamp;


    public BlogPost(){}

    public BlogPost(String user_id, String image_uri, String desc, String image_thumb, Date timeStamp) {
        this.club_id = user_id;
        this.image_uri = image_uri;
        this.desc = desc;
        this.image_thumb = image_thumb;
        this.timeStamp = timeStamp;

    }

    public String getClub_id() {
        return club_id;
    }

    public void setClub_id(String club_id) {
        this.club_id = club_id;
    }

    public String getImage_uri() {
        return image_uri;
    }
    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public void setImage_thumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

}
