package com.atey.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.atey.context.UserContext;
import com.atey.dto.ArticleDto;
import com.atey.dto.PageDto;
import com.atey.entity.Article;
import com.atey.entity.User;
import com.atey.enumeration.ArticleCheckEnum;
import com.atey.enumeration.ArticleHotEnum;
import com.atey.enumeration.ArticleStatusEnum;
import com.atey.mapper.ArticleMapper;
import com.atey.query.ArticlePageQuery;
import com.atey.service.ArticleService;
import com.atey.vo.ArticleVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author atey
 * @since 2025-05-23
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private static final Long ZERO = 0L;

    private final UserContext userContext;

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

        Map<Long, String> user = userContext.getUser();

        Set<Map.Entry<Long, String>> entries = user.entrySet();

        Long id = 0L;
        for (Map.Entry<Long, String> entry : entries) {
            id = entry.getKey();
        }

        User one = Db.lambdaQuery(User.class)
                .eq(User::getId, id)
                .one();

        article.setAuthorId(one.getId());
        article.setAuthorName(one.getUsername());

        this.save(article);
    }

    /**
     * 文章分页查询
     * @param query
     * @return
     */
    @Override
    public PageDto<ArticleVo> pageQuery(ArticlePageQuery query) {
        Page<Article> page = query.toMpPageDefaultSortByCreateTimeDesc();
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

    /**
     * 更新文章
     * @param dto
     */
    @Override
    public void updateArticle(ArticleDto dto) {
        Article article = BeanUtil.copyProperties(dto, Article.class);
        this.updateById(article);
    }

    /**
     * 获取热门文章
     * @return
     */
    @Override
    public List<ArticleVo> getHotArticle() {
        List<Article> list = lambdaQuery()
                .eq(Article::getIsHot, ArticleHotEnum.HOT.getKey())
                .eq(Article::getStatus, ArticleStatusEnum.POST.getKey())
                .eq(Article::getAudit,ArticleCheckEnum.PASS.getKey())
                .list();

        return BeanUtil.copyToList(list,ArticleVo.class);
    }
}
