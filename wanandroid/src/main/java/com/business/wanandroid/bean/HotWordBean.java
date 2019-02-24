package com.business.wanandroid.bean;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/24/024 13:43
 */
public class HotWordBean implements Serializable {
    /**
     * id : 6
     * link :
     * name : 面试
     * order : 1
     * visible : 1
     */
    private int id;
    private String link;
    private String name;
    private int order;
    private int visible;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
}
