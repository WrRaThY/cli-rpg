package priv.rdo.rpg.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static priv.rdo.rpg.common.util.StringUtils.isBlank;

/**
 * simplified version of apache-commons-lang
 */
public class ToStringBuilder {
    private static final Logger LOG = LogManager.getLogger(ToStringBuilder.class);

    private final Class callingClass;
    private final List<Field> fields;

    private final String delimiter;
    private final String prefix;
    private final String suffix;

    private final String fieldDelimiter;
    private final String fieldPrefix;
    private final String fieldSuffix;

    public static ToStringBuilder defaultBuilder(Object callingObject) {
        return new ToStringBuilder(callingObject, ",", "[", "]", "=", "", "");
    }

    public static ToStringBuilder defaultBuilderWithoutBrackets(Object callingObject) {
        return new ToStringBuilder(callingObject, ", ", "", "", "=", "", "");
    }

    public static ToStringBuilder fieldsWithNewlinesAndTabs(Object callingObject) {
        return new ToStringBuilder(callingObject, "\n\t", "\t", "\n", ": ", "\t", "\n");
    }

    public ToStringBuilder(Object callingObject, String delimiter, String prefix, String suffix, String fieldDelimiter, String fieldPrefix, String fieldSuffix) {
        this.callingClass = callingObject.getClass();
        this.delimiter = delimiter;
        this.prefix = prefix;
        this.suffix = suffix;
        this.fieldDelimiter = fieldDelimiter;
        this.fieldPrefix = fieldPrefix;
        this.fieldSuffix = fieldSuffix;
        fields = new ArrayList<>();
    }

    public ToStringBuilder append(String name, int value) {
        return append(name, value, Color.CLEAR_ALL_FORMATTING);
    }

    public ToStringBuilder append(String name, int value, Color color) {
        return append(name, String.valueOf(value), color);
    }

    public ToStringBuilder append(String name, String value) {
        return append(name, value, Color.CLEAR_ALL_FORMATTING);
    }

    public ToStringBuilder append(String name, String value, Color color) {
        if (isBlank(name)) {
            LOG.warn("name {} is empty", name);
            return this;
        }

        if (null == value) {
            LOG.warn("value for name {} is null", name);
            return this;
        }

        return append(new Field(name, value, color));
    }

    ToStringBuilder append(Field field) {
        fields.add(field);
        return this;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Do not use toString on ToStringBuilder. Use one of the builder methods instead");
    }

    public String buildWithClassName() {
        return build(false, true);
    }

    public String build() {
        return build(false, false);
    }

    public String build(boolean withColors) {
        return build(withColors, false);
    }

    public String build(boolean withColors, boolean withClassName) {
        return classNameIfRequired(withClassName) + fieldsToString(withColors);
    }

    private String classNameIfRequired(boolean withClassName) {
        if (withClassName) {
            return callingClass.getSimpleName();
        }

        return "";
    }

    String fieldsToString(boolean withColors) {
        StringJoiner fieldsJoiner = new StringJoiner(delimiter, prefix, suffix);

        for (Field field : fields) {
            fieldsJoiner.merge(fieldToString(field, withColors));
        }
        return fieldsJoiner.toString();
    }

    StringJoiner fieldToString(Field field, boolean withColors) {
        StringJoiner fieldJoiner = new StringJoiner(fieldDelimiter, fieldPrefix, fieldSuffix);

        fieldJoiner.add(field.getName());
        if (withColors) {
            fieldJoiner.add(field.coloredValue());
        } else {
            fieldJoiner.add(field.getValue());
        }

        return fieldJoiner;
    }

    static class Field {
        private final String name;
        private final String value;
        private final Color color;

        Field(String name, String value, Color color) {
            this.name = name;
            this.value = value;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public Color getColor() {
            return color;
        }

        public String coloredValue() {
            return color.format(value);
        }
    }
}
