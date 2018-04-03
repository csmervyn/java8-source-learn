package com.mervyn;

import org.junit.Assert;
import org.junit.Test;

public class StringTest {
    @Test
    public void testString() {
        String emptyStr = "";
        String emptyStrCreatedByConstruct = new String();
        Assert.assertFalse(emptyStr == emptyStrCreatedByConstruct);
        Assert.assertTrue(emptyStr.equals(emptyStrCreatedByConstruct));
    }

    @Test
    public void testString1() {
        String original = "abc";
        String current = new String(original);
        Assert.assertFalse(original == current);
        Assert.assertTrue(original.equals(current));
        System.out.println(original.hashCode());
    }

    @Test
    public void testString2 () {
        char[] charArray = {'a','b','c'};
        String str = new String(charArray);
    }

    @Test
    public void testString3 () {
        char[] charArray = {'a','b','c','d'};
        String str = new String(charArray,1,2);
        Assert.assertTrue("bc".equals(str));
    }
}
