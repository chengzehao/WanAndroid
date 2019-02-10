package com.business.user.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/7/24/024 22:09
 */
public class UserBean implements Serializable {
    private int id;
    private String username;
    private String password;
    private String email;
    private String token;
    private String icon;
    private int type;
    private List<Integer> collectIds;
    private List<Integer> chapterTops;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Integer> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(List<Integer> collectIds) {
        this.collectIds = collectIds;
    }

    public List<Integer> getChapterTops() {
        return chapterTops;
    }

    public void setChapterTops(List<Integer> chapterTops) {
        this.chapterTops = chapterTops;
    }
}
