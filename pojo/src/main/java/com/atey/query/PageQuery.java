package com.atey.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class PageQuery {

    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @ApiModelProperty(value = "分页条数")
    private Integer pageSize;

    @ApiModelProperty(value = "创建开始时间")
    @DateTimeFormat(pattern = ("yyyy-MM-dd HH:mm:ss"))
    private LocalDateTime startCreateTime;

    @ApiModelProperty(value = "创建结束时间")
    @DateTimeFormat(pattern = ("yyyy-MM-dd HH:mm:ss"))
    private LocalDateTime endCreateTime;

    @ApiModelProperty(value = "排序字段")
    private String orderBy;

    @ApiModelProperty(value = "是否升序")
    private Boolean asc = true;

    public <T> Page<T> toPage(OrderItem... orderItems) {
        Page<T> p = Page.of(pageNo, pageSize);
        //先看前端有没有传递排序字段
        if (StrUtil.isNotBlank(orderBy)) {
            OrderItem item = new OrderItem();
            item.setColumn(orderBy);
            item.setAsc(asc);
            p.addOrder(item);
            return p;
        }
        //再看有没有手动指定排序字段
        if (orderItems != null) {
            p.addOrder(orderItems);
        }
        return p;
    }

    public <T> Page<T> toPage(String defaultOrderBy, boolean isAsc) {
        OrderItem item = new OrderItem();
        item.setColumn(defaultOrderBy);
        item.setAsc(isAsc);
        Page<T> p = Page.of(pageNo, pageSize);
        p.addOrder(item);
        return p;
    }

    public <T> Page<T> toMpPageDefaultSortByCreateTimeDesc() {
        return toPage("create_time", false);
    }
}
