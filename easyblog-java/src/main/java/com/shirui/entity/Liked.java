package com.shirui.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Shirui
 * @since 2021-02-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_liked")
public class Liked implements Serializable {

    private Integer id; //主键

    private String objId;//对应对象的id

    private String userId;//点赞用户的id

    private Integer likeStatus;//点赞状态，对应的1标识已赞，0标识取消赞
}
