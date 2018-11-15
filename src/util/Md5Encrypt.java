package util;

import java.security.MessageDigest;

/**
 * Created by ASUS on 2018/3/19.
 */
public class Md5Encrypt {
    public  static  String  Encrypt(String s)throws Exception{
        MessageDigest md5=MessageDigest.getInstance("MD5");
        sun.misc.BASE64Encoder base64Encoder = new sun.misc.BASE64Encoder();
        return base64Encoder.encode(md5.digest(s.getBytes("utf-8")));

    }

}
