package com.sgitg.common.http;

import com.yanzhenjie.nohttp.RequestMethod;

/**
 * 描述：
 *
 * @author 周麟
 * @created 2018/3/12 9:23
 */

public class StringRequest extends AbstractRequest<String> {
    public StringRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    protected String getResult(String responseBody) throws Exception {
        return responseBody;
    }
}
