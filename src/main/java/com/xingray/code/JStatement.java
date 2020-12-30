package com.xingray.code;

import java.util.List;

/**
 * 语句
 * 语句是一段可执行代码，不一定有值。
 */
public interface JStatement {
    List<String> toStatementCodeLines();
}
