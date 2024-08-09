package com.code.recat.comment;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CommentDtoMapper {

    public static CommentDto mapCommentToDto(Comment comment) {
        return mapCommentToDto(comment, new HashSet<>());
    }

    private static CommentDto mapCommentToDto(Comment comment, Set<Integer> visitedComments) {
        if (comment == null || visitedComments.contains(comment.getId())) {
            return null;
        }

        visitedComments.add(comment.getId());

        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .parent(mapCommentToDto(comment.getParent(), visitedComments))
                .userProfileName(comment.getCommentAuthor().getProfileName())
                .build();
    }

    public static Set<CommentDto> mapCommentsToDtos(Set<Comment> comments) {
        return comments.stream().map(
                CommentDtoMapper::mapCommentToDto
        ).collect(Collectors.toSet());
    }


}
