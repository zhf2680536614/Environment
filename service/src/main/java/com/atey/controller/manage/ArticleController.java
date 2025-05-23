package com.atey.controller.manage;


import com.atey.dto.ArticleDto;
import com.atey.entity.Article;
import com.atey.result.Result;
import com.atey.service.ArticleService;
import com.atey.vo.ArticleVo;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author atey
 * @since 2025-05-23
 */
@RestController
@RequestMapping("/api/article")
@Slf4j
@Api(tags="文章相关接口")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 添加文章
     * @param articleDto
     * @return
     */
    @PostMapping
    public Result<ArticleVo> addArticle(@RequestBody ArticleDto articleDto) {
        articleService.addArticle(articleDto);
        return Result.success();
    }

}
