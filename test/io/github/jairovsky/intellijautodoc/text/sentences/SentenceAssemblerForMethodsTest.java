package io.github.jairovsky.intellijautodoc.text.sentences;

import org.fest.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SentenceAssemblerForMethodsTest {

    private SentenceAssemblerForMethods assembler;

    @Before
    public void setUp() {

        assembler = new SentenceAssemblerForMethods();
    }

    @Test
    public void assembleSentence_givenListOfWords_shouldReturnASentenceWithTheWordsSeparatedBySpaces() {

        ArrayList<String> words = Lists.newArrayList("set", "Full", "Name");

        String sentence = assembler.assembleSentence(words);

        assertEquals("Set full name.", sentence);
    }

    @Test(expected = IllegalArgumentException.class)
    public void assembleSentence_givenEmptyList_shouldThrowException() {

        List<String> l = null;

        assembler.assembleSentence(l);
    }

    @Test(expected = IllegalArgumentException.class)
    public void assembleSentence_givenEmptyList_shouldThrowException_2() {

        List<String> l = Lists.newArrayList();

        assembler.assembleSentence(l);
    }
}