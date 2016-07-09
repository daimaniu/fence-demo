package cn.daimaniu.dao;

import cn.daimaniu.model.UploadLog;

public interface UploadLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UploadLog record);

    int insertSelective(UploadLog record);

    UploadLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UploadLog record);

    int updateByPrimaryKey(UploadLog record);
}