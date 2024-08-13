package com.code.recat.author;

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
public class AuthorServiceImpl implements AuthorService{

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
    public Author getAuthor(Integer authorId) {
        return authorRepository.findById(authorId).orElseThrow(
                () -> new EntityNotFoundException("Author not found.")
        );
    }

    @Override
    @Transactional
    public Author addNewAuthor(AuthorRequest authorRequest) {
        var newAuthor = Author.builder()
                .name(authorRequest.getName())
                .gender(authorRequest.getGender())
                .dateOfBirth(authorRequest.getDateOfBirth())
                .build();

        return saveAuthor(newAuthor);
    }

    @Override
    @Transactional
    public Author updateAuthor(Integer authorId, AuthorRequest author) {
        var existingAuthor = getAuthor(authorId);
        if(author.getName() != null){existingAuthor.setName(author.getName());}
        if(author.getGender() != null){existingAuthor.setGender(author.getGender());}
        if(author.getDateOfBirth() != null){existingAuthor.setDateOfBirth(author.getDateOfBirth());}

        return authorRepository.save(existingAuthor);

    }

    @Override
    @Transactional
    public void deleteAuthor(Integer authorId) {
        var author = getAuthor(authorId);
        authorRepository.delete(author);
    }

    @Override
    public Author getAuthorFromDto(AuthorDto authorDto) {
        return getAuthor(authorDto.getId());
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);

    }

    @Override
    @Transactional
    public Author getAuthorByName(String authorName) {
        return authorRepository.findAuthorByNameIgnoreCase(authorName).orElseThrow(
                () -> new EntityNotFoundException("Author not found.")
        );
    }
}
