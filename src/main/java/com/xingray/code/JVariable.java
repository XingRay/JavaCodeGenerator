package com.xingray.code;

/**
 * 变量，如局部变量，类成员变量，类静态变量，方法参数
 */
public class JVariable {
    private JType type;
    private String name;
    private boolean isFinal;

    public JType getType() {
        return type;
    }

    public void setType(JType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    @Override
    public String toString() {
        return "JVariable{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", isFinal=" + isFinal +
                '}';
    }
}
