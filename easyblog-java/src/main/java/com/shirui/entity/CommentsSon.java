package com.shirui.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 评论回复表
 * </p>
 *
 * @author Shirui
 * @since 2021-02-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_comments_son")
public class CommentsSon implements Serializable {

    //父评论的主键id
    private Integer id;

    //该条评论的唯一标识
    private String commentId;

    //评论者id
    private String fromId;

    //评论者名字
    private String fromName;

    //评论者头像
    private String fromAvatar;

    //被评论者id
    private String toId;

    //被评论者名字
    private String toName;

    //被评论者头像
    private String toAvatar;

    //获得点赞的数量
    private Integer likeNum;

    //评论内容
    private String content;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;


}
