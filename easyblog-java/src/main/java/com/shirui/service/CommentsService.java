package com.shirui.service;

import com.shirui.entity.CommentsDad;
import com.shirui.entity.CommentsSon;
import com.shirui.entity.Liked;

import java.util.List;

/**
 * <p>
 * 评论主表 服务类
 * </p>
 *
 * @author Shirui
 * @since 2021-02-21
 */
public interface CommentsService {

    String getDadId(Integer id);

    String getDadName(Integer id);

    String getDadAvatar(Integer id);

    /**
     * 获取该文章或资源下的所有评论
     *
     * @param ownerId 文章或资源id
     * @return
     */
    List<CommentsDad> findByOwnerIdService(String ownerId);

    /**
     * 添加父评论
     *
     * @param commentsDad
     * @return
     */
    boolean addDadCommentsService(CommentsDad commentsDad);

    /**
     * 添加子评论或回复评论
     *
     * @param commentsSon
     * @return
     */
    boolean addSonCommentsService(CommentsSon commentsSon);

    /**
     * 点赞
     *
     * @param liked
     * @return
     */
    boolean addLikedService(Liked liked);

    /**
     * 查询单个用户的所有点赞信息
     *
     * @param userId
     * @return
     */
    List<Liked> getListLikedService(String userId);

    /**
     * 修改点赞
     *
     * @param liked
     * @return
     */
    boolean updateLikedService(Liked liked);

    /**
     * 检测用户是否点赞了
     *
     * @param liked
     * @return
     */
    Liked checkedLikeService(Liked liked);

    /**
     * 更新父表的点赞数
     *
     * @param liked
     * @return
     */
    boolean updateRootLikeNumService(Liked liked);

    /**
     * 更新子评论点赞数
     *
     * @param liked
     * @return
     */
    boolean updateReplyLikeNumService(Liked liked);
}
