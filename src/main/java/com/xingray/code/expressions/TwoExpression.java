package com.xingray.code.expressions;


import com.xingray.code.JExpression;
import com.xingray.code.JOperator;

import java.util.ArrayList;
import java.util.List;

public class TwoExpression implements JExpression {

    private JExpression leftExpression;
    private JExpression rightExpression;
    private JOperator operator;

    public JExpression getLeftExpression() {
        return leftExpression;
    }

    public void setLeftExpression(JExpression leftExpression) {
        this.leftExpression = leftExpression;
    }

    public JExpression getRightExpression() {
        return rightExpression;
    }

    public void setRightExpression(JExpression rightExpression) {
        this.rightExpression = rightExpression;
    }

    public JOperator getOperator() {
        return operator;
    }

    public void setOperator(JOperator operator) {
        this.operator = operator;
    }

    @Override
    public List<String> toExpressionCodeLines() {
        List<String> codeLines = new ArrayList<>();

        List<String> leftExpressionCodeLines = leftExpression.toExpressionCodeLines();
        List<String> rightCodeLines = rightExpression.toExpressionCodeLines();
        int leftSize = leftExpressionCodeLines.size();
        int rightSize = rightCodeLines.size();

        int leftLastIndex = leftSize - 1;
        for (int i = 0; i < leftLastIndex; i++) {
            codeLines.add(leftExpressionCodeLines.get(i));
        }
        codeLines.add(leftExpressionCodeLines.get(leftLastIndex) + operator.getCode() + rightCodeLines.get(0));
        for (int i = 1; i < rightSize; i++) {
            codeLines.add(rightCodeLines.get(i));
        }

        return codeLines;
    }
}
