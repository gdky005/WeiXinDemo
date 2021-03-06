package wq.gdky005.wx;

import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by WangQing on 16/9/28.
 */

public class WeiXinTest extends UiAutomatorTestCase {


    /**
     * TODO 分钟数设置为 5分钟 到 10分钟, 随机获取
     * 分钟单位
     */
    int waitTimeCount = 1;

    int msgCount;
    Random random;
    Queue queue;
    ArrayList arrayList;

    private ArrayList alreadyList;
    private ArrayList noExitList;
    private ArrayList addList;

//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
//                    Toast.makeText(getInstrumentation().getTargetContext(), "Hello", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//    };

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        random = new Random();
        arrayList = new ArrayList();
        queue = new LinkedList();


        alreadyList = new ArrayList();
        noExitList = new ArrayList();
        addList = new ArrayList();


        waitTimeCount = getRandom(10, 5);

        try {
            arrayList = ReadFileUtil.readSayHelloFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (arrayList == null || arrayList.size() == 0) {
            if (arrayList == null) {
                arrayList = new ArrayList();
            }

            arrayList.add("我很想认识你");
            arrayList.add("真希望和你做朋友");
            arrayList.add("冥冥中是一种约定,上天让我认识了你");
            arrayList.add("众里寻他千百度,蓦然回首遇见你");
            arrayList.add("曾经沧海难为水,除去巫山不是你。");
        }

        try {
            queue = ReadFileUtil.readPhoneNumberFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (queue == null) {
            queue = new LinkedList();
        }


        msgCount = arrayList.size();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        //统计数据
        SaveFileUtil.saveTongJiDataFile(alreadyList, noExitList, addList);

        pressBack();
        pressBack();
        pressHome();
    }

    public void testWeiXinAddUser() throws UiObjectNotFoundException {
        pressHome();
        pressHome();
        pressHome();

        toast("启动自动化加人程序");

        //启动微信
        clickUIObject("微信");
        //点击搜索
        clickUIObject("搜索");

        //查找用户
        searchUser(queue.size());

    }

    private void toast(final String msg) {
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getInstrumentation().getTargetContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * 查找用户
     * <p>
     * 递归查找用户
     */
    private void searchUser(int count) {
        if (count <= 0) {
            return;
        } else {
            count--;
        }


        String phoneNumber = (String) queue.poll();

        alreadyList.add(phoneNumber);

        //这个手机号 可以用一个 队列存储,取出一个后,里面就少一个
        //输入 手机号
        inputTextUIObject("com.tencent.mm:id/fo", phoneNumber);

        toast("等待" + waitTimeCount + "分钟后,开始自动添加");
        waitTime();


        //查找手机/QQ号 XXXX, 这个是布局
        searchPhoneNum();

        if (isExit("用户不存在")) {  //当前用户不存在

            noExitList.add(phoneNumber);

            pressBack();
//            请重新输入
            searchUser(count);
        } else if (isExit("操作过于频繁，请稍后再试。")) {
            //下次的时间是 上次时间 的2倍
            waitTimeCount += waitTimeCount;


            toast("操作过于频繁,已经将下次间隔时间调整为:" + waitTimeCount + "分钟");

            pressBack();
            searchUser(count);
        } else if (isExit("添加到通讯录")) {
            addList.add(phoneNumber);

            //添加用户到通讯录
            clickUIObject("添加到通讯录");
            //随机数,随机从 已经存在的 List 里面获取消息
            //发送验证申请,等对方通过, 写消息
            String sendMsg = (String) arrayList.get(random.nextInt(msgCount - 1));
            Log.i(TAG, "searchUser_sendMsg: " + sendMsg);
            inputTextUIObject("com.tencent.mm:id/c5k", sendMsg);
            //发送消息
            clickUIObject("com.tencent.mm:id/fb");
            //发送完成返回
            pressBack();
//            请重新输入
            searchUser(count);
        } else {
//            发消息
            if (isExit("发消息")) {
                pressBack();

                toast("已经为您自动添加其他联系人");

//                请重新输入
                searchUser(count);
            } else {
                noExitList.add(phoneNumber);
                //发送完成返回
                pressBack();
//            请重新输入
                searchUser(count);
                toast("其他错误");
            }
        }
    }

    /**
     * 获取一个范围的 随机数
     *
     * @param max 最大值
     * @param min 最小值
     * @return
     */
    public int getRandom(int max, int min) {
        int random = (int) (min + (max - min) * Math.random());
        return random;
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
//          分钟数设置为 5分钟 到 30分钟, 随机获取
            Thread.sleep(waitTimeCount * 60 * 1000);
//            Thread.sleep(waitTimeCount * 1000);
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
