/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.util;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;


/**
 * <pre>
 * HTTP协议请求工具类。
 * </pre>
 *
 * @author keshijun@foresee.com.cn
 * @date 2017年2月7日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
public class HttpRequestUtil {

    /**
     * 向第三方系统发送请求
     */
    public static String requestCall(String url, String requestXml) {
        URL u;
        byte[] b = null;
        try {
            u = new URL(url);
            URLConnection uc = u.openConnection();
            uc.setDoOutput(true);
            uc.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            PrintWriter pw = new PrintWriter(uc.getOutputStream());
            pw.println(requestXml);
            pw.close();
            InputStream is = uc.getInputStream();
            b = readStream(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new String(b);
    }

    /**
     * 读取流
     * 
     * @param inStream
     * @return 字节数组
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        byte[] result = outSteam.toByteArray();
        outSteam.close();
        inStream.close();
        return result;
    }

}
