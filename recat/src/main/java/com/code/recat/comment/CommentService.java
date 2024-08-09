package com.code.recat.comment;

import org.springframework.data.domain.Page;

public interface CommentService {

    Page<Comment> getAllComments(int pageNum, int pageSize, Integer bookId);

    Comment getComment(Integer commentId);

    Comment addNewComment(String commentAuthorEmail, Integer bookId, String content);

    Comment addNewReply(String commentAuthorEmail, Integer commentId, String content);

    void deleteComment(Integer commentId);

    Comment updateComment(Integer commentAuthorId, Integer commentId, String content);

}
