package com.atey.service;

import com.atey.entity.DataDictionary;
import com.atey.vo.DataDictionaryVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author atey
 * @since 2025-05-25
 */
@Mapper
public interface IDataDictionaryService extends IService<DataDictionary> {

    /**
     * 查询文章分类
     * @param articleCategory
     * @return
     */
    List<DataDictionaryVo> getData(String articleCategory);
}
