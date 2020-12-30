package com.xingray.code.expressions;


import com.xingray.code.JExpression;
import com.xingray.code.JVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * 变量表达式
 */
public class VariableExpression implements JExpression {

    private JVariable variable;

    public JVariable getVariable() {
        return variable;
    }

    public void setVariable(JVariable variable) {
        this.variable = variable;
    }

    @Override
    public String toString() {
        return "VariableExpression{" +
                "variable=" + variable +
                '}';
    }

    @Override
    public List<String> toExpressionCodeLines() {
        List<String> codeLines = new ArrayList<>();

        codeLines.add(variable.getName());

        return codeLines;
    }
}
