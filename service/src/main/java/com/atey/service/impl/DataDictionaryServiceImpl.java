package com.atey.service.impl;

import cn.hutool.core.lang.hash.Hash;
import com.atey.entity.DataDictionary;
import com.atey.mapper.DataDictionaryMapper;
import com.atey.service.IDataDictionaryService;
import com.atey.vo.DataDictionaryVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author atey
 * @since 2025-05-25
 */
@Service
public class DataDictionaryServiceImpl extends ServiceImpl<DataDictionaryMapper, DataDictionary> implements IDataDictionaryService {

    /**
     * 查询文章分类
     *
     * @param articleCategory
     * @return
     */
    @Override
    public List<DataDictionaryVo> getData(String articleCategory) {
        List<DataDictionary> list = lambdaQuery()
                .eq(DataDictionary::getType, articleCategory)
                .list();
        List<DataDictionaryVo> result = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (DataDictionary dataDictionary : list) {
                DataDictionaryVo dataDictionaryVo = new DataDictionaryVo();
                dataDictionaryVo.setLabel(dataDictionary.getLabel());
                result.add(dataDictionaryVo);
            }
        }
        return result;
    }
}
