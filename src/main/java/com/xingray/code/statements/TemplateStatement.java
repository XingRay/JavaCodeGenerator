package com.xingray.code.statements;


import com.xingray.code.JStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xx ${param} xxx => xx argument xx
 */
public class TemplateStatement implements JStatement {

    private String template;
    private Map<String, String> arguments;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, String> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, String> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "TemplateStatement{" +
                "template='" + template + '\'' +
                ", arguments=" + arguments +
                '}';
    }

    @Override
    public List<String> toStatementCodeLines() {
        List<String> codeLines = new ArrayList<>();

        String code = template;
        if (arguments != null) {
            for (Map.Entry<String, String> entry : arguments.entrySet()) {
                String param = entry.getKey();
                String argument = entry.getValue();
                code = code.replaceAll(param, argument);
            }
        }

        codeLines.add(code);

        return codeLines;
    }

    public void addArgument(String name, String value) {
        if (arguments == null) {
            arguments = new HashMap<>();
        }
        arguments.put(name, value);
    }
}
