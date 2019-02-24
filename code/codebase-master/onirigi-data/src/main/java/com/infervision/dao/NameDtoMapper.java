package com.infervision.dao;

import com.infervision.model.NameDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NameDtoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NameDto record);

    int insertSelective(NameDto record);

    NameDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NameDto record);

    int updateByPrimaryKey(NameDto record);
    
    /**
     * 根据条件查询列表信息
     * @author fruiqi
     * @description //TODO
     * @date 19-2-13 下午4:15 
     * @param dto
     * @return java.util.List<com.infervision.model.NameDto>
     **/
    List<NameDto> selectNamesByName(NameDto dto);
}