package com.infervision.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

/**
 * @author: fruiqi
 * @date: 19-2-19 下午5:46
 * @version:1.0
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "NameVo" ,description=" 名字展示" )
public class NameVo extends NameDto {



}
