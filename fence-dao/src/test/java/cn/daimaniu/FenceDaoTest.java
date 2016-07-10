package cn.daimaniu;

import cn.daimaniu.dao.FenceMapper;
import cn.daimaniu.model.Fence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Author :chenqisheng
 * Date   :2016-07-10 14:29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-dao.xml")
public class FenceDaoTest {
    @Autowired
    FenceMapper fenceMapper;

    @Test
    public void selectTest() {
        List<Fence> fenceList = fenceMapper.selectAll();
    }
}
