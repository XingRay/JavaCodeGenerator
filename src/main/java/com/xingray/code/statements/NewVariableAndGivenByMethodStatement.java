package com.xingray.code.statements;

import com.xingray.code.JExpression;
import com.xingray.code.JMethod;
import com.xingray.code.JStatement;
import com.xingray.code.JVariable;
import com.xingray.javabase.interfaces.Mapper;
import com.xingray.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Abc abc = method(args);
 */
public class NewVariableAndGivenByMethodStatement implements JStatement {

    private JVariable variable;
    private JMethod method;
    private List<JExpression> arguments;

    public JVariable getVariable() {
        return variable;
    }

    public void setVariable(JVariable variable) {
        this.variable = variable;
    }

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
        return "ConstructorVariableStatement{" +
                "method=" + method +
                '}';
    }

    @Override
    public List<String> toStatementCodeLines() {
        List<String> codeLines = new ArrayList<>();

        String code = "";
        JVariable variable = getVariable();
        code += variable.getType().getName() + " " + variable.getName() + " = " + getMethod().getName() + "(" + StringUtil.toString(arguments, ", ", new Mapper<JExpression, String>() {
            @Override
            public String map(JExpression expression) {
                return StringUtil.toString(expression.toExpressionCodeLines(), "\n");
            }
        }) + ");";
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
