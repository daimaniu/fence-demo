package cn.daimaniu.service;

import cn.daimaniu.dao.FenceMapper;
import cn.daimaniu.dao.UploadLogMapper;
import cn.daimaniu.form.FenceQueryForm;
import cn.daimaniu.model.Fence;
import cn.daimaniu.model.UploadLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author :chenqisheng
 * Date   :2016-07-09 19:36.
 */
@Service
public class FenceService {

    @Autowired
    FenceMapper fenceMapper;

    @Autowired
    UploadLogMapper uploadLogMapper;

    public void add(Fence fence) {
        fence.setCreated(System.currentTimeMillis());
        fenceMapper.insert(fence);
    }

    public void upload(UploadLog uploadLog) {
        uploadLogMapper.insert(uploadLog);
    }

    public void update(Fence fence) {
        fenceMapper.updateByPrimaryKey(fence);
    }

    public void delete(int id) {
        fenceMapper.deleteByPrimaryKey(id);
    }

    public List<Fence> query(FenceQueryForm regionQueryForm) {
        return fenceMapper.selectAll();
    }
}
