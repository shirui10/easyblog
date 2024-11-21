package com.shirui.service.impl;

import com.shirui.entity.CommentsDad;
import com.shirui.entity.CommentsSon;
import com.shirui.entity.Liked;
import com.shirui.mapper.CommentsMapper;
import com.shirui.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 评论主表 服务实现类
 * </p>
 *
 * @author Shirui
 * @since 2021-02-21
 */
@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    CommentsMapper commentsMapper;


    @Override
    public String getDadId(Integer id) {
        if (id == null) {
            return null;
        }
        return commentsMapper.getDadId(id);
    }

    @Override
    public String getDadName(Integer id) {
        if (id == null) {
            return null;
        }
        return commentsMapper.getDadName(id);
    }

    @Override
    public String getDadAvatar(Integer id) {
        if (id == null) {
            return null;
        }
        return commentsMapper.getDadAvatar(id);
    }

    @Override
    public List<CommentsDad> findByOwnerIdService(String ownerId) {
        if (ownerId == null || ownerId.equals("")) {
            return null;
        }
        return commentsMapper.findByOwnerId(ownerId);
    }

    @Override
    public boolean addDadCommentsService(CommentsDad commentsDad) {
        if (commentsDad == null) {
            return false;
        }
        return commentsMapper.addDadComments(commentsDad);
    }

    @Override
    public boolean addSonCommentsService(CommentsSon commentsSon) {
        if (commentsSon == null) {
            return false;
        }
        return commentsMapper.addSonComments(commentsSon);
    }

    @Override
    public boolean addLikedService(Liked liked) {
        if (liked == null)
            return false;
        return commentsMapper.addLiked(liked);
    }

    @Override
    public List<Liked> getListLikedService(String userId) {
        return commentsMapper.getListLiked(userId);
    }

    @Override
    public boolean updateLikedService(Liked liked) {
        return commentsMapper.updateLiked(liked);
    }

    @Override
    public Liked checkedLikeService(Liked liked) {
        return commentsMapper.checkedLike(liked);
    }

    @Override
    public boolean updateRootLikeNumService(Liked liked) {
        return commentsMapper.updateRootLikeNum(liked);
    }

    @Override
    public boolean updateReplyLikeNumService(Liked liked) {
        return commentsMapper.updateReplyLikeNum(liked);
    }
}
