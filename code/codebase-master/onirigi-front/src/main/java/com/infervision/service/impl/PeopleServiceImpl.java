package com.infervision.service.impl;

import com.alibaba.fastjson.JSON;
import com.infervision.config.RedisOperation;
import com.infervision.dao.PeopleMapper;
import com.infervision.model.People;
import com.infervision.model.PeopleVo;
import com.infervision.service.PeopleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.infervision.constant.RedisKey.USER_LIST_KEY;

/**
 * @author: fruiqi
 * @date: 19-2-20 上午11:36
 * @version:1.0
 **/
@Service
public class PeopleServiceImpl implements PeopleService {


    private static final Logger logger = LoggerFactory.getLogger(PeopleServiceImpl.class);

    @Resource
    PeopleMapper peopleMapper;

    @Autowired
    RedisOperation operation;

    /**
     * 查询用户列表
     *
     * @param vo 用户对象
     * @return: java.util.List<com.infervision.model.PeopleVo>
     * @author: fruiqi
     * @date: 19-2-20 上午11:35
     */
    @Override
    public List<PeopleVo> getPeoples(PeopleVo vo) {
        People peop = new People();
        BeanUtils.copyProperties(vo, peop);

        List<String>  users = operation.popList(USER_LIST_KEY);


        List<People>  people = JSON.parseArray(users.toString(),People.class);
        if (people.size() == 0) {
            people = peopleMapper.selectPeoples(peop);
            users = JSON.parseArray(JSON.toJSONString(people),String.class);
            operation.pushList(USER_LIST_KEY,users);
        }

        final List<PeopleVo> result = new ArrayList<>(people.size());

        people.stream().forEach(peo -> {
            PeopleVo peopleVo = new PeopleVo();
            BeanUtils.copyProperties(peo, peopleVo);
            result.add(peopleVo);
        });


        return result;
    }

    /**
     * 增加用户
     *
     * @param vo 增加用户
     * @return: com.infervision.model.PeopleVo
     * @author: fruiqi
     * @date: 19-2-20 下午3:52
     */
    @Override
    public PeopleVo addPeople(PeopleVo vo) {
        People people = new People();
        BeanUtils.copyProperties(vo, people);
        int id = peopleMapper.insertSelective(people);
        return vo;
    }

    /**
     * @param id 用户id
     * @param vo
     * @return: com.infervision.model.PeopleVo
     * @author: fruiqi
     * @date: 19-2-20 下午4:50
     */
    @Override
    public PeopleVo updatePeople(Integer id, PeopleVo vo) {
        People people = new People();
        BeanUtils.copyProperties(vo, people);
        people.setId(id);
        peopleMapper.updateByPrimaryKey(people);
        return vo;
    }

    @Override
    public void deletePeople(Integer id) {
        int i = peopleMapper.deleteByPrimaryKey(id);
        if (i > 0) ;

    }
}
