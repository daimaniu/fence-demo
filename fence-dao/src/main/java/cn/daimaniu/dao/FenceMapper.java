package cn.daimaniu.dao;

import cn.daimaniu.model.Fence;

import java.util.List;
import java.util.Map;

public interface FenceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Fence record);

    int insertSelective(Fence record);

    Fence selectByPrimaryKey(Integer id);

    List<Fence> selectAll();

    int countList(Map<String, Object> qm);

    int updateByPrimaryKeySelective(Fence record);

    int updateByPrimaryKey(Fence record);
}