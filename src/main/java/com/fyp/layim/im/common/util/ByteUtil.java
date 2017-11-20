package com.fyp.layim.im.common.util;

import com.fyp.layim.im.common.LayimConst;
import org.tio.utils.json.Json;

import java.io.IOException;

/**
 * @author fyp
 * @crate 2017/11/20 7:07
 * @project SpringBootLayIM
 */
public class ByteUtil {
    /**
     * 转换成byte[]
     * */
    public static byte[] toBytes(Object obj) throws IOException{
        if(obj == null) {
            return null;
        }
        if(obj instanceof String){
            return ((String) obj).getBytes(LayimConst.CHAR_SET);
        }
        return Json.toJson(obj).getBytes(LayimConst.CHAR_SET);
    }

    /**
     * byte[] 转换成string
     * */
    public static String toText(byte[] bytes) throws IOException{
        if(bytes == null|| bytes.length == 0){
            return null;
        }
        return new String(bytes,LayimConst.CHAR_SET);
    }
}
