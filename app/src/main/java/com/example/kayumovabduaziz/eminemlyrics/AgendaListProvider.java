package com.example.kayumovabduaziz.eminemlyrics;

/**
 * Created by Kayumov Abduaziz on 6/3/2015.
 */
public class AgendaListProvider {

    private int pics;
    private String time;
    private String title;
    private String description;

    public AgendaListProvider(int pic,String tim,String tit,String des)
    {
        this.setPics(pic);
        this.setTitle(tit);
        this.setDescription(des);
        this.setTime(tim);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String tim) {
        this.time = tim;
    }

    public int getPics() {
        return pics;
    }

    public void setPics(int pics) {
        this.pics = pics;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
