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
### 3 已char数组为参数的构造方法
    public String(char value[]) {
       this.value = Arrays.copyOf(value, value.length);
    }
该构造方法将char数组value复制一份（而不是将value的引用复制一份），构造出一个新的String的对象。通过下面的测试用例和内存分析图来说明：

    @Test
    public void testString2 () {
        char[] charArray = {'a','b','c'};
        String str = new String(charArray);
    }
 ![image](https://raw.githubusercontent.com/csmervyn/java8-source-learn/master/image/java8-source-learn-lang/%E5%B7%B2char%E6%95%B0%E7%BB%84%E4%B8%BA%E5%8F%82%E6%95%B0%E7%9A%84%E6%9E%84%E9%80%A0%E6%96%B9%E6%B3%95-%E5%86%85%E5%AD%98%E5%88%86%E6%9E%90.png)

    public String(char value[], int offset, int count) {
       if (offset < 0) {
           throw new StringIndexOutOfBoundsException(offset);
       }
       if (count <= 0) {
           if (count < 0) {
               throw new StringIndexOutOfBoundsException(count);
           }
           if (offset <= value.length) {
               this.value = "".value;
               return;
           }
       }
       // Note: offset or count might be near -1>>>1.
       if (offset > value.length - count) {
           throw new StringIndexOutOfBoundsException(offset + count);
       }
       this.value = Arrays.copyOfRange(value, offset, offset+count);
    }
该构造方法将char数组value从offset索引开始拷贝count个char字符（包含offset索引位置），构造出一个新的String对象。
