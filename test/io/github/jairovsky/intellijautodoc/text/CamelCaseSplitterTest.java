package io.github.jairovsky.intellijautodoc.text;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CamelCaseSplitterTest {

    private CamelCaseSplitter splitter;

    @Before
    public void setUp() {
        splitter = new CamelCaseSplitter();
    }

    @Test
    public void testSplitWords_givenCamelCaseString_shouldReturnAListWithTheWordsOfTheString() {

        List<String> words = splitter.splitWords("setStringBuilder");

        assertEquals("set", words.get(0));
        assertEquals("String", words.get(1));
        assertEquals("Builder", words.get(2));
    }

    @Test
    public void testSplitWords_givenCamelCaseString_shouldReturnAListWithTheWordsOfTheString_2() {

        List<String> words = splitter.splitWords("getFullName");

        assertEquals("get", words.get(0));
        assertEquals("Full", words.get(1));
        assertEquals("Name", words.get(2));
    }

    @Test
    public void testSplitWords_givenNonCamelCaseString_shouldReturnTheSameString() {

        List<String> words = splitter.splitWords("hello_world");

        assertEquals("hello_world", words.get(0));
    }
}
