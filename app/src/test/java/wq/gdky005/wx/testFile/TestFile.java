package wq.gdky005.wx.testFile;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import wq.gdky005.wx.ReadFileUtil;

/**
 * Created by WangQing on 2016/10/4.
 */

public class TestFile {

    @Test
    public void testSayHello() {
        ArrayList arrayList = ReadFileUtil.readPhoneNumberFile(new File("/Users/WangQing/Desktop/test/phoneNumber.txt"));

        System.out.println(arrayList.size());

    }

}
