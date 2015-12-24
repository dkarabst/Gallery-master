package com.grafixartist.gallery.vk.model;

import java.util.List;

/**
 * Created by Dmitriy on 10/12/2015.
 */
public class Response {
    private String count;

    private List<Items> items;

    public String getCount ()
    {
        return count;
    }

    public void setCount (String count)
    {
        this.count = count;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [count = "+count+", items = "+items+"]";
    }
}
