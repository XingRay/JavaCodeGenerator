package com.xingray.code.generate;

import com.xingray.code.*;
import com.xingray.code.expressions.EmptyLineStatement;
import com.xingray.code.expressions.TwoExpression;
import com.xingray.code.expressions.ValueExpression;
import com.xingray.code.expressions.VariableExpression;
import com.xingray.code.statements.*;
import com.xingray.code.util.Util;
import com.xingray.util.NumberUtil;
import com.xingray.util.ReflectUtil;
import com.xingray.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CodeGenerator {

    public static List<JField> getFields(String fieldListText) {
        if (StringUtil.isEmpty(fieldListText)) {
            return Collections.emptyList();
        }


        String[] lines = fieldListText.split("\n");

        List<JField> fields = new ArrayList<>(lines.length);
        for (String line : lines) {
            JField field = getField(line);
            if (field != null) {
                fields.add(field);
            }
        }

        return fields;
    }

    private static JField getField(String filedLine) {
        if (StringUtil.isEmpty(filedLine)) {
            return null;
        }
        JField field = new JField();
        filedLine = filedLine.trim();
        if (filedLine.endsWith(";")) {
            filedLine = filedLine.substring(0, filedLine.length() - 1);
        }
        String[] strings = filedLine.split("\\s+");
        List<String> words = new ArrayList<>(strings.length);
        //noinspection ManualArrayToCollectionCopy
        for (String s : strings) {
            //noinspection UseBulkOperation
            words.add(s);
        }

        if (words.remove(CodeConstants.PRIVATE)) {
            field.setAccessible(JAccessible.PRIVATE);
        } else if (words.remove(CodeConstants.PUBLIC)) {
            field.setAccessible(JAccessible.PUBLIC);
        } else if (words.remove(CodeConstants.PROTECTED)) {
            field.setAccessible(JAccessible.PROTECTED);
        } else {
            field.setAccessible(JAccessible.PACKAGE);
        }

        if (words.remove(CodeConstants.STATIC)) {
            field.setStatic(true);
        }

        if (words.remove(CodeConstants.FINAL)) {
            field.setFinal(true);
        }

        String typeName = words.get(0);
        String filedName = words.get(1);

        JType type = new JType();
        type.setName(typeName);
        field.setType(type);

        field.setName(filedName);

        return field;
    }

    public static String generateValueTransportCode(JField filed, String source, String target) {
        Class<?> type;
        String typeName = filed.getType().getName();
        if (typeName.equals("Boolean")) {
            type = Boolean.class;
        } else if (typeName.equals(CodeConstants.BOOLEAN)) {
            type = boolean.class;
        } else {
            type = Object.class;
        }

        String getterName = ReflectUtil.getGetterName(filed.getName(), type);
        String setterName = ReflectUtil.getSetterName(filed.getName(), type);

        return target + "." + setterName + "(" + source + "." + getterName + "()" + ");";
    }

    public static List<JField> generateFields(String dataText) {
        if (StringUtil.isEmpty(dataText)) {
            return null;
        }

        String[] lines = dataText.split("\n");
        if (lines.length == 0) {
            return null;
        }
        List<JField> fields = new ArrayList<>(lines.length);
        for (String line : lines) {
            JField field = generateField(line);
            if (field != null) {
                fields.add(field);
            }
        }

        return fields;
    }

    private static JField generateField(String line) {
        if (StringUtil.isEmpty(line)) {
            return null;
        }
        int index = line.indexOf(':');
        if (index < 0) {
            return null;
        }

        JField field = new JField();
        field.setAccessible(JAccessible.PRIVATE);
        field.setStatic(false);
        field.setFinal(false);

        String fieldName = line.substring(0, index).trim();
        if (fieldName.startsWith("\"") && fieldName.endsWith("\"")) {
            fieldName = fieldName.substring(1, fieldName.length() - 1);
        }
        field.setName(fieldName);

        String fieldValue = line.substring(index + 1).trim();
        JClass javaClass = new JClass();
        if (fieldValue.startsWith("\"") && fieldValue.endsWith("\"")) {
            javaClass.setName(CodeConstants.STRING);
        } else if (fieldValue.equals(CodeConstants.TRUE) || fieldValue.equals(CodeConstants.FALSE)) {
            javaClass.setName(CodeConstants.BOOLEAN);
        } else if (fieldValue.contains(".")) {
            javaClass.setName(CodeConstants.DOUBLE);
        } else {
            Long longValue = NumberUtil.toLong(fieldValue);
            if (longValue != null) {
                if (longValue < 100) {
                    javaClass.setName(CodeConstants.INT);
                } else {
                    javaClass.setName(CodeConstants.LONG);
                }
            } else {
                javaClass.setName(CodeConstants.OBJECT);
            }
        }
        field.setType(javaClass);

        return field;
    }

    public static List<String> toCodes(List<JField> fields) {
        if (fields == null || fields.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> lines = new ArrayList<>(fields.size());
        for (JField field : fields) {
            lines.add(toCode(field));
        }
        return lines;
    }

    public static String toCode(JField field) {
        String s = "";
        switch (field.getAccessible()) {
            case PRIVATE -> s += CodeConstants.PRIVATE;
            case PUBLIC -> s += CodeConstants.PUBLIC;
            case PROTECTED -> s += CodeConstants.PROTECTED;
        }

        if (field.isStatic()) {
            if (!s.isBlank()) {
                s += " ";
            }
            s += CodeConstants.STATIC;
        }

        if (field.isFinal()) {
            if (!s.isBlank()) {
                s += " ";
            }
            s += CodeConstants.FINAL;
        }

        if (!s.isBlank()) {
            s += " ";
        }
        s += field.getType().getName() + " " + field.getName() + ";";
        return s;
    }

    public static JMethod generateObjectConverterMethod(JClass source, JClass target, List<JField> fields) {
        JMethod method = new JMethod();
        List<String> existNames = new ArrayList<>();

        method.setAccessible(JAccessible.PUBLIC);
        method.setStatic(true);
        method.setReturnType(target);
        method.setName("to" + target.getName());

        JVariable sourceVariable = new JVariable();
        sourceVariable.setType(source);
        String sourceName = Util.firstLowCase(source.getName());
        sourceVariable.setName(sourceName);
        method.addParam(sourceVariable);
        existNames.add(sourceName);

        // Target target = new Target();
        String targetName = newVariableName(existNames, Util.firstLowCase(target.getName()), "target");
        NewVariableAndGivenByMethodStatement constructStatement = createConstructorInvokeStatement(target, targetName);
        existNames.add(targetName);
        method.addStatement(constructStatement);

        JVariable targetVariable = constructStatement.getVariable();

        if (fields != null) {
            for (JField field : fields) {
                method.addStatement(generateVariableInvokeMethodStatement(field, sourceVariable, targetVariable));
            }
        }

        return method;
    }

    /**
     * target.setXxx(source.getXxx());
     */
    public static JStatement generateVariableInvokeMethodStatement(JField filed, JVariable source, JVariable target) {
        Class<?> type;
        String typeName = filed.getType().getName();
        if (typeName.equals("Boolean")) {
            type = Boolean.class;
        } else if (typeName.equals(CodeConstants.BOOLEAN)) {
            type = boolean.class;
        } else {
            type = Object.class;
        }

        String getterName = ReflectUtil.getGetterName(filed.getName(), type);
        String setterName = ReflectUtil.getSetterName(filed.getName(), type);

        VariableInvokeMethodStatement statement = new VariableInvokeMethodStatement();
        statement.setVariable(target);

        JMethod setter = new JMethod();
        setter.setName(setterName);
        statement.setMethod(setter);

        // source.getXxx()
        VariableInvokeMethodStatement getExpressionStatement = new VariableInvokeMethodStatement();
        getExpressionStatement.setVariable(source);
        JMethod getter = new JMethod();
        getter.setName(getterName);
        getExpressionStatement.setMethod(getter);

        statement.addArgument(getExpressionStatement);

        return statement;
    }

    public static NewVariableAndGivenByMethodStatement createConstructorInvokeStatement(JClass cls, String name) {
        NewVariableAndGivenByMethodStatement newVariableAndGivenByMethodStatement = new NewVariableAndGivenByMethodStatement();
        newVariableAndGivenByMethodStatement.setMethod(cls.getDefaultConstruct());
        JVariable variable = new JVariable();
        variable.setName(name);
        variable.setType(cls);
        newVariableAndGivenByMethodStatement.setVariable(variable);
        return newVariableAndGivenByMethodStatement;
    }

    public static String toCode(JMethod method) {
        String code = "";
        code += toCode(method.getAccessible());

        if (method.isStatic()) {
            if (!code.isBlank()) {
                code += " ";
            }
            code += CodeConstants.STATIC;
        }

        if (method.isFinal()) {
            if (!code.isBlank()) {
                code += " ";
            }
            code += CodeConstants.FINAL;
        }

        if (!code.isBlank()) {
            code += " ";
        }
        code += method.getReturnType().getName();

        code += " " + method.getName() + "(" + Util.toCode(method.getParams()) + "){\n";
        List<String> codeLines = new ArrayList<>();
        for (JStatement statement : method.getStatements()) {
            codeLines.addAll(statement.toStatementCodeLines());
        }
        code += StringUtil.toString(codeLines, "\n");
        code += "\n}";

        return code;

    }

    public static String toCode(JAccessible accessible) {
        return switch (accessible) {
            case PUBLIC -> CodeConstants.PUBLIC;
            case PRIVATE -> CodeConstants.PRIVATE;
            case PROTECTED -> CodeConstants.PROTECTED;
            default -> "";
        };
    }

    public static String newVariableName(List<String> existNames, String name, String name2) {
        if (existNames == null || existNames.isEmpty()) {
            return name;
        }
        if (!existNames.contains(name)) {
            return name;
        }
        if (!existNames.contains(name2)) {
            return name2;
        }

        int count = 1;
        while (true) {
            name = name + count;
            if (!existNames.contains(name)) {
                return name;
            }
            count++;
        }
    }

    /**
     * public static List<OptionInfo> toOptionInfos(List<QotGetOptionChain.OptionItem> optionList) {
     * if (optionList == null) {
     * return Collections.emptyList();
     * }
     * <p>
     * List<OptionInfo> optionInfos = new ArrayList<>(optionList.size());
     * for (QotGetOptionChain.OptionItem optionItem : optionList) {
     * optionInfos.add(toOptionInfo(optionItem));
     * }
     * return optionInfos;
     * }
     */
    public static JMethod generateListConverterMethod(JClass sourceType, JClass target) {
        JMethod method = new JMethod();
        List<String> existNames = new ArrayList<>();

        // public static List<OptionInfo> toOptionInfos(List<QotGetOptionChain.OptionItem> optionList) {
        method.setAccessible(JAccessible.PUBLIC);
        method.setStatic(true);
        JClass targetListType = new JClass();
        targetListType.setName("List<" + target.getName() + ">");
        method.setReturnType(targetListType);
        method.setName("to" + toListName(target.getName()));

        JVariable sourceList = new JVariable();
        JClass sourceListType = new JClass();
        sourceListType.setName("List<" + sourceType.getName() + ">");
        sourceList.setType(sourceListType);
        String sourceListName = toListName(Util.firstLowCase(Util.getTypeName(sourceType.getName())));
        sourceList.setName(sourceListName);
        method.addParam(sourceList);
        existNames.add(sourceListName);

        /*
        if (optionList == null) {
            return Collections.emptyList();
        }
        */
        IfStatement ifStatement = new IfStatement();
        TwoExpression condition = new TwoExpression();
        VariableExpression leftExpression = new VariableExpression();
        leftExpression.setVariable(sourceList);
        condition.setLeftExpression(leftExpression);
        condition.setOperator(JOperator.EQUALS);
        ValueExpression rightExpression = new ValueExpression();
        rightExpression.setValue(CodeConstants.NULL);
        condition.setRightExpression(rightExpression);
        // if(sourceList==null)
        ifStatement.setConditionalExpression(condition);
        // return Collections.emptyList();
        ReturnStatement returnStatement = new ReturnStatement();
        ValueExpression valueExpression = new ValueExpression();
        valueExpression.setValue("Collections.emptyList()");
        returnStatement.setExpression(valueExpression);
        ifStatement.addStatement(returnStatement);
        method.addStatement(ifStatement);

        // 空一行
        method.addStatement(new EmptyLineStatement());


        // List<OptionInfo> optionInfos = new ArrayList<>(optionList.size());
        NewVariableAndGivenByMethodStatement newVariableStatement = new NewVariableAndGivenByMethodStatement();
        JVariable targetList = new JVariable();
        targetList.setType(targetListType);
        String targetListName = newVariableName(existNames, Util.firstLowCase(toListName(target.getName())), "targetList");
        targetList.setName(targetListName);
        newVariableStatement.setVariable(targetList);

        JMethod targetListConstructor = new JMethod();
        targetListConstructor.setName(CodeConstants.NEW + " ArrayList<>");
        newVariableStatement.setMethod(targetListConstructor);

        // optionList.size()
        VariableInvokeMethodStatement sizeExpression = new VariableInvokeMethodStatement();
        sizeExpression.setVariable(sourceList);
        JMethod sizeMethod = new JMethod();
        sizeMethod.setName("size");
        sizeExpression.setMethod(sizeMethod);
        newVariableStatement.addArgument(sizeExpression);

        method.addStatement(newVariableStatement);

        /*
        for (QotGetOptionChain.OptionItem optionItem : optionList) {
            optionInfos.add(toOptionInfo(optionItem));
        }
        */
        ForEachStatement loopStatement = new ForEachStatement();
        JVariable loopElement = new JVariable();
        String loopElementName = newVariableName(existNames, Util.firstLowCase(Util.getTypeName(sourceType.getName())), "e");
        loopElement.setName(loopElementName);
        loopElement.setType(sourceType);
        loopStatement.setElement(loopElement);
        loopStatement.setList(sourceList);
        //optionInfos.add()
        VariableInvokeMethodStatement addStatement = new VariableInvokeMethodStatement();
        addStatement.setVariable(targetList);
        JMethod addMethod = new JMethod();
        addMethod.setName("add");
        addStatement.setMethod(addMethod);
        // toOptionInfo(optionItem)
        StaticInvokeMethodStatement convertMethodStatement = new StaticInvokeMethodStatement();
        JMethod convertMethod = new JMethod();
        convertMethod.setName("to" + target.getName());
        convertMethodStatement.setMethod(convertMethod);
        VariableExpression loopElementExpression = new VariableExpression();
        loopElementExpression.setVariable(loopElement);
        convertMethodStatement.addArgument(loopElementExpression);
        addStatement.addArgument(convertMethodStatement);
        loopStatement.addStatement(addStatement);

        method.addStatement(loopStatement);

        returnStatement = new ReturnStatement();
        VariableExpression returnVariableExpression = new VariableExpression();
        returnVariableExpression.setVariable(targetList);
        returnStatement.setExpression(returnVariableExpression);
        method.addStatement(returnStatement);

        return method;
    }

    private static String toListName(String name) {
        if (name.endsWith("i")) {
            return name + "es";
        } else if (name.endsWith("y")) {
            return name.substring(0, name.length() - 1) + "ies";
        } else {
            return name + "s";
        }
    }
}
