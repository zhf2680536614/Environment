package com.atey.service;

import com.atey.dto.ArticleDto;
import com.atey.dto.PageDto;
import com.atey.entity.Article;
import com.atey.query.ArticlePageQuery;
import com.atey.vo.ArticleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author atey
 * @since 2025-05-23
 */
public interface ArticleService extends IService<Article> {

    /**
     * 添加文章
     * @param articleDto
     * @return
     */
    void addArticle(ArticleDto articleDto);

    /**
     * 文章分页查询
     * @param query
     * @return
     */
    PageDto<ArticleVo> pageQuery(ArticlePageQuery query);

    /**
     * 更新文章
     * @param dto
     */
    void updateArticle(ArticleDto dto);

    /**
     * 获取热门文章
     * @return
     */
    List<ArticleVo> getHotArticle();
}
