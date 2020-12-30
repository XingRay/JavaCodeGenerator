package com.xingray.code;

import java.util.List;

/**
 * 表达式
 * 表达式是可以被求值的代码,所以它可写在赋值语句等号的右侧。
 */
public interface JExpression {
    List<String> toExpressionCodeLines();
}
