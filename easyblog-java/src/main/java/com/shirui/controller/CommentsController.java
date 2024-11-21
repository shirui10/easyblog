package com.shirui.controller;


import com.shirui.common.lang.ResultDT;
import com.shirui.entity.CommentsDad;
import com.shirui.entity.CommentsSon;
import com.shirui.entity.Liked;
import com.shirui.mapper.CommentsMapper;
import com.shirui.service.CommentsService;
import com.shirui.util.ResultDTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 评论主表 前端控制器
 * </p>
 *
 * @author Shirui
 * @since 2021-02-21
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600) //跨域
@Slf4j
public class CommentsController {

    @Autowired
    CommentsService commentsService;

    @Autowired
    CommentsMapper commentsMapper;

    /**
     * 添加父评论   直接对标文章，资源等下面的评论
     *
     * @param commentsDad
     * @return
     */
    @PostMapping("/addDadComments")
    public ResultDT addDadComments(@RequestBody CommentsDad commentsDad) {
        log.info("Comment1:" + commentsDad);
        if (commentsDad.getContent().length() != 0) {
            commentsDad.setCommentId(UUID.randomUUID().toString().replaceAll("-", ""));//设置评论唯一标识
            commentsDad.setCreateTime(LocalDateTime.now());//设置添加评论时间
            commentsDad.setType(1);
            log.info("Comment2:" + commentsDad);
            boolean b = commentsService.addDadCommentsService(commentsDad); //调用service方法来完成评论的存储
            if (b) {
                return ResultDTUtils.success(commentsDad);
            }
        }
        //评论内容为空 返回错误信息
        return ResultDTUtils.error(ResultDTUtils.COMMENT_ERROR, "addError");
    }

    /**
     * 添加子评论，对应父评论
     *
     * @param commentsSon
     * @return
     */
    @PostMapping("/addSonComments")
    public ResultDT addSonComments(@RequestBody CommentsSon commentsSon) {
        if (commentsSon.getContent().length() != 0) {
            commentsSon.setCommentId(UUID.randomUUID().toString().replaceAll("-", ""));
            commentsSon.setCreateTime(LocalDateTime.now());

            Integer ID = commentsSon.getId();

            commentsSon.setToId(commentsService.getDadId(ID));
            commentsSon.setToName(commentsService.getDadName(ID));
            commentsSon.setToAvatar(commentsService.getDadAvatar(ID));

            boolean b = commentsService.addSonCommentsService(commentsSon);
            if (b) {
                return ResultDTUtils.success(commentsSon, null);
            }
        }
        return ResultDTUtils.error(ResultDTUtils.COMMENT_ERROR, "addError");
    }

    /**
     * 根据资源ID来回去该资源的所有评论
     *
     * @param request
     * @return
     */
    @RequestMapping("/getListByOwnerId")
    public ResultDT getListByOwnerId(HttpServletRequest request) {
        String ownerId = request.getHeader("ownerId");
        String userId = request.getParameter("userId");
        //查询所有评论
        List<CommentsDad> byOwnerIdService = commentsService.findByOwnerIdService(ownerId);
        log.info("Data: " + request);
        log.info("Data ID: " + ownerId);
        return ResultDTUtils.success(byOwnerIdService);
    }

    /**
     * 点赞模块，已完善
     *
     * @param liked
     * @return
     */
    @PostMapping("/isLike")
    public ResultDT isLike(Liked liked, @RequestParam("commType") String commType) {
        boolean b;
        Liked checked = commentsService.checkedLikeService(liked);
        if (checked == null) {
            liked.setLikeStatus(1);
            b = commentsService.addLikedService(liked);
        } else {
            if (checked.getLikeStatus() == 0)
                liked.setLikeStatus(1);
            else
                liked.setLikeStatus(0);
            b = commentsService.updateLikedService(liked);
        }
        if (b) {
            //更新评论的点赞次数
            if (liked.getLikeStatus() == 0)
                liked.setLikeStatus(-1);
            if (commType.equals("root"))
                commentsService.updateRootLikeNumService(liked);
            else
                commentsService.updateReplyLikeNumService(liked);
            return ResultDTUtils.success(liked);
        } else
            return ResultDTUtils.error(ResultDTUtils.SUBMIT_ERROR, "SubmitError");
    }

    @RequestMapping("/getListLikeByUserId")
    public ResultDT getListLikeByUserId(@RequestParam("userId") String userId) {
        //查询所有点赞信息
        List<Liked> listLikedService = commentsService.getListLikedService(userId);
        return ResultDTUtils.success(listLikedService);
    }
}
