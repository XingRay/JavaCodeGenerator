package com.xingray.code.statements;


import com.xingray.code.JExpression;
import com.xingray.code.JStatement;
import com.xingray.code.generate.CodeConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * return; or return xxx;
 */
public class ReturnStatement implements JStatement {

    private JExpression expression;

    public JExpression getExpression() {
        return expression;
    }

    public void setExpression(JExpression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "ReturnStatement{" +
                "expression=" + expression +
                '}';
    }

    @Override
    public List<String> toStatementCodeLines() {
        List<String> codeLines = new ArrayList<>();

        if (expression == null) {
            codeLines.add(CodeConstants.RETURN + ";");
        } else {
            List<String> expressionCodeLines = new ArrayList<>(expression.toExpressionCodeLines());
            String firstCodeLine = CodeConstants.RETURN + " " + expressionCodeLines.get(0);
            expressionCodeLines.set(0, firstCodeLine);

            int lastIndex = expressionCodeLines.size() - 1;
            String lastCodeLine = expressionCodeLines.get(lastIndex) + ";";
            expressionCodeLines.set(lastIndex, lastCodeLine);

            codeLines.addAll(expressionCodeLines);
        }

        return codeLines;
    }
}
