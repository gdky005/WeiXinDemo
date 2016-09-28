package wq.gdky005.wx;

import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.widget.Toast;

/**
 * Created by WangQing on 16/9/28.
 */

public class WeiXinSearchTest extends UiAutomatorTestCase {


    public void testStartWeiXin() {
        try {
            UiObject app = getUiObjectFromText("微信");
            app.click();
        } catch (UiObjectNotFoundException e) {
            showError(e);
        } catch (Exception e) {
            showError(e);
        } finally {
        }


//        UiObject app = mDevice.findObject(new UiSelector().text("电台"));
//        UiObject appSplash = mDevice.findObject(new UiSelector().resourceId("com.itings" +
//                ".myradio:id/flash_skip_textView"));
//        UiObject liveBtn = mDevice.findObject(new UiSelector().text("我的"));
//        UiObject myLive = mDevice.findObject(new UiSelector().resourceId("com.itings.myradio:id/title_left_imageView"));
//        UiObject living = mDevice.findObject(new UiSelector().text("开播"));
//
//        UiObject podcastLive = mDevice.findObject(new UiSelector().text("直播中"));

//        UiObject living = mDevice.findObject(new UiSelector().resourceId("com.itings" +
//                ".myradio:id/live_auchor_left_layout"));
//        UiObject livingHeart = mDevice.findObject(new UiSelector().resourceId("com.itings" +
//                ".myradio:id/favorHeart"));


//        app.click();
//        appSplash.click();
//        liveBtn.click();
//        myLive.click();
//        living.click();
//        podcastLive.click();

//        for (int i = 0; i < 10; i++) {
//            livingHeart.click();
//        }


    }

    public void testClickSearch() throws UiObjectNotFoundException {
        UiObject object = getUiObjectFromDescription("搜索");
        object.click();
    }


    public void testClickEditText() throws UiObjectNotFoundException {
        UiObject object = getUiObjectFromResourceId("com.tencent.mm:id/fo");
//        object.clearTextField(); //清除文本框中的功能
        object.setText("18612116814");
        object.click();
    }

    public void testClickSearchPhone() throws UiObjectNotFoundException {
        UiObject object = getUiObjectFromResourceId("com.tencent.mm:id/aty");
        object.click();
    }

    public void testBackPress() throws UiObjectNotFoundException {
        UiObject object = getUiObjectFromDescription("返回");
        object.click();
    }

    public void testExit() throws UiObjectNotFoundException {
        boolean exit = isExit("确定");
        System.out.println(exit);
    }

    public void testAddUser() throws UiObjectNotFoundException {

        UiObject object = getUiObjectFromText("添加到通讯录");
        object.click();
    }

    public void testAuthorUser() throws UiObjectNotFoundException {

        UiObject object = getUiObjectFromText("添加到通讯录");
        object.click();
    }

    public void testInputInfo() throws UiObjectNotFoundException {

        UiObject object = getUiObjectFromResourceId("com.tencent.mm:id/c5k");
        object.clearTextField();
        object.setText("很想认识你");
        object.click();
    }

    public void testSendMsg() throws UiObjectNotFoundException {
        UiObject object = getUiObjectFromResourceId("com.tencent.mm:id/fb");
        object.click();
    }

    private boolean isExit(String text) {
        UiObject object = getUiObjectFromText(text);
        return object.exists();
    }


    private UiObject getUiObjectFromText(String text) {
        UiDevice mDevice = getUiDevice();
        UiObject object = mDevice.findObject(new UiSelector().text(text));

        return object;
    }

    private UiObject getUiObjectFromDescription(String text) {
        UiDevice mDevice = getUiDevice();
        UiObject object = mDevice.findObject(new UiSelector().description(text));

        return object;
    }

    private UiObject getUiObjectFromResourceId(String text) {
        UiDevice mDevice = getUiDevice();
        UiObject object = mDevice.findObject(new UiSelector().resourceId(text));

        return object;
    }


    private void showError(Exception e) {
        e.printStackTrace();
        String msg = e.getMessage();
        System.out.println(msg);


        Toast.makeText(getInstrumentation().getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
