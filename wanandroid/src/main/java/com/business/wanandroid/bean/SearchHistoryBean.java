package com.business.wanandroid.bean;

import org.litepal.crud.LitePalSupport;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/24/024 18:22
 */
public class SearchHistoryBean extends LitePalSupport {
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
