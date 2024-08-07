package com.code.recat.comment;

import com.code.recat.book.BookServiceImpl;
import com.code.recat.user.UserService;
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
    private final UserService userService;

    @Override
    public Page<Comment> getAllComments(int pageNum, int pageSize, Integer bookId) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return commentRepository.findAllByOrderByCreatedAtDesc(pageable, bookId);
    }

    @Override
    public Comment getComment(Integer commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("Comment does not exist")
        );
    }


    @Override
    @Transactional
    public Comment addNewComment(String commentAuthorEmail, Integer bookId, String content) {
        var user = userService.getUserByEmail(commentAuthorEmail);
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
    public Comment addNewReply(String commentAuthorEmail, Integer commentId, String content) {

        var user = userService.getUserByEmail(commentAuthorEmail);

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
    public void deleteComment(Integer commentId) {
        var comment = getComment(commentId);
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public Comment updateComment(Integer commentAuthorId, Integer commentId, String content) {
        var existingComment = getComment(commentId);

        if(content != null){
            existingComment.setContent(content);
        }
        return commentRepository.save(existingComment);
    }


}
