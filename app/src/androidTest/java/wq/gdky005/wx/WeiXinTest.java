package wq.gdky005.wx;

import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by WangQing on 16/9/28.
 */

public class WeiXinTest extends UiAutomatorTestCase {

    int waitTimeCount = 1000;

    int msgCount;
    Random random;
    Queue queue;
    ArrayList arrayList;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        random = new Random();
        arrayList = new ArrayList();
        queue = new LinkedList();

        arrayList.add("我很想认识你");
        arrayList.add("真希望和你做朋友");
        arrayList.add("冥冥中是一种约定,上天让我认识了你");
        arrayList.add("众里寻他千百度,蓦然回首遇见你");
        arrayList.add("曾经沧海难为水,除去巫山不是你。");

        queue.offer("18612116814");
        queue.offer("18612116824");
        queue.offer("18612116834");
        queue.offer("18612116844");
        queue.offer("18612116854");
        queue.offer("18612116864");


        msgCount = arrayList.size();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        pressBack();
        pressBack();
        pressHome();
    }

    public void testWeiXinAddUser() throws UiObjectNotFoundException {
        pressHome();

        //启动微信
        clickUIObject("微信");
        //点击搜索
        clickUIObject("搜索");
        //查找用户
        searchUser(queue.size());
    }

    /**
     * 查找用户
     * <p>
     * 递归查找用户
     */
    private void searchUser(int count) {
        waitTime();

        if (count <= 0) {
            return;
        } else {
            count--;
        }

        //这个手机号 可以用一个 队列存储,取出一个后,里面就少一个
        //输入 手机号
        inputTextUIObject("com.tencent.mm:id/fo", (String) queue.poll());
        //查找手机/QQ号 XXXX, 这个是布局
        searchPhoneNum();

        if (isExit("用户不存在")) {  //当前用户不存在
            pressBack();
//            请重新输入
            searchUser(count);
        } else if (isExit("操作过于频繁，请稍后再试。")) {
            //下次的时间是 上次时间 的2倍
            waitTimeCount += waitTimeCount;

            pressBack();
            searchUser(count);
        } else {
//            发消息
            if (isExit("发消息")) {
                pressBack();
//                请重新输入
                searchUser(count);
            } else {
                //添加用户到通讯录
                clickUIObject("添加到通讯录");
                //随机数,随机从 已经存在的 List 里面获取消息
                //发送验证申请,等对方通过, 写消息
                String sendMsg = (String) arrayList.get(random.nextInt(msgCount - 1));
                Log.i(TAG, "searchUser_sendMsg: "  + sendMsg);
                inputTextUIObject("com.tencent.mm:id/c5k", sendMsg);
                //发送消息
                clickUIObject("com.tencent.mm:id/fb");
                //发送完成返回
                pressBack();
//            请重新输入
                searchUser(count);
            }
        }
    }

    private void searchPhoneNum() {
        UiDevice mDevice = getUiDevice();
        UiObject textUiObject = mDevice.findObject(new UiSelector().resourceId("com.tencent.mm:id/aty").className("android.widget.LinearLayout"));
        if (textUiObject != null) {
            try {
                textUiObject.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private synchronized void waitTime() {
        try {
            Thread.sleep(waitTimeCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void inputTextUIObject(String obj, String text) {
        try {
            UiObject object1 = getUiObject(obj);
            if (object1 != null) {
                object1.clearTextField(); //清除文本框中的功能
                object1.setText(text);
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            // 控件没找到,暂不处理其他的东西
        }
    }

    //点击 msg 的控件
    private void clickUIObject(String msg) {
        try {
            UiObject app = getUiObject(msg);
            if (app != null)
                app.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            // 控件没找到,暂不处理其他的东西
        }
    }

    private UiObject getUiObject(String text) {
        UiDevice mDevice = getUiDevice();

        UiObject uiObject = null;

        UiObject textUiObject = mDevice.findObject(new UiSelector().text(text));
        boolean textState = textUiObject.exists();
        if (textState) {
            uiObject = textUiObject;
        } else {
            UiObject descriptionUiObject = mDevice.findObject(new UiSelector().description(text));
            boolean descriptionStatue = descriptionUiObject.exists();

            if (descriptionStatue) {
                uiObject = descriptionUiObject;
            } else {
                UiObject resourceUiObject = mDevice.findObject(new UiSelector().resourceId(text));
                boolean resourceStatue = resourceUiObject.exists();
                if (resourceStatue) {
                    uiObject = resourceUiObject;
                } else {
                    //TODO 如果以上都没找打,请填写这个
                }
            }

        }


        return uiObject;
    }

    private boolean isExit(String text) {
        UiObject object = getUiObject(text);
        if (object != null) {
            return object.exists();
        } else {
            return false;
        }

    }


    private UiObject getUiObjectFromText(String text) {
        UiDevice mDevice = getUiDevice();
        UiObject object = mDevice.findObject(new UiSelector().text(text));

        return object;
    }

    public void pressHome() {
        UiDevice mDevice = getUiDevice();
        mDevice.pressHome();
    }

    public void pressBack() {
        UiDevice mDevice = getUiDevice();
        mDevice.pressBack();
    }

}
