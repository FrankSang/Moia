package com.franksang.dsapi.properties;

/**
 * Created by Administrator on 2015/9/29.
 */
//package com.franksang.dsapi.properties;

        import java.io.*;
        import java.text.SimpleDateFormat;
        import java.util.Date;

        import com.ibm.iis.spi.security.crypto.DecryptException;
        import com.ibm.iis.spi.security.crypto.EncryptException;
        import com.sun.jna.NativeLong;
        import com.sun.jna.Pointer;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

public class prop {
    private static Logger logger = LoggerFactory.getLogger(prop.class);
    // 系统换行符号
    public static final String line_separate = System
            .getProperty("line.separator");
    //日志目录
    public static final String logPath = "";
    // 默认日期格式 YYYY-MM-DD
    public static final String sysdate = new SimpleDateFormat("yyyy-MM-dd")
            .format(new Date());
    // 默认时间格式YYYY-MM-DD HH:mm:ss
    public static final String systime = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss").format(new Date());
    public static final String charset = "GBK";


    // FrangSang 2014-05-11 测试成功
    public static String getTimeStamp(NativeLong value) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
        String date = sdf.format(new Date(value.longValue() * 1000));
        return date;
    }

    // FrangSang 2014-05-11 测试成功
    public static void writeLogFile(String JobLog, String filename) {
        File file = new File(filename);
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(JobLog);
            bw.flush();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // FrangSang 2014-05-11  测试成功
    public static String GetPointer(Pointer p) {
        String returnvalue = "";
        String pValue = p.getString(0);
        String space = "        ";
        returnvalue = pValue + line_separate;
        int offset = 0;
        while (!pValue.isEmpty()) {
            offset = offset + pValue.length() + 1;
            pValue = p.getString(offset);
            if (!pValue.equals("")) {
                returnvalue = returnvalue + space + pValue + line_separate;
            }
        }
        returnvalue = returnvalue.substring(0, returnvalue.length()
                - line_separate.length());
        return returnvalue;
    }

    public static String getJobElapsed(int elapsed) {
        int seconds = elapsed % 60;
        int minutes = elapsed % 3600 / 60;
        int hour = (elapsed / 3600) % 60;
        return hour + ":" + minutes + ":" + seconds;
    }

    public static String encryp(final String text)
    {
        try {
            return com.ibm.iis.isf.security.crypto.CryptoHelper.encrypt(text.getBytes("UTF-8"));
        } catch (EncryptException e) {
            logger.error("EncryptException");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException");
            e.printStackTrace();
        }
        return null;
    }

    public static String decryp(final String encryptedText)
    {
        try {
            com.ibm.iis.isf.security.crypto.CryptoHelper.decrypt(encryptedText);
        }catch(DecryptException e)
        {
            logger.error("DecryptException");
            e.printStackTrace();
        }
        return null;
    }
}
