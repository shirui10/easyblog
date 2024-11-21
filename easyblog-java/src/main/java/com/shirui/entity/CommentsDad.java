package com.shirui.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 评论主表
 * </p>
 *
 * @author Shirui
 * @since 2021-02-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_comments_dad")
public class CommentsDad implements Serializable {

    //评论主键id
    private Integer id;

    //评论唯一标识
    private String commentId;

    //评论的资源id。标记这条评论是属于哪个资源的。资源可以是文章、视频、资源
    private Long ownerId;

    //评论类型。1文章，2视频评论，3资源评论
    private Integer type;

    //评论者id
    private String fromId;

    //评论者名字
    private String fromName;

    //评论者头像
    private String fromAvatar;

    //获得点赞的数量
    private Integer likeNum;

    //评论内容
    private String content;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    //子评论
    private List<CommentsSon> listCommentsReply;

}
