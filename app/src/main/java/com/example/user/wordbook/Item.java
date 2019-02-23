package com.example.user.wordbook;

/**
 * Created by User on 12/1/2017.
 */

public class Item {

    private String type,defination,example;

    public Item(String t,String d,String e) {
        type=t;
        defination=d;
        example=e;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefination() {
        return defination;
    }

    public void setDefination(String defination) {
        this.defination = defination;
    }
    public void setExample(String example) {
        this.example = example;
    }

    public String getExample() {
        return example;
    }
}
