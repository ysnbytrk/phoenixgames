package com.spotlight.platform.userprofile.api.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * Represents a string value that is alphanumeric and has a maximum length.
 * It extends the WrappedString class.
 */
public class AlphaNumericalStringWithMaxLength extends WrappedString {
    private static final int MAX_LENGTH = 1024;
    private static final int MIN_LENGTH = 1;
    private static final String PATTERN_ALPHA_NUMERIC_ONLY = "[A-Za-z0-9_-]*";

    /**
     * Constructs a new AlphaNumericalStringWithMaxLength object with the specified value.
     *
     * @param value The string value.
     */
    protected AlphaNumericalStringWithMaxLength(String value) {
        super(value);
    }

    /**
     * Retrieves the value of the AlphaNumericalStringWithMaxLength.
     *
     * @return The string value.
     */
    @Override
    @Length(min = MIN_LENGTH, max = MAX_LENGTH)
    @Pattern(regexp = PATTERN_ALPHA_NUMERIC_ONLY)
    @JsonProperty
    protected String getValue() {
        return super.getValue();
    }
}
