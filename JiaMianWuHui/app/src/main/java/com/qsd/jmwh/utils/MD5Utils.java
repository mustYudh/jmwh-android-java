package com.qsd.jmwh.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    public static String string2MD5(String inStr) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            try {
                md.update(inStr.getBytes("UTF8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer(200);
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset] & 0xff;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
