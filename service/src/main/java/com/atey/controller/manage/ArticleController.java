package com.atey.controller.manage;


import cn.hutool.core.bean.BeanUtil;
import com.atey.dto.ArticleDto;
import com.atey.dto.PageDto;
import com.atey.entity.Article;
import com.atey.query.ArticlePageQuery;
import com.atey.result.Result;
import com.atey.service.ArticleService;
import com.atey.vo.ArticleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author atey
 * @since 2025-05-23
 */
@RestController
@RequestMapping("/api/manage/article")
@Slf4j
@Api(tags = "管理员相关接口/文章相关接口")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 添加文章
     *
     * @param articleDto
     * @return
     */
    @ApiOperation("添加文章")
    @PostMapping("/add")
    public Result<ArticleVo> addArticle(@RequestBody ArticleDto articleDto) {
        log.info("添加文章");
        articleService.addArticle(articleDto);
        return Result.success();
    }

    /**
     * 文章分页查询
     *
     * @param query
     * @return
     */
    @ApiOperation("文章分页查询")
    @GetMapping("/list")
    public Result<PageDto<ArticleVo>> pageQuery(ArticlePageQuery query) {
        PageDto<ArticleVo> dto = articleService.pageQuery(query);
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
}
