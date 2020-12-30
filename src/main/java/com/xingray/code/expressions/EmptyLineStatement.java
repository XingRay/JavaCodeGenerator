package com.xingray.code.expressions;


import com.xingray.code.JStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * 空行
 */
public class EmptyLineStatement implements JStatement {

    /**
     * 空行的行数，默认为1
     */
    private int number = 1;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public List<String> toStatementCodeLines() {
        List<String> codeLines = new ArrayList<>();
        if (number > 1) {
            for (int i = 0, size = number; i < size; i++) {
                codeLines.add("\n");
            }
        } else {
            codeLines.add("\n");
        }
        return codeLines;
    }
}
