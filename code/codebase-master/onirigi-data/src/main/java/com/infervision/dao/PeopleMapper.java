package com.infervision.dao;

import com.infervision.model.People;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface PeopleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(People record);

    int insertSelective(People record);

    People selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(People record);

    int updateByPrimaryKey(People record);
    
    /** 
     * 查询用户列表
     * @param people
     * @return: java.util.List<com.infervision.model.People> 
     * @author: fruiqi
     * @date: 19-2-20 上午11:40
     */ 
    List<People> selectPeoples(People people);
}