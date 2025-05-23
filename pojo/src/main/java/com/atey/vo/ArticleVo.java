package com.atey.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ArticleVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "作者名称")
    private Long authorName;

    @ApiModelProperty(value = "分类名称")
    private Long categoryName;

    @ApiModelProperty(value = "HTML格式的文章内容")
    private String contentHtml;

    @ApiModelProperty(value = "Markdown格式的文章内容")
    private String contentMd;

    @ApiModelProperty(value = "文章摘要")
    private String summary;

    @ApiModelProperty(value = "封面图片URL")
    private String coverImage;

    @ApiModelProperty(value = "文章审核状态")
    private Integer check;

    @ApiModelProperty(value = "文章状态：0-草稿，1-已发布，2-已下架，3-已删除")
    private Integer status;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "浏览量")
    private Long viewCount;

    @ApiModelProperty(value = "评论数")
    private Long commentCount;

    @ApiModelProperty(value = "点赞数")
    private Long likeCount;

    @ApiModelProperty(value = "收藏数")
    private Long collectCount;

    @ApiModelProperty(value = "是否热门：0-否，1-是")
    private Boolean isHot;

    @ApiModelProperty(value = "是否推荐：0-否，1-是")
    private Boolean isRecommend;

    @ApiModelProperty(value = "是否允许评论：0-否，1-是")
    private Boolean allowComment;

    @ApiModelProperty(value = "文章字数")
    private Integer wordCount;
}
