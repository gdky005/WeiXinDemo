package wq.gdky005.wx;

import android.os.Environment;

import java.io.File;

/**
 * Created by WangQing on 2016/10/4.
 */

public class FileUtil {

    public static final String sayHelloFilePath = "sayGood.txt";
    public static final String phoneNumberFilePath = "phoneNumber.txt";
    public static final String tongJiDataFilePath = "tongJiData.txt";
    public static final String homeDir = "/0WX/";


    public static File getSDCard() {
        return new File(Environment.getExternalStorageDirectory(), homeDir);
    }

    public static File getSayHelloFile() {
        return new File(getSDCard(), sayHelloFilePath);
    }

    public static File getPhoneNumberFile() {
        return new File(getSDCard(), phoneNumberFilePath);
    }

    public static File saveTongJiData() {
        return new File(getSDCard(), tongJiDataFilePath);
    }
}
