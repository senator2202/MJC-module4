package com.epam.esm.controller;

import com.epam.esm.controller.error_handler.ProjectError;
import com.epam.esm.exception.ExceptionProvider;
import com.epam.esm.model.dto.DeleteResultDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * RestController for Tag entity.
 */
@RestController
@RequestMapping("api/tags")
public class TagApiController {

    private TagService service;
    private ExceptionProvider exceptionProvider;

    /**
     * Method adds HATEOAS link to TagDTO entity
     *
     * @param tag the tag
     * @return the tag dto
     */
    static TagDTO addLinks(TagDTO tag) {
        return tag
                .add(linkTo(TagApiController.class).slash(tag.getId()).withSelfRel())
                .add(linkTo(TagApiController.class)
                        .withRel(HateoasData.POST)
                        .withName(HateoasData.ADD_TAG))
                .add(linkTo(TagApiController.class).slash(tag.getId())
                        .withRel(HateoasData.PUT)
                        .withName(HateoasData.UPDATE_TAG))
                .add(linkTo(TagApiController.class).slash(tag.getId())
                        .withRel(HateoasData.DELETE)
                        .withName(HateoasData.DELETE_TAG));
    }

    /**
     * Sets service.
     *
     * @param service the service
     */
    @Autowired
    public void setService(TagService service) {
        this.service = service;
    }

    /**
     * Sets exception provider.
     *
     * @param exceptionProvider the exception provider
     */
    @Autowired
    public void setExceptionProvider(ExceptionProvider exceptionProvider) {
        this.exceptionProvider = exceptionProvider;
    }

    /**
     * Find all certificates, optionally with size of page and page number.
     *
     * @param size the size of page
     * @param page the page number
     * @return the list
     */
    @GetMapping
    //@PreAuthorize("hasAuthority(T(com.epam.esm.controller.type.Permission).READ_TAGS)")
    public List<TagDTO> findAll(@RequestParam(required = false) Integer page,
                                @RequestParam(required = false) Integer size) {
        List<TagDTO> tags = service.findAll(page, size);
        return tags.stream()
                .map(TagApiController::addLinks)
                .collect(Collectors.toList());
    }

    /**
     * Find by id, return tag dto.
     *
     * @param id the id
     * @return the tag dto
     */
    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    @PreAuthorize("hasAuthority(T(com.epam.esm.controller.type.Permission).READ_TAGS)")
    //@PreAuthorize("isAuthenticated()")
    public TagDTO findById(@PathVariable long id) {
        TagDTO tag = service.findById(id).orElseThrow(
                () -> exceptionProvider.giftEntityNotFoundException(ProjectError.TAG_NOT_FOUND)
        );
        return addLinks(tag);
    }

    /**
     * Create tag, return created dto.
     *
     * @param tag the tag
     * @return the tag dto
     */
    @PostMapping
    @PreAuthorize("hasAuthority(T(com.epam.esm.controller.type.Permission).ADD_TAGS)")
    public TagDTO create(@RequestBody TagDTO tag) {
        if (!GiftEntityValidator.correctTag(tag)) {
            throw exceptionProvider.wrongParameterFormatException(ProjectError.TAG_WRONG_PARAMETERS);
        }
        return addLinks(service.add(tag));
    }

    /**
     * Update tag, return updated dto.
     *
     * @param tag the tag
     * @param id  the id
     * @return the tag dto
     */
    @PutMapping("/{id:^[1-9]\\d{0,18}$}")
    @PreAuthorize("hasAuthority(T(com.epam.esm.controller.type.Permission).UPDATE_TAGS)")
    public TagDTO update(@RequestBody TagDTO tag, @PathVariable long id) {
        if (!GiftEntityValidator.correctTag(tag)) {
            throw exceptionProvider.wrongParameterFormatException(ProjectError.TAG_WRONG_PARAMETERS);
        }
        tag.setId(id);
        TagDTO updated = service.update(tag).orElseThrow(
                () -> exceptionProvider.giftEntityNotFoundException(ProjectError.TAG_NOT_FOUND)
        );
        return addLinks(updated);
    }

    /**
     * Delete gift certificate, return the result of deleting.
     *
     * @param id the id
     * @return the delete result
     */
    @DeleteMapping("/{id:^[1-9]\\d{0,18}$}")
    @PreAuthorize("hasAuthority(T(com.epam.esm.controller.type.Permission).DELETE_TAGS)")
    public DeleteResultDTO delete(@PathVariable int id) {
        boolean result = service.delete(id);
        return new DeleteResultDTO(result);
    }
}
