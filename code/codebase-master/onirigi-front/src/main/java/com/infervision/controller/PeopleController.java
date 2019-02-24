package com.infervision.controller;

import com.infervision.exception.CommonException;
import com.infervision.model.PeopleVo;
import com.infervision.service.PeopleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: fruiqi
 * @date: 19-2-20 上午10:41
 * @version:1.0 用户的控制端
 **/
@RestController
@RequestMapping("people")
@Api(value = "people", description = "用户控制端")
public class PeopleController {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(PeopleController.class);

    @Autowired
    PeopleService peopleService;


    /**
     * 根据用户实体内容 分类查询用户
     * 1. 在这里 vo层将查询条件封装成为一个实体或者
     * 参数较少可以直接使用RESTful的方式将其传参
     *
     *
     * @param people 用户实体
     * @return: com.infervision.model.People
     * @author: fruiqi
     * @date: 19-2-20 上午10:45
     */
    @GetMapping
    @ApiOperation(value = "获取用户列表信息", notes = "条件可以选择 用户名,公司等内容")
    public List<PeopleVo> getPeoples(PeopleVo people) throws CommonException {
        List<PeopleVo> peoples = peopleService.getPeoples(people);
        return peoples;
    }


    /**
     * 增加用户信息
     *
     * @param peopleVo 增加的用户信息
     * @return: com.infervision.model.PeopleVo
     * @author: fruiqi
     * @date: 19-2-20 下午3:31
     */
    @PostMapping
    @ApiOperation(value = "增加用户")
    public PeopleVo addPeople(@RequestBody  PeopleVo peopleVo) {
        PeopleVo result = peopleService.addPeople(peopleVo);
        return result;
    }

    /**
     * 修改对象
     * @param id       用户id
     * @param peopleVo 修改的对象
     * @return: com.infervision.model.PeopleVo
     * @author: fruiqi
     * @date: 19-2-20 下午4:06
     */
    @PutMapping("{id}")
    @ApiOperation(value="更新用户")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name = "id",value = "用户id",required = true)
    })
    public PeopleVo updatePeople(@PathVariable Integer id, @RequestBody PeopleVo peopleVo) {
        PeopleVo result = peopleService.updatePeople(id, peopleVo);
        return result;
    }


    @DeleteMapping("{id}")
    @ApiOperation(value="删除用户")
    @ApiImplicitParam(dataType = "string",name = "id",value = "用户id",required = true)
    public void deletePeople(@PathVariable Integer id){

        if (id != null){
            peopleService.deletePeople(id);
            logger.info("[INFO]  删除用户id  ");
        }


    }


}
