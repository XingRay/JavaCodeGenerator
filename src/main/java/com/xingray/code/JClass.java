package com.xingray.code;

import com.xingray.code.generate.CodeConstants;
import com.xingray.code.util.Util;

import java.util.ArrayList;
import java.util.List;

public class JClass extends JType {

    private List<JField> fields;
    private List<JMethod> methods;
    private List<JMethod> constructors;

    public List<JField> getFields() {
        return fields;
    }

    public void setFields(List<JField> fields) {
        this.fields = fields;
    }

    public List<JMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<JMethod> methods) {
        this.methods = methods;
    }

    public List<JMethod> getConstructors() {
        return constructors;
    }

    public void setConstructors(List<JMethod> constructors) {
        this.constructors = constructors;
    }

    @Override
    public String toString() {
        return "JClass{" +
                "fields=" + fields +
                ", methods=" + methods +
                '}';
    }

    public void addMethod(JMethod method) {
        if (methods == null) {
            methods = new ArrayList<>();
        }
        methods.add(method);
    }

    public void addConstructor(JMethod constructor) {
        if (constructors == null) {
            constructors = new ArrayList<>();
        }
        constructors.add(constructor);
    }

    public void addConstructMethod(JAccessible accessible, List<JVariable> params) {
        addMethod(Util.getConstructor(this, accessible, params));
    }

    public JMethod getDefaultConstruct() {
        if (constructors == null || constructors.isEmpty()) {
            JMethod defaultConstructor = createDefaultConstructor();
            addConstructor(defaultConstructor);
        }
        return constructors.get(0);
    }

    private JMethod createDefaultConstructor() {
        JMethod constructor = new JMethod();

        constructor.setAccessible(JAccessible.PUBLIC);
        constructor.setStatic(true);
        constructor.setReturnType(this);
        constructor.setName(CodeConstants.NEW + " " + getName());

        return constructor;
    }
}
