package io.github.jairovsky.intellijautodoc.text.sentences;

import org.fest.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SentenceAssemblerForClassesTest {

    private SentenceAssemblerForClasses assembler;

    @Before
    public void setUp() {

        assembler = new SentenceAssemblerForClasses();
    }

    @Test
    public void testAssembleSentece_givenClassName_shouldReturnSentence() {

        ArrayList<String> strings = Lists.newArrayList("MyPojo");

        String sentence = assembler.assembleSentence(strings);

        assertEquals("The class MyPojo.", sentence);
    }
}