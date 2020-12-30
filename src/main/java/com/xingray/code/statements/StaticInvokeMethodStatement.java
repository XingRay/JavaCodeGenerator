package com.xingray.code.statements;


import com.xingray.code.JExpression;
import com.xingray.code.JExpressionStatement;
import com.xingray.code.JMethod;
import com.xingray.code.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * method(xxx);
 */
public class StaticInvokeMethodStatement extends JExpressionStatement {

    private JMethod method;
    private List<JExpression> arguments;

    public JMethod getMethod() {
        return method;
    }

    public void setMethod(JMethod method) {
        this.method = method;
    }

    public List<JExpression> getArguments() {
        return arguments;
    }

    public void setArguments(List<JExpression> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "StaticInvokeMethodStatement{" +
                "method=" + method +
                ", arguments=" + arguments +
                '}';
    }

    @Override
    public List<String> toExpressionCodeLines() {
        List<String> codeLines = new ArrayList<>();
        String code = method.getName() + "(" + Util.toCodeString(arguments) + ")";
        codeLines.add(code);
        return codeLines;
    }

    public void addArgument(JExpression argument) {
        if (arguments == null) {
            arguments = new ArrayList<>();
        }
        arguments.add(argument);
    }
}
