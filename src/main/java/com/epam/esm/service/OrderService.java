package com.epam.esm.service;

import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.TagDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides operation on Order entity.
 */
public interface OrderService {

    /**
     * Add order dto.
     *
     * @param userId        the user id
     * @param certificateId the certificate id
     * @return the order dto
     */
    OrderDTO add(long userId, long certificateId);

    /**
     * Find user order by id optional.
     *
     * @param userId  the user id
     * @param orderId the order id
     * @return the optional
     */
    Optional<OrderDTO> findUserOrderById(long userId, long orderId);

    /**
     * Find orders by user id list, limiting them by page number and page size.
     *
     * @param userId the user id
     * @param page   the page number
     * @param size   the page size
     * @return the list
     */
    List<OrderDTO> findOrdersByUserId(long userId, Integer page, Integer size);

    /**
     * Most widely used tag of user with highest orders sum optional.
     *
     * @return the optional
     */
    Optional<TagDTO> mostWidelyUsedTagOfUserWithHighestOrdersSum();
}
