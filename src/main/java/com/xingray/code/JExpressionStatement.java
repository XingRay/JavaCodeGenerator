package com.xingray.code;

import java.util.ArrayList;
import java.util.List;

public abstract class JExpressionStatement implements JStatement, JExpression {
    @Override
    public List<String> toStatementCodeLines() {
        List<String> expressionCodeLines = new ArrayList<>(toExpressionCodeLines());

        int lastIndex = expressionCodeLines.size() - 1;
        String lastCodeLine = expressionCodeLines.get(lastIndex) + ";";
        expressionCodeLines.set(lastIndex, lastCodeLine);

        return expressionCodeLines;
    }
}
