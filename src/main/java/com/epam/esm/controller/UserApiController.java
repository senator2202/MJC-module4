package com.epam.esm.controller;

import com.epam.esm.controller.error_handler.ProjectError;
import com.epam.esm.exception.ExceptionProvider;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.GiftEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Rest Controller for User entities and their orders.
 */
@RestController
@RequestMapping("api/users")
public class UserApiController {

    private static final String ORDERS = "orders";
    private static final String GET_USER_ORDERS = "Get user orders";
    private static final String ADD_ORDER = "Add an order on certificate by user";

    private UserService userService;
    private OrderService orderService;
    private ExceptionProvider exceptionProvider;

    /**
     * Sets user service.
     *
     * @param userService the user service
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Sets order service.
     *
     * @param orderService the order service
     */
    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
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
     * Find all users, optionally with limit and offset.
     *
     * @param page the page number
     * @param size the page size
     * @return the list of user dto
     */
    @GetMapping
    @PreAuthorize("hasAuthority(T(com.epam.esm.controller.type.ApiPermission).READ_USERS) || hasRole('USER')")
    public List<UserDTO> findAll(@RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) Integer size) {
        List<UserDTO> users = userService.findAll(page, size);
        return users.stream()
                .map(this::addUserLinks)
                .collect(Collectors.toList());
    }

    /**
     * Find user by id, return user dto.
     *
     * @param id the id
     * @return the user dto
     */
    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    @PreAuthorize("hasAuthority(T(com.epam.esm.controller.type.ApiPermission).READ_USERS) || hasRole('USER')")
    public UserDTO findById(@PathVariable long id) {
        UserDTO user = userService.findById(id).orElseThrow(
                () -> exceptionProvider.giftEntityNotFoundException(ProjectError.USER_NOT_FOUND)
        );
        return addUserLinks(user);
    }

    /**
     * Buy certificate, return order dto.
     *
     * @param userId        the user id
     * @param certificateId the certificate id
     * @return the order dto
     */
    @PostMapping("/{userId:^[1-9]\\d{0,18}$}/orders")
    @PreAuthorize("hasAuthority(T(com.epam.esm.controller.type.ApiPermission).ADD_ORDERS)")
    public OrderDTO buyCertificate(@PathVariable long userId, @RequestBody long certificateId) {
        if (!GiftEntityValidator.correctId(certificateId)) {
            throw exceptionProvider.wrongParameterFormatException(ProjectError.BUY_PARAMETERS_WRONG_FORMAT);
        }
        OrderDTO added = orderService.add(userId, certificateId);
        return addOrderLinks(added);
    }

    /**
     * Find user orders, optionally with page number and page size.
     *
     * @param userId the user id
     * @param page   the page number
     * @param size   the page size
     * @return the list
     */
    @GetMapping("/{userId:^[1-9]\\d{0,18}$}/orders")
    @PreAuthorize("hasAuthority(T(com.epam.esm.controller.type.ApiPermission).READ_USER_ORDERS) || hasRole('USER')")
    public List<OrderDTO> findUserOrders(@PathVariable long userId,
                                         @RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer size) {
        List<OrderDTO> orders = orderService.findOrdersByUserId(userId, page, size);
        return orders.stream().map(this::addOrderLinks).collect(Collectors.toList());
    }

    /**
     * Find user order, return its dto.
     *
     * @param userId  the user id
     * @param orderId the order id
     * @return the order dto
     */
    @GetMapping("/{userId:^[1-9]\\d{0,18}$}/orders/{orderId:^[1-9]\\d{0,18}$}")
    @PreAuthorize("hasAuthority(T(com.epam.esm.controller.type.ApiPermission).READ_USER_ORDERS) || hasRole('USER')")
    public OrderDTO findUserOrderById(@PathVariable long userId,
                                      @PathVariable long orderId) {
        Optional<OrderDTO> optional = orderService.findUserOrderById(userId, orderId);
        return optional.map(this::addOrderLinks).orElseThrow(
                () -> exceptionProvider.giftEntityNotFoundException(ProjectError.ORDER_NOT_FOUND)
        );
    }

    /**
     * Find most widely used tag of user with highest total orders sum, return its dto.
     *
     * @return the tag dto
     */
    @GetMapping("/widely-used-tag")
    @PreAuthorize("hasAuthority(T(com.epam.esm.controller.type.ApiPermission).READ_WIDELY_USED_TAG)")
    public TagDTO widelyUsedTag() {
        TagDTO tag = orderService.mostWidelyUsedTagOfUserWithHighestOrdersSum().orElseThrow(
                () -> exceptionProvider.giftEntityNotFoundException(ProjectError.TAG_NOT_FOUND)
        );
        return TagApiController.addLinks(tag);
    }

    /**
     * Method add HATEOAS links to UserDTO entity
     */
    private UserDTO addUserLinks(UserDTO user) {
        return user
                .add(linkTo(UserApiController.class).slash(user.getId()).withSelfRel())
                .add(linkTo(UserApiController.class).slash(user.getId()).slash(ORDERS)
                        .withRel(HttpMethod.GET.name())
                        .withName(GET_USER_ORDERS))
                .add(linkTo(UserApiController.class).slash(user.getId()).slash(ORDERS)
                        .withRel(HttpMethod.POST.name())
                        .withName(ADD_ORDER));
    }

    /**
     * Method add HATEOAS links to OrderDTO entity
     */
    private OrderDTO addOrderLinks(OrderDTO order) {
        Long userId = order.getUser().getId();
        Long orderId = order.getId();
        GiftCertificateApiController.addSelfLink(order.getGiftCertificate());
        addUserLinks(order.getUser());
        return order
                .add(linkTo(UserApiController.class).slash(userId).slash(ORDERS).slash(orderId).withSelfRel());
    }
}
