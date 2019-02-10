package com.sgitg.common.http;

import com.alibaba.fastjson.JSON;
import com.yanzhenjie.nohttp.RequestMethod;

/**
 * 描述：
 *
 * @author 周麟
 * @created 2018/3/12 8:38
 */

public class EntityRequest<Entity> extends AbstractRequest<Entity> {
    private Class<Entity> aClazz;

    public EntityRequest(String url, RequestMethod requestMethod, Class<Entity> clazz) {
        super(url, requestMethod);
        this.aClazz = clazz;
    }

    @Override
    Entity getResult(String responseBody) throws Exception {
        return JSON.parseObject(responseBody, aClazz);
    }
}
