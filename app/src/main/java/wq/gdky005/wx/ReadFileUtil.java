package wq.gdky005.wx;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import okio.BufferedSource;
import okio.Okio;
import okio.Source;

/**
 * Created by WangQing on 2016/10/4.
 */

public class ReadFileUtil {

    public static String readFile(File file) {
        String content = null;


        if (!file.exists()) {

            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        Source source = null;
        BufferedSource bufferedSource = null;
        try {
            source = Okio.source(file);
            bufferedSource = Okio.buffer(source);

            content = bufferedSource.readString(Charset.forName("GB2312"));
//            content = bufferedSource.readUtf8();
            System.out.println("读取的数据是:" + content);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                source.close();
                bufferedSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return content;
    }

    /**
     * 读取问候的提示语
     *
     * @return
     */
    public static ArrayList readSayHelloFile() {
        return readSayHelloFile(FileUitl.getSayHelloFile());
    }

    public static ArrayList readSayHelloFile(File file) {
        String content = readFile(file);
        ArrayList list = getArrayList(content);
        return list;
    }

    /**
     * 读取 手机号的文件
     *
     * @return
     */
    public static ArrayList readPhoneNumberFile() {
        return readPhoneNumberFile(FileUitl.getPhoneNumberFile());
    }

    public static ArrayList readPhoneNumberFile(File file) {
        String content = readFile(file);
        ArrayList list = getArrayList(content);
        return list;
    }

    @NonNull
    private static ArrayList getArrayList(String content) {
        ArrayList list = new ArrayList();
        // 注意这里注意使用 Windows 的分隔符,用 mac 试试
        String splitStr = "\r\n";

        if (!content.contains(splitStr))
            splitStr = "\\n";

        String[] myList = content.split(splitStr);

        for (String number : myList) {

            number = number.trim();

            if (!TextUtils.isEmpty(number))
                list.add(number);
        }
        return list;
    }


}
