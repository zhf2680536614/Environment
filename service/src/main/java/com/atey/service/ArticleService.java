package com.atey.service;

import com.atey.dto.ArticleDto;
import com.atey.entity.Article;
import com.atey.vo.ArticleVo;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
