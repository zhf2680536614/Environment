package com.atey.dto;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class PageDto<V> {

    @ApiModelProperty(value = "总数")
    private Long total;
    @ApiModelProperty(value = "页码总数")
    private Long pages;
    @ApiModelProperty(value = "数据VO集合")
    private List<V> list;

    //返回空集合
    public static <V,P> PageDto<V> empty(Page<P> p){
        return new PageDto<>(p.getTotal(),p.getPages(), Collections.emptyList());
    }

    public static <V,P> PageDto<V> of(Page<P> p,Class<V> voClass){
        List<P> records = p.getRecords();
        if(records == null || records.isEmpty()){
            //无数据，返回空结果
            return empty(p);
        }
        //数据转换
        List<V> voList = BeanUtil.copyToList(records, voClass);
        return new PageDto<>(p.getTotal(),p.getPages(),voList);
    }

    //自定义转换规则
    public static <V,P> PageDto<V> of(Page<P> p, Function<P,V> convertor){
        List<P> records = p.getRecords();
        if(BeanUtil.isEmpty(records)){
            //无数据，返回空结果
            return empty(p);
        }
        //数据转换
        List<V> voList = records.stream().map(convertor).collect(Collectors.toList());
        return new PageDto<>(p.getTotal(),p.getPages(),voList);
    }

}
