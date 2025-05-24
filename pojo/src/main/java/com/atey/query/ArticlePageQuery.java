package com.atey.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArticlePageQuery extends PageQuery{
    @ApiModelProperty(value = "文章标题")
    private String title;
    @ApiModelProperty(value = "作者昵称")
    private String authorName;
    @ApiModelProperty(value = "分类名称")
    private String categoryName;
    @ApiModelProperty(value = "文章发布状态 1-草稿 2-已发布")
    private Short status;
    @ApiModelProperty(value = "文章审核状态 1-审核通过 2-审核中 3-审核未通过")
    private Short audit;
    @ApiModelProperty("发布开始时间")
    @DateTimeFormat(pattern = ("yyyy-MM-dd HH:mm:ss"))
    private LocalDateTime startPublishTime;
    @ApiModelProperty("发布结束时间")
    @DateTimeFormat(pattern = ("yyyy-MM-dd HH:mm:ss"))
    private LocalDateTime endPublishTime;
}
