package com.sgitg.common.http;

import com.alibaba.fastjson.JSON;
import com.yanzhenjie.nohttp.RequestMethod;

import java.util.ArrayList;

/**
 * 描述：
 *
 * @author 周麟
 * @created 2018/3/12 8:41
 */

public class EntityListRequest<Entity> extends AbstractRequest<ArrayList<Entity>> {
    private Class<Entity> aClazz;

    public EntityListRequest(String url, RequestMethod requestMethod, Class<Entity> clazz) {
        super(url, requestMethod);
        this.aClazz = clazz;
    }

    @Override
    ArrayList<Entity> getResult(String responseBody) throws Exception {
        return (ArrayList<Entity>) JSON.parseArray(responseBody, aClazz);
    }
}
