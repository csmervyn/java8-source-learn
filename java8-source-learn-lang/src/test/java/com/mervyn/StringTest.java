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
    public void testString2 () {
        char[] charArray = {'a','b','c'};
        String str = new String(charArray);
    }
}
