package com.code.recat.comment;

import org.springframework.data.domain.Page;

public interface CommentService {

    Page<Comment> getAllComments(int pageNum, int pageSize, Long bookId);

    Comment getComment(Long commentId);

    Comment addNewComment(String commentAuthorEmail, Long bookId, String content);

    Comment addNewReply(String commentAuthorEmail, Long commentId, String content);

    void deleteComment(Long commentId);

    Comment updateComment(Long commentAuthorId, Long commentId, String content);

}
