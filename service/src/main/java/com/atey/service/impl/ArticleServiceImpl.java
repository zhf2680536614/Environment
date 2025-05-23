package com.atey.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.atey.dto.ArticleDto;
import com.atey.entity.Article;
import com.atey.enumeration.ArticleCheckEnum;
import com.atey.enumeration.ArticleStatusEnum;
import com.atey.mapper.ArticleMapper;
import com.atey.service.ArticleService;
import com.atey.vo.ArticleVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author atey
 * @since 2025-05-23
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private static final Long ZERO = 0L;


    /**
     * 添加文章
     * @param articleDto
     * @return
     */
    @Override
    public void addArticle(ArticleDto articleDto) {
        Article article = BeanUtil.copyProperties(articleDto, Article.class);
        article.setCheck(ArticleCheckEnum.ING.getKey());
        article.setStatus(ArticleStatusEnum.POST.getKey());
        article.setPublishTime(LocalDateTime.now());
        article.setViewCount(ZERO);
        article.setCommentCount(ZERO);
        article.setLikeCount(ZERO);
        article.setCollectCount(ZERO);
        article.setIsRecommend(false);
        article.setIsHot(false);
        article.setAllowComment(false);
        this.save(article);
    }
}
