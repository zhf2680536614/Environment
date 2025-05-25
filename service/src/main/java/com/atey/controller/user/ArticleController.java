package com.atey.controller.user;


import cn.hutool.core.bean.BeanUtil;
import com.atey.dto.ArticleDto;
import com.atey.dto.PageDto;
import com.atey.query.ArticlePageQuery;
import com.atey.result.Result;
import com.atey.service.ArticleService;
import com.atey.vo.ArticleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author atey
 * @since 2025-05-23
 */
@RestController("userArticleController")
@RequestMapping("/api/user/article")
@Slf4j
@Api(tags = "管理员相关接口/文章相关接口")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 查询热点文章
     */
    @ApiOperation("查询热点文章")
    @GetMapping("/hot")
    public Result<List<ArticleVo>> getHotArticle() {
        List<ArticleVo> dto = articleService.getHotArticle();
        return Result.success(dto);
    }

    /**
     * 根据id获取文章
     * @param id
     * @return
     */
    @ApiOperation("根据id获取文章")
    @GetMapping("/{id}")
    public Result<ArticleVo> getById(@PathVariable Long id) {
        log.info("根据id获取文章{}",id);
        return Result.success(BeanUtil.copyProperties(articleService.getById(id), ArticleVo.class));
    }

    /**
     * 更新文章
     * @param dto
     * @return
     */
    @ApiOperation("更新文章")
    @PutMapping("/update")
    public Result<Object> updateArticle(@RequestBody ArticleDto dto){
        log.info("更新文章");
        articleService.updateArticle(dto);
        return Result.success();
    }

    @ApiOperation("删除文章")
    @DeleteMapping("/{id}")
    public Result<Object> deleteArticle(@PathVariable Long id){
        log.info("删除文章");
        articleService.removeById(id);
        return Result.success();
    }

    @ApiOperation("批量删除")
    @DeleteMapping("/batchDelete")
    public Result<Object> batchDelete(@RequestBody ArrayList<Long> ids){
        log.info("批量删除文章");
        articleService.removeBatchByIds(ids);
        return Result.success();
    }
}
