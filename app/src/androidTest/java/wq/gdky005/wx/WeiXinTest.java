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

        UiObject app = getUiObject("微信");
        if (app != null)
            app.click();

        UiObject object = getUiObject("搜索");
        if (object != null)
            object.click();

        UiObject object1 = getUiObject("com.tencent.mm:id/fo");
        if (object1 != null) {
            object1.clearTextField(); //清除文本框中的功能
            object1.setText("18612116813");
            object1.click();
        }


        UiObject object2 = getUiObject("com.tencent.mm:id/aty");
        if (object2 != null)
            object2.click();


        if (isExit("确定")) {
            pressBack();
//            请重新输入
        } else {
            UiObject addTXL = getUiObject("添加到通讯录");
            if (addTXL != null)
                addTXL.click();

            UiObject object3 = getUiObject("com.tencent.mm:id/c5k");
            if (object3 != null) {
                object3.clearTextField();
                object3.setText("很想认识你");
                object3.click();
            }

            UiObject object4 = getUiObject("com.tencent.mm:id/fb");
            if (object4 != null)
                object4.click();
            pressBack();

            //请重新输入
            UiObject object5 = getUiObject("com.tencent.mm:id/fo");
            if (object5 != null) {
                object5.clearTextField(); //清除文本框中的功能
                object5.setText("18612116812");
                object5.click();
            }


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
