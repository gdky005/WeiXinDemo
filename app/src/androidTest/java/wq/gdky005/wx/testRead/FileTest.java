package wq.gdky005.wx.testRead;

import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import wq.gdky005.wx.ReadFileUtil;

/**
 * Created by WangQing on 2016/10/4.
 */

@MediumTest
@RunWith(AndroidJUnit4.class)
public class FileTest {

    @Test
    public void testFile() {
//        ArrayList arrayList = ReadFileUtil.readPhoneNumberFile();
        ArrayList arrayList = ReadFileUtil.readSayHelloFile();

        System.out.println(arrayList.size());
    }
}
