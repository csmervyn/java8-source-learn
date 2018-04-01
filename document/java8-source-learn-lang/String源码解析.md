# String常用方法
  本文中的源码源于JDK 8
## 1 构造方法
### 1 无参的构造方法
    public String() {
        this.value = "".value;
    }
  该构造方法是将空串""内部的char数组的引用复制一份，构造出一个空的String对象。该对象与空串""的区别，通过下面的测试用例来说明：

    @Test
    public void testString() {
        String emptyStr = "";
        String emptyStrCreatedByConstruct = new String();
        Assert.assertFalse(emptyStr == emptyStrCreatedByConstruct);
        Assert.assertTrue(emptyStr.equals(emptyStrCreatedByConstruct));
    }

  上面的测试用例通过测试，表明：""对象与用String()构造出的对象不是同一对象；""对象与用String()构造出的对象的内容是相等的。
###
