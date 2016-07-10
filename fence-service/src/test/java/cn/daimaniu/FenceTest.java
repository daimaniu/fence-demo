package cn.daimaniu;

import cn.daimaniu.component.FenceComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Author :chenqisheng
 * Date   :2016-07-09 20:22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-service.xml")
public class FenceTest {
    @Autowired
    FenceComponent fenceComponent;

    @Test
    public void inFenceTest() {
        //浙大玉泉校区 120.123077,30.263842
        boolean isInYQ = fenceComponent.checkInFence(120.123077, 30.263842);
        assertTrue(isInYQ);
        //浙大紫金港校区 120.086634,30.303638
        boolean isInZJG = fenceComponent.checkInFence(120.086634, 30.303638);
        assertTrue(isInZJG);

        //五洲国际 120.096333,30.294223
        boolean isInWZGJ = fenceComponent.checkInFence(120.096333, 30.294223);
        assertFalse(isInWZGJ);
    }
}
