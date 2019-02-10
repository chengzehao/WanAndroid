package com.sgitg.common.utils;

import com.yanzhenjie.nohttp.Logger;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

public class Base64Util {
    public static String encodeBase64String(String arg) {
        byte[] byteArg;
        try {
            if (arg == null) {
                return null;
            }
            byteArg = arg.getBytes("UTF-8");
            return new String(Base64.encodeBase64(byteArg));
        } catch (UnsupportedEncodingException e) {
            Logger.e(e.getMessage());
        }
        return null;
    }

    public static String decodeBase64String(String arg) {
        byte[] byteArg;
        try {
            if (arg == null) {
                return null;
            }
            byteArg = arg.getBytes("UTF-8");
            if (Base64.isArrayByteBase64(byteArg))
                return new String(Base64.decodeBase64(byteArg), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Logger.e(e.getMessage());
        }
        return arg;
    }

}
