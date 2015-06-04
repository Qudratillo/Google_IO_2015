package com.example.kayumovabduaziz.eminemlyrics;

/**
 * Created by Kayumov Abduaziz on 5/30/2015.
 */
public class MainListProvider {
    private int pics;
    private String main_list_title;

    public MainListProvider(int pic,String title){
        this.setPics(pic);
        this.setMain_list_title(title);
    }

    public String getMain_list_title() {
        return main_list_title;
    }

    public void setMain_list_title(String main_list_title) {
        this.main_list_title = main_list_title;
    }

    public int getPics() {
        return pics;
    }

    public void setPics(int pics) {
        this.pics = pics;
    }
}
