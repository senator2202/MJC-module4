package com.epam.esm.validator;

import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserRegistrationDTO;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Class - validator. Validates DTO objects and their fields.
 */
public class GiftEntityValidator {

    public static final String TAG_SPLITERATOR = ",";
    private static final String ID_REGEX = "^[1-9]\\d{0,18}$";
    private static final String NAME_REGEX = "^.{2,50}$";
    private static final String LOGIN_REGEX = "^[\\p{L},\\d]{2,50}$";
    private static final String PASSWORD_REGEX = NAME_REGEX;
    private static final String CERTIFICATE_DESCRIPTION_REGEX = "^.{1,250}$";
    private static final String POSITIVE_INT_REGEX = "^[1-9]\\d{0,9}$";
    private static final String PRICE_REQUEST_PARAMETER = "price";
    private static final String NAME_REQUEST_PARAMETER = "name";
    private static final String CREATE_DATE_REQUEST_PARAMETER = "create-date";
    private static final String LAST_UPDATE_DATE_REQUEST_PARAMETER = "last-update-date";
    private static final String DURATION_REQUEST_PARAMETER = "duration";

    private GiftEntityValidator() {
    }

    /**
     * Method validates parameters, that must be either null or match regular expressions
     *
     * @param name        the name
     * @param description the description
     * @param tagNames    the tag names
     * @param sortType    the sort type
     * @param direction   the direction
     * @param page        the page number
     * @param size        the page size
     * @return the boolean
     */
    public static boolean correctOptionalParameters(String name,
                                                    String description,
                                                    String tagNames,
                                                    String sortType,
                                                    String direction,
                                                    Integer page,
                                                    Integer size) {
        if (name != null && !name.matches(NAME_REGEX)) {
            return false;
        }
        if (description != null && !description.matches(CERTIFICATE_DESCRIPTION_REGEX)) {
            return false;
        }
        if (tagNames != null && !Arrays.stream(tagNames.split(TAG_SPLITERATOR)).allMatch(t -> t.matches(NAME_REGEX))) {
            return false;
        }
        return correctSortType(sortType) &&
                correctDirection(direction) &&
                correctOptionalNotNegativeIntValue(page) &&
                correctOptionalNotNegativeIntValue(size);
    }

    /**
     * Method checks if sortType has correct value
     */
    private static boolean correctSortType(String sortType) {
        return sortType == null || sortType.equals(PRICE_REQUEST_PARAMETER)
                || sortType.equals(NAME_REQUEST_PARAMETER) || sortType.equals(CREATE_DATE_REQUEST_PARAMETER)
                || sortType.equals(LAST_UPDATE_DATE_REQUEST_PARAMETER) || sortType.equals(DURATION_REQUEST_PARAMETER);
    }

    /**
     * Method checks if sort direction has correct value
     */
    private static boolean correctDirection(String direction) {
        return direction == null
                || direction.equalsIgnoreCase(Sort.Direction.ASC.name())
                || direction.equalsIgnoreCase(Sort.Direction.DESC.name());
    }

    /**
     * Method checks if Integer value either null or > 0
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean correctOptionalNotNegativeIntValue(Integer value) {
        return value == null || value == 0 || String.valueOf(value).matches(POSITIVE_INT_REGEX);
    }

    /**
     * Correct ids boolean.
     *
     * @param ids the ids
     * @return the boolean
     */
    public static boolean correctId(long... ids) {
        return Arrays.stream(ids).allMatch(id -> String.valueOf(id).matches(ID_REGEX));
    }

    /**
     * Correct tag name boolean.
     *
     * @param tagName the tag name
     * @return the boolean
     */
    public static boolean correctTagName(String tagName) {
        return tagName != null && tagName.matches(NAME_REGEX);
    }

    /**
     * Correct certificate description boolean.
     *
     * @param description the description
     * @return the boolean
     */
    public static boolean correctCertificateDescription(String description) {
        return description.matches(CERTIFICATE_DESCRIPTION_REGEX);
    }

    /**
     * Correct tag names boolean.
     *
     * @param tagNames the tag names
     * @return the boolean
     */
    public static boolean correctTagNames(String tagNames) {
        return Arrays.stream(tagNames.split(TAG_SPLITERATOR)).allMatch(GiftEntityValidator::correctTagName);
    }

    /**
     * Correct tag boolean.
     *
     * @param tag the tag
     * @return the boolean
     */
    public static boolean correctTag(TagDTO tag) {
        return tag != null && tag.getName() != null && tag.getName().matches(NAME_REGEX);
    }

    /**
     * Correct gift certificate boolean.
     *
     * @param certificate the certificate
     * @return the boolean
     */
    public static boolean correctGiftCertificate(GiftCertificateDTO certificate) {
        return correctCertificateName(certificate.getName()) &&
                correctOptionalDescription(certificate.getDescription()) &&
                correctOptionalPrice(certificate.getPrice()) &&
                correctOptionalDuration(certificate.getDuration()) &&
                correctTags(certificate.getTags());
    }

    private static boolean correctCertificateName(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    /**
     * Correct optional certificate name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean correctOptionalCertificateName(String name) {
        return name == null || name.matches(NAME_REGEX);
    }


    /**
     * Correct optional description boolean.
     *
     * @param description the description
     * @return the boolean
     */
    public static boolean correctOptionalDescription(String description) {
        return description == null || description.matches(CERTIFICATE_DESCRIPTION_REGEX);
    }

    /**
     * Correct optional price boolean.
     *
     * @param price the price
     * @return the boolean
     */
    public static boolean correctOptionalPrice(BigDecimal price) {
        return price == null || price.doubleValue() > 0;
    }

    /**
     * Correct optional duration boolean.
     *
     * @param duration the duration
     * @return the boolean
     */
    public static boolean correctOptionalDuration(Integer duration) {
        return duration == null || String.valueOf(duration).matches(POSITIVE_INT_REGEX);
    }

    /**
     * Correct tags boolean.
     *
     * @param tags the tags
     * @return the boolean
     */
    public static boolean correctTags(List<TagDTO> tags) {
        return tags == null || tags.stream().filter(Objects::nonNull).allMatch(GiftEntityValidator::correctTag);
    }

    /**
     * Method checks for correct gift certificate optional parameters.
     *
     * @param certificate the certificate
     * @return the boolean
     */
    public static boolean correctGiftCertificateOptional(GiftCertificateDTO certificate) {
        return correctOptionalCertificateName(certificate.getName()) &&
                correctOptionalDescription(certificate.getDescription()) &&
                correctOptionalPrice(certificate.getPrice()) &&
                correctOptionalDuration(certificate.getDuration()) &&
                correctTags(certificate.getTags());
    }

    /**
     * Method checks for correct user registration data.
     *
     * @param data the data
     * @return the boolean
     */
    public static boolean correctUserRegistrationData(UserRegistrationDTO data) {
        if (data.getName() == null || !data.getName().matches(NAME_REGEX)) {
            return false;
        }
        if (data.getUsername() == null || !data.getUsername().matches(LOGIN_REGEX)) {
            return false;
        }
        return data.getPassword() != null
                && data.getPassword().matches(PASSWORD_REGEX)
                && data.getPassword().equals(data.getPasswordRepeat());
    }
}
