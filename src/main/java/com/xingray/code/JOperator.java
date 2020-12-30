package com.xingray.code;

public enum JOperator {
    /**
     * =
     */
    GIVEN("="),

    /**
     * ==
     */
    EQUALS("=="),

    /**
     * !=
     */
    NOT_EQUALS("!="),

    /**
     * >
     */
    GREATER_THAN(">"),
    /**
     * >=
     */
    GREATER_THAN_OR_EQUALS(">="),

    /**
     * <
     */
    LESS_THAN("<"),

    /**
     * <=
     */
    LESS_THAN_OR_EQUALS("<="),

    /**
     * &&
     */
    AND("&&"),

    /**
     * ||
     */
    OR("||"),

    /**
     * !
     */
    NOT("!"),

    /**
     * ^
     */
    XOR("^"),

    /**
     * &
     */
    BIT_AND("&"),

    /**
     * |
     */
    BIT_OR("|"),

    /**
     * +
     */
    ADD("+"),

    /**
     * -
     */
    MINUS("-"),

    /**
     * *
     */
    MULTIPLY("*"),

    /**
     * /
     */
    DIVIDE("/"),

    /**
     * %
     */
    MODE("%"),

    /**
     * ?
     */
    IF_OR("?");

    private final String code;

    JOperator(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "JOperator{" +
                "code='" + code + '\'' +
                '}';
    }
}
