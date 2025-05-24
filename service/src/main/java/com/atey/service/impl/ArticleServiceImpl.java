package com.atey.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.atey.dto.ArticleDto;
import com.atey.dto.PageDto;
import com.atey.entity.Article;
import com.atey.enumeration.ArticleCheckEnum;
import com.atey.enumeration.ArticleStatusEnum;
import com.atey.mapper.ArticleMapper;
import com.atey.query.ArticlePageQuery;
import com.atey.service.ArticleService;
import com.atey.vo.ArticleVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
     *
     * @param articleDto
     * @return
     */
    @Override
    public void addArticle(ArticleDto articleDto) {
        Article article = BeanUtil.copyProperties(articleDto, Article.class);
        article.setAudit(ArticleCheckEnum.ING.getKey());
        article.setStatus(ArticleStatusEnum.POST.getKey());
        article.setPublishTime(LocalDateTime.now());
        article.setViewCount(ZERO);
        article.setCommentCount(ZERO);
        article.setLikeCount(ZERO);
        article.setCollectCount(ZERO);
        article.setIsRecommend(Short.valueOf(String.valueOf(ZERO)));
        article.setIsHot(Short.valueOf(String.valueOf(ZERO)));
        article.setAllowComment(Short.valueOf(String.valueOf(ZERO)));
        this.save(article);
    }

    @Override
    public PageDto<ArticleVo> pageQuery(ArticlePageQuery query) {
        Page<Article> page = query.toPage();
        Page<Article> dto = lambdaQuery()
                .like(StrUtil.isNotBlank(query.getTitle()), Article::getTitle, query.getTitle())
                .like(StrUtil.isNotBlank(query.getAuthorName()), Article::getAuthorName, query.getAuthorName())
                .like(StrUtil.isNotBlank(query.getCategoryName()), Article::getCategoryName, query.getCategoryName())
                .eq(BeanUtil.isNotEmpty(query.getStatus()), Article::getStatus, query.getStatus())
                .eq(BeanUtil.isNotEmpty(query.getAudit()), Article::getAudit, query.getAudit())
                .between(
                        query.getStartPublishTime() != null && query.getEndPublishTime() != null,
                        Article::getPublishTime, query.getStartPublishTime(), query.getEndPublishTime()
                ).page(page);
        return PageDto.of(dto, ArticleVo.class);
    }
}
