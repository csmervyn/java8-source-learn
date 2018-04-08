# String构造方法浅析
  本文中的源码源于JDK 8
## 1 无参的构造方法
    public String() {
        this.value = "".value;
    }
该构造方法是将空串""内部的char数组的引用复制一份，构造出一个空的String对象。该对象与空串""的区别，通过下面的测试用例和对象内存
分析图来说明。

    @Test
    public void testString() {
        String emptyStr = "";
        String emptyStrCreatedByConstruct = new String();
        Assert.assertFalse(emptyStr == emptyStrCreatedByConstruct);
        Assert.assertTrue(emptyStr.equals(emptyStrCreatedByConstruct));
    }

  ![image](https://raw.githubusercontent.com/csmervyn/java8-source-learn/master/image/java8-source-learn-lang/String%E6%97%A0%E5%8F%82%E6%9E%84%E9%80%A0%E6%96%B9%E6%B3%95-%E5%86%85%E5%AD%98%E5%88%86%E6%9E%90.png)

通过上面的测试用例和对象内存分析图，可以看出：""对象与用String()构造出的对象不是同一对象；""对象与用String()构造出的对象的内容
是相等的。
## 2 以String为参数的构造方法
    public String(String original) {
        this.value = original.value;
        this.hash = original.hash;
    }
该构造方法是将original对象的char数组的引用和hash值复制一份，构造出一个新的String对象。该对象与original对象的区别，通过下面的测
试用例和对象内存分析图来说明。

    @Test
    public void testString1() {
        String original = "abc";
        String current = new String(original);
        Assert.assertFalse(original == current);
        Assert.assertTrue(original.equals(current));
        System.out.println(original.hashCode());
    }  
     
![image](https://raw.githubusercontent.com/csmervyn/java8-source-learn/master/image/java8-source-learn-lang/%E4%BB%A5String%E4%B8%BA%E5%85%A5%E5%8F%82%E7%9A%84%E6%9E%84%E9%80%A0%E6%96%B9%E6%B3%95-%E5%86%85%E5%AD%98%E5%88%86%E6%9E%90.png)

## 3 已char数组为参数的构造方法
### ①
    public String(char value[]) {
       this.value = Arrays.copyOf(value, value.length);
    }
该构造方法将char数组value复制一份（而不是将value的引用复制一份），构造出一个新的String的对象。通过下面的测试用例和对象内存分析
图，来展示该构造函数的用法。

    @Test
    public void testString2 () {
        char[] charArray = {'a','b','c'};
        String str = new String(charArray);
    }
 ![image](https://raw.githubusercontent.com/csmervyn/java8-source-learn/master/image/java8-source-learn-lang/%E5%B7%B2char%E6%95%B0%E7%BB%84%E4%B8%BA%E5%8F%82%E6%95%B0%E7%9A%84%E6%9E%84%E9%80%A0%E6%96%B9%E6%B3%95-%E5%86%85%E5%AD%98%E5%88%86%E6%9E%90.png)
### ②
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
该构造方法将char数组value从offset索引开始拷贝count个char字符（包含offset索引位置），构造出一个新的String对象。通过下面的测试用
例和对象内存分析图展示该构造函数的用法。

    @Test
    public void testString3 () {
        char[] charArray = {'a','b','c','d'};
        String str = new String(charArray,1,2);
        Assert.assertTrue("bc".equals(str));
    }
上面测试用例的对象内存分析图：
![image](https://raw.githubusercontent.com/csmervyn/java8-source-learn/master/image/java8-source-learn-lang/%E5%B7%B2char%E6%95%B0%E7%BB%84%E4%B8%BA%E5%8F%82%E6%95%B0%E7%9A%84%E6%9E%84%E9%80%A0%E6%96%B9%E6%B3%951-%E5%86%85%E5%AD%98%E5%88%86%E6%9E%90.png)    

