package io.github.jairovsky.intellijautodoc.text;

import java.util.List;

public interface MethodNameSplitter {

    List<String> splitWords(String methodName);
}
