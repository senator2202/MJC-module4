package com.epam.esm.data_provider;

import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StaticDataProvider {

    public static final Integer LIMIT = 2;
    public static final TagDTO TAG_DTO;
    public static final TagDTO ADDING_TAG_DTO;
    public static final TagDTO ADDED_TAG_DTO;
    public static final Tag TAG;
    public static final Tag ADDING_TAG;
    public static final Tag ADDED_TAG;
    public static final List<TagDTO> TAG_DTO_LIST;
    public static final List<TagDTO> TAG_DTO_LIST_LIMIT;
    public static final List<Tag> TAG_LIST;
    public static final List<Tag> TAG_LIST_LIMIT;
    public static final UserDTO USER_DTO;
    public static final User USER;
    public static final List<UserDTO> USER_DTO_LIST;
    public static final List<UserDTO> USER_DTO_LIST_LIMIT;
    public static final List<User> USER_LIST;
    public static final List<User> USER_LIST_LIMIT;
    public static final GiftCertificate GIFT_CERTIFICATE;
    public static final GiftCertificateDTO GIFT_CERTIFICATE_DTO;
    public static final List<GiftCertificate> GIFT_CERTIFICATE_LIST;
    public static final List<GiftCertificate> GIFT_CERTIFICATE_LIST_LIMIT;
    public static final List<GiftCertificateDTO> GIFT_CERTIFICATE_DTO_LIST;
    public static final List<GiftCertificateDTO> GIFT_CERTIFICATE_DTO_LIST_LIMIT;
    public static final Order ORDER;
    public static final OrderDTO ORDER_DTO;
    public static final List<Order> ORDER_LIST;
    public static final List<Order> ORDER_LIST_LIMIT;
    public static final List<OrderDTO> ORDER_DTO_LIST;
    public static final List<OrderDTO> ORDER_DTO_LIST_LIMIT;
    public static final GiftCertificate ADDING_GIFT_CERTIFICATE;
    public static final GiftCertificate UPDATING_GIFT_CERTIFICATE;
    public static final Order ADDING_ORDER;

    static {
        TAG_DTO = new TagDTO(1L, "Вязание");
        ADDING_TAG_DTO = new TagDTO();
        ADDING_TAG_DTO.setName("Baseball");
        ADDED_TAG_DTO = new TagDTO(25L, "Baseball");
        TAG = new Tag(1L, "Вязание");
        ADDING_TAG = new Tag();
        ADDING_TAG.setName("Baseball");
        ADDED_TAG = new Tag(25L, "Baseball");
        TAG_DTO_LIST = Collections.nCopies(10, TAG_DTO);
        TAG_DTO_LIST_LIMIT = Collections.nCopies(LIMIT, TAG_DTO);
        TAG_LIST = Collections.nCopies(10, TAG);
        TAG_LIST_LIMIT = Collections.nCopies(LIMIT, TAG);
        USER_DTO = new UserDTO(1L, "Alex");
        USER = new User(1L, "Alex");
        USER_DTO_LIST = Collections.nCopies(10, USER_DTO);
        USER_DTO_LIST_LIMIT = Collections.nCopies(LIMIT, USER_DTO);
        USER_LIST = Collections.nCopies(10, USER);
        USER_LIST_LIMIT = Collections.nCopies(LIMIT, USER);
        GIFT_CERTIFICATE = new GiftCertificate(
                1L,
                "English courses",
                "English courses in school of foreign languages SkyEng",
                BigDecimal.valueOf(250.00),
                180,
                "2021-01-13T12:42Z",
                "2021-01-13T12:42Z",
                List.of(TAG)
        );
        ADDING_GIFT_CERTIFICATE = new GiftCertificate(
                null,
                "English courses",
                "English courses in school of foreign languages SkyEng",
                BigDecimal.valueOf(250.00),
                180,
                "2021-01-13T12:42Z",
                "2021-01-13T12:42Z",
                new ArrayList<>(List.of(new Tag(1L, "Активность"), new Tag(19L, "Развлечения")))
        );
        UPDATING_GIFT_CERTIFICATE = new GiftCertificate(
                1L,
                "Spanish courses",
                "Spanish courses in school of foreign languages SkySpain",
                BigDecimal.valueOf(255.00),
                180,
                "2021-01-13T12:42Z",
                "2021-01-13T12:42Z",
                List.of(new Tag(8L, "Активность"), new Tag(1L, "Отдых")));
        GIFT_CERTIFICATE_DTO = new GiftCertificateDTO(
                1L,
                "English courses",
                "English courses in school of foreign languages SkyEng",
                BigDecimal.valueOf(250.00),
                180,
                "2021-01-13T12:42Z",
                "2021-01-13T12:42Z",
                List.of(TAG_DTO)
        );
        GIFT_CERTIFICATE_LIST = Collections.nCopies(10, GIFT_CERTIFICATE);
        GIFT_CERTIFICATE_LIST_LIMIT = Collections.nCopies(LIMIT, GIFT_CERTIFICATE);
        GIFT_CERTIFICATE_DTO_LIST = Collections.nCopies(10, GIFT_CERTIFICATE_DTO);
        GIFT_CERTIFICATE_DTO_LIST_LIMIT = Collections.nCopies(LIMIT, GIFT_CERTIFICATE_DTO);
        ORDER = new Order(1L, USER, GIFT_CERTIFICATE, "2021-01-13T12:42Z", BigDecimal.valueOf(250.00));
        ORDER_DTO = new OrderDTO(1L, USER_DTO, GIFT_CERTIFICATE_DTO,
                "2021-01-13T12:42Z", BigDecimal.valueOf(250.00));
        ORDER_LIST = Collections.nCopies(10, ORDER);
        ORDER_LIST_LIMIT = Collections.nCopies(LIMIT, ORDER);
        ORDER_DTO_LIST = Collections.nCopies(10, ORDER_DTO);
        ORDER_DTO_LIST_LIMIT = Collections.nCopies(LIMIT, ORDER_DTO);
        ADDING_ORDER = new Order(
                null,
                new User(1L, "Alex"),
                new GiftCertificate(12L, "SilverScreen", "Просмотр любого кинофильма", BigDecimal.valueOf(15.00),
                        45, "2020-12-18T09:22Z", "2020-12-18T09:25Z", List.of(new Tag(7L, "Кино"))),
                "2021-02-05T17:05:55.6995333",
                BigDecimal.valueOf(15.00)
        );
    }
}
