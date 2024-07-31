package com.code.recat.book.comment;

import com.code.recat.book.BookServiceImpl;
import com.code.recat.user.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;
    private final BookServiceImpl bookService;
    private final UserServiceImpl userServiceImpl;

    @Override
    public Page<Comment> getAllComments(int pageNum, int pageSize, Long bookId) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return commentRepository.findAllByOrderByCreatedAtDesc(pageable, bookId);
    }

    @Override
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("Comment does not exist")
        );
    }


    @Override
    @Transactional
    public Comment addNewComment(String commentAuthorEmail, Long bookId, String content) {
        var user = userServiceImpl.getUserByEmail(commentAuthorEmail);
        var book = bookService.findById(bookId);
        var newComment = Comment.builder()
                .book(book)
                .commentAuthor(user)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        return commentRepository.save(newComment);
    }

    @Override
    public Comment addNewReply(String commentAuthorEmail, Long commentId, String content) {

        var user = userServiceImpl.getUserByEmail(commentAuthorEmail);

        var comment = getComment(commentId);

        var newReply = Comment.builder()
                .parent(comment)
                .commentAuthor(user)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        return commentRepository.save(newReply);

    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        var comment = getComment(commentId);
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public Comment updateComment(Long commentAuthorId, Long commentId, String content) {
        var existingComment = getComment(commentId);

        if(content != null){
            existingComment.setContent(content);
        }
        return commentRepository.save(existingComment);
    }


}
