package com.xingray.code.java.generate;

import com.xingray.code.java.JVariable;
import com.xingray.javabase.interfaces.Mapper;
import com.xingray.util.StringUtil;

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

    public static String getTypeName(String name) {
        int index = name.indexOf('.');
        if (index < 0) {
            return name;
        }
        int lastIndex = name.lastIndexOf('.');
        return name.substring(lastIndex + 1);
    }
}
