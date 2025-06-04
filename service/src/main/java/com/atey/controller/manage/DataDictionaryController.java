package com.atey.controller.manage;


import com.atey.constant.DataDictionaryConstant;
import com.atey.result.Result;
import com.atey.service.IDataDictionaryService;
import com.atey.vo.DataDictionaryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author atey
 * @since 2025-05-25
 */
@RestController("manageDataDictionaryController")
@RequestMapping("/api/manage/data-dictionary")
@Api(tags = "管理端数据字典相关接口")
@Slf4j
@RequiredArgsConstructor
public class DataDictionaryController {

    private final IDataDictionaryService dataDictionaryService;

    /**
     * 查询文章分类
     * @return
     */
    @ApiOperation("查询文章分类")
    @GetMapping("/article-category")
    public Result<List<DataDictionaryVo>> getArticleCategory(){
        log.info("获取文章分类");
        List<DataDictionaryVo> list = dataDictionaryService.getData(DataDictionaryConstant.ARTICLE_CATEGORY);
        return Result.success(list);
    }
}
