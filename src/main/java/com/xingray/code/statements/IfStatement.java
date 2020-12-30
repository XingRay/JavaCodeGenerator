package com.xingray.code.statements;

import com.xingray.code.JExpression;
import com.xingray.code.JStatement;
import com.xingray.code.generate.CodeConstants;
import com.xingray.code.util.Util;
import com.xingray.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * if(xxx){xxx;}
 */
public class IfStatement implements JStatement {

    /**
     * 条件表达式
     */
    private JExpression conditionalExpression;

    private List<JStatement> statements;

    public JExpression getConditionalExpression() {
        return conditionalExpression;
    }

    public void setConditionalExpression(JExpression conditionalExpression) {
        this.conditionalExpression = conditionalExpression;
    }

    public List<JStatement> getStatements() {
        return statements;
    }

    public void setStatements(List<JStatement> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        return "IfStatement{" +
                "conditionalExpression=" + conditionalExpression +
                ", statements=" + statements +
                '}';
    }

    @Override
    public List<String> toStatementCodeLines() {
        List<String> codeLines = new ArrayList<>();

        codeLines.add(CodeConstants.IF + "(" + StringUtil.toString(conditionalExpression.toExpressionCodeLines(), "\n") + "){");
        codeLines.addAll(Util.toCodeLines(statements));
        codeLines.add("}");

        return codeLines;
    }

    public void addStatement(JStatement statement) {
        if (statements == null) {
            statements = new ArrayList<>();
        }
        statements.add(statement);
    }
}
