package wq.gdky005.wx;

import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

/**
 * Created by WangQing on 16/9/28.
 */

public class WeiXinTest extends UiAutomatorTestCase {

    public void testWeiXinAddUser() throws UiObjectNotFoundException {
        pressHome();

        //启动微信
        clickUIObject("微信");
        //点击搜索
        clickUIObject("搜索");
        //输入 手机号
        inputTextUIObject("com.tencent.mm:id/fo", "18612116824");
        //查找手机/QQ号 XXXX
        clickUIObject("com.tencent.mm:id/aty");

        if (isExit("用户不存在")) {  //当前用户不存在
            pressBack();
//            请重新输入
        } else {
//            发消息
            if (isExit("发消息")) {
                pressBack();
//                请重新输入
            } else {
                //添加用户到通讯录
                clickUIObject("添加到通讯录");
                //发送验证申请,等对方通过, 写消息
                inputTextUIObject("com.tencent.mm:id/c5k", "很想认识你");
                //发送消息
                clickUIObject("com.tencent.mm:id/fb");
                //发送完成返回
                pressBack();

//            请重新输入

//            //重新输入 手机号,添加新用户
//            inputTextUIObject("com.tencent.mm:id/fo", "18612116834");
            }
        }
    }

    private void inputTextUIObject(String obj, String text) {
        try {
            UiObject object1 = getUiObject(obj);
            if (object1 != null) {
                object1.clearTextField(); //清除文本框中的功能
                object1.setText(text);
                object1.click();
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
