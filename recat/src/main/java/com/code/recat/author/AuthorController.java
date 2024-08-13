package com.code.recat.author;

import com.code.recat.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.BASE_URL + "/authors")
public class AuthorController {
    private final AuthorService authorService;


    @GetMapping
    public ResponseEntity<Page<AuthorDto>> getAuthors(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        Page<AuthorDto> page = authorService.findAllAuthors(pageNum, pageSize);
        return ResponseEntity.ok(page);
    }
}
