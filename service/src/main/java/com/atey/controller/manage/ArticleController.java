package com.atey.controller.manage;


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
@Api(tags="管理员相关接口/文章相关接口")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 添加文章
     * @param articleDto
     * @return
     */
    @ApiOperation("添加文章")
    @PostMapping("/add")
    public Result<ArticleVo> addArticle(@RequestBody ArticleDto articleDto) {
        articleService.addArticle(articleDto);
        return Result.success();
    }

    /**
     * 文章分页查询
     * @param query
     * @return
     */
    @ApiOperation("文章分页查询")
    @GetMapping("/list")
    public Result<PageDto<ArticleVo>> pageQuery(ArticlePageQuery query){
        PageDto<ArticleVo> dto = articleService.pageQuery(query);
        return Result.success(dto);
    }

}
