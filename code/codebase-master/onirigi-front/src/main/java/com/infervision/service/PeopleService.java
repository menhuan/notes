package com.infervision.service;

import com.infervision.model.PeopleVo;

import java.util.List;

/**
 * @author: fruiqi
 * @date: 19-2-20 上午11:34
 * @version:1.0 用户接口
 **/
public interface PeopleService {


    /**
     * 查询用户列表
     * @param vo 用户对象
     * @return: java.util.List<com.infervision.model.PeopleVo>
     * @author: fruiqi
     * @date: 19-2-20 上午11:35
     */
    public List<PeopleVo> getPeoples(PeopleVo vo);
    
    /** 
     * 增加用户
     * @param vo	 增加用户
     * @return: com.infervision.model.PeopleVo 
     * @author: fruiqi
     * @date: 19-2-20 下午3:52
     */ 
    public PeopleVo addPeople(PeopleVo vo);


    /** 
     * 修改的对象
     * @param vo 需要修改的对象
     * @return: com.infervision.model.PeopleVo 
     * @author: fruiqi
     * @date: 19-2-20 下午4:48
     */ 
    public PeopleVo updatePeople(Integer id, PeopleVo vo );

    /** 
     *
     * @param id 删除
     * @return: void 
     * @author: fruiqi
     * @date: 19-2-20 下午4:49
     */ 
    public void deletePeople(Integer id );
}
