package com.code.recat.author;

import com.code.recat.util.AppConstants;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Page<AuthorDto> findAllAuthors(int pageNum, int pageSize) {
        var pageable = PageRequest.of(pageNum, pageSize);
        var authors = authorRepository.getAllByOrderByNameAsc(pageable);
        var authorDtos = authors.stream().map(
                AuthorDtoMapper::mapAuthorToDto
        ).collect(Collectors.toList());
        return new PageImpl<>(authorDtos, pageable, authorDtos.size());
    }

    @Override
    public Author getAuthorById(Integer authorId) {
        return authorRepository.findById(authorId).orElseThrow(
                () -> new EntityNotFoundException(AppConstants.AUTHOR_NOT_FOUND)
        );
    }

    @Override
    public Author getAuthorByName(String name) {
        return authorRepository.getAuthorByNameIgnoreCase(name).orElseThrow(() -> new EntityNotFoundException(AppConstants.AUTHOR_NOT_FOUND));
    }

    @Override
    @Transactional
    public AuthorDto addNewAuthor(AuthorRequest authorRequest) {
        var newAuthorRequest = Author.builder()
                .name(authorRequest.getName())
                .gender(authorRequest.getGender())
                .dateOfBirth(authorRequest.getDateOfBirth())
                .build();

        var savedAuthor = saveAuthor(newAuthorRequest);
        return AuthorDtoMapper.mapAuthorToDto(savedAuthor);
    }

    @Override
    @Transactional
    public AuthorDto updateAuthor(Integer authorId, AuthorRequest author) {
        var existingAuthor = getAuthorById(authorId);
        if (author.getName() != null) {
            existingAuthor.setName(author.getName());
        }
        if (author.getGender() != null) {
            existingAuthor.setGender(author.getGender());
        }
        if (author.getDateOfBirth() != null) {
            existingAuthor.setDateOfBirth(author.getDateOfBirth());
        }

        var updatedAuthor = authorRepository.save(existingAuthor);
        return AuthorDtoMapper.mapAuthorToDto(updatedAuthor);

    }

    @Override
    @Transactional
    public void deleteAuthor(Integer authorId) {
        var author = getAuthorById(authorId);
        authorRepository.delete(author);
    }

    @Override
    public Author getAuthorFromDto(AuthorDto authorDto) {
        return getAuthorById(authorDto.getId());
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);

    }

    @Override
    @Transactional
    public Page<AuthorDto> searchAuthorsByName(int pageNum, int pageSize, String authorName) {
        var page = PageRequest.of(pageNum, pageSize);
        var authors = authorRepository.searchAuthorsByName(page,authorName);
        return new PageImpl<>(authors.stream().map(
                AuthorDtoMapper::mapAuthorToDto
        ).collect(Collectors.toList()));

    }
}
