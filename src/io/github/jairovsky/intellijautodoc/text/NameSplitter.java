package io.github.jairovsky.intellijautodoc.text;

import java.util.List;

public interface NameSplitter {

    List<String> splitWords(String methodName);
}
