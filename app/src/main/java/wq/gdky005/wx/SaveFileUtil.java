package wq.gdky005.wx;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by WangQing on 2016/10/4.
 */

public class SaveFileUtil {

    public static void saveTongJiDataFile(ArrayList alreadyList, ArrayList noExitList, ArrayList addList) {
        File file = FileUtil.saveTongJiData();

        //保存数据到文件中
        try {
            StringBuilder sb = new StringBuilder();

            addBlock(sb);
            sb.append("已经添加的数据总数为:" + alreadyList.size() + "\n");
            for (Object phoneNumber : alreadyList) {
                sb.append(phoneNumber + "\n");
            }

            addBlock(sb);
            sb.append("不存在的数据总数为:" + noExitList.size() + "\n");
            for (Object phoneNumber : noExitList) {
                sb.append(phoneNumber + "\n");
            }

            addBlock(sb);
            sb.append("已经自动添加好友的数据总数为:" + addList.size() + "\n");
            for (Object phoneNumber : addList) {
                sb.append(phoneNumber + "\n");
            }


            Sink sink = Okio.sink(file);
            BufferedSink bufferedSink = Okio.buffer(sink);
            bufferedSink.writeString(sb.toString(), Charset.forName("GB2312"));
//            bufferedSink.writeUtf8(text);
            bufferedSink.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addBlock(StringBuilder sb) {
        addLine(sb);
        addLine(sb);
        addLine(sb);
        addLine(sb);
        addLine(sb);
    }

    private static void addLine(StringBuilder sb) {
        sb.append("\r\n");
    }
}
