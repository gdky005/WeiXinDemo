package wq.gdky005.wx;

import org.junit.Test;

/**
 * Created by WangQing on 2016/10/4.
 */

public class TestRadom {
    int max = 30;
    int min = 5;

    @Test
    public void testRandom() {

        for (int i = 0; i < 50; i++) {
            int random = (int) (min+(max-min)*Math.random());
            System.out.println("这次随机数是:"+random);

        }



    }
}
