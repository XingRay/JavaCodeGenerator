package com.xingray.code;

public class JType {

    private JAccessible accessible;
    private boolean isStatic;
    private boolean isAbstract;
    private boolean isFinal;
    private String name;
    private String packageName;

    public JAccessible getAccessible() {
        return accessible;
    }

    public void setAccessible(JAccessible accessible) {
        this.accessible = accessible;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "Type{" +
                "accessible=" + accessible +
                ", isStatic=" + isStatic +
                ", isAbstract=" + isAbstract +
                ", isFinal=" + isFinal +
                ", name='" + name + '\'' +
                '}';
    }

    public String getFullName() {
        return packageName + "." + name;
    }
}
