package com.epam.esm.util;

import com.epam.esm.data_provider.StaticDataProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectConverterTest {

    @Test
    void toTagDTO() {
        assertEquals(StaticDataProvider.TAG_DTO, ObjectConverter.toTagDTO(StaticDataProvider.TAG));
    }

    @Test
    void toTagEntity() {
        assertEquals(StaticDataProvider.TAG, ObjectConverter.toTagEntity(StaticDataProvider.TAG_DTO));
    }

    @Test
    void toGiftCertificateDTO() {
        assertEquals(StaticDataProvider.GIFT_CERTIFICATE_DTO,
                ObjectConverter.toGiftCertificateDTO(StaticDataProvider.GIFT_CERTIFICATE));
    }

    @Test
    void toGiftCertificateEntity() {
        assertEquals(StaticDataProvider.GIFT_CERTIFICATE,
                ObjectConverter.toGiftCertificateEntity(StaticDataProvider.GIFT_CERTIFICATE_DTO));
    }

    @Test
    void toUserDTO() {
        assertEquals(StaticDataProvider.USER_DTO, ObjectConverter.toUserDTO(StaticDataProvider.USER));
    }

    @Test
    void toOrderDTO() {
        assertEquals(StaticDataProvider.ORDER_DTO, ObjectConverter.toOrderDTO(StaticDataProvider.ORDER));
    }

    @Test
    void toGiftCertificateDTOs() {
        assertEquals(StaticDataProvider.GIFT_CERTIFICATE_DTO_LIST,
                ObjectConverter.toGiftCertificateDTOs(StaticDataProvider.GIFT_CERTIFICATE_LIST));
    }

    @Test
    void toUserDTOs() {
        assertEquals(StaticDataProvider.USER_DTO_LIST,
                ObjectConverter.toUserDTOs(StaticDataProvider.USER_LIST));
    }

    @Test
    void toOrderDTOs() {
        assertEquals(StaticDataProvider.ORDER_DTO_LIST,
                ObjectConverter.toOrderDTOs(StaticDataProvider.ORDER_LIST));
    }
}