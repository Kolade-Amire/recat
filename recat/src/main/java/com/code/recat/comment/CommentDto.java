package com.code.recat.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private CommentDto parent;
    private String userProfileName;
    private String content;
    private LocalDateTime createdAt;
}
