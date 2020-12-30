package com.xingray.code.expressions;


import com.xingray.code.JExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * 字面值表达式
 */
public class ValueExpression implements JExpression {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ValueExpression{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public List<String> toExpressionCodeLines() {
        List<String> codeLines = new ArrayList<>();

        codeLines.add(value);

        return codeLines;
    }
}
