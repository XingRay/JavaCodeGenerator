package com.xingray.code.util;

import com.xingray.code.*;
import com.xingray.code.generate.CodeConstants;
import com.xingray.javabase.interfaces.Mapper;
import com.xingray.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Util {

    public static String firstLowCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') {
            ch[0] = (char) (ch[0] + 32);
            str = new String(ch);
        }
        return str;
    }

    public static String toCode(List<JVariable> params) {
        return StringUtil.toString(params, ",", new Mapper<JVariable, String>() {
            @Override
            public String map(JVariable jParam) {
                return jParam.getType().getName() + " " + jParam.getName();
            }
        });
    }


    public static JMethod getConstructor(JClass cls, JAccessible accessible, List<JVariable> params) {
        JMethod constructor = new JMethod();

        constructor.setAccessible(accessible);
        constructor.setStatic(true);
        constructor.setReturnType(cls);
        constructor.setName(CodeConstants.NEW + " " + cls.getName());
        constructor.setParams(params);

        return constructor;
    }

    public static List<String> toCodeLines(List<JStatement> statements) {
        if (statements == null) {
            return Collections.emptyList();
        }
        List<String> codeLines = new ArrayList<>();

        for (JStatement statement : statements) {
            codeLines.addAll(statement.toStatementCodeLines());
        }

        return codeLines;
    }

    public static String toCodeString(List<JExpression> expressions) {
        return StringUtil.toString(expressions, ",", new Mapper<JExpression, String>() {
            @Override
            public String map(JExpression argument) {
                return StringUtil.toString(argument.toExpressionCodeLines(), "\n");
            }
        });
    }

    public static String getTypeName(String name) {
        int index = name.indexOf('.');
        if (index < 0) {
            return name;
        }
        int lastIndex = name.lastIndexOf('.');
        return name.substring(lastIndex + 1);
    }
}
