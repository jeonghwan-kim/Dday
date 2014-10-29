package com.example.jeonghwan.dday;

import java.util.Date;

/**
 * Created by jeonghwan on 14. 10. 25..
 */
public class Data {

    private int id;
    private Date date;
    private String title;
    private Date created;

    public Data(){}

    public Data(int id, Date date, String title, Date created) {
        super();
        this.id = id;
        this.date = date;
        this.title = title;
        this.created = created;
    }

}
