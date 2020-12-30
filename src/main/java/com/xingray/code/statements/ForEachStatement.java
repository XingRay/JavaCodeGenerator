package com.xingray.code.statements;


import com.xingray.code.JStatement;
import com.xingray.code.JVariable;
import com.xingray.code.generate.CodeConstants;
import com.xingray.code.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * for(xx; xx; xx){ xxx; }
 */
public class ForEachStatement implements JStatement {

    private JVariable element;
    private JVariable list;
    private List<JStatement> statements;

    public JVariable getElement() {
        return element;
    }

    public void setElement(JVariable element) {
        this.element = element;
    }

    public JVariable getList() {
        return list;
    }

    public void setList(JVariable list) {
        this.list = list;
    }

    public List<JStatement> getStatements() {
        return statements;
    }

    public void setStatements(List<JStatement> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        return "ForEachStatement{" +
                "element=" + element +
                ", list=" + list +
                ", statements=" + statements +
                '}';
    }

    @Override
    public List<String> toStatementCodeLines() {
        List<String> codeLines = new ArrayList<>();

        String codeLine = CodeConstants.FOR + "(" + element.getType().getName() + " " + element.getName() + ":" + list.getName() + "){";
        codeLines.add(codeLine);
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
