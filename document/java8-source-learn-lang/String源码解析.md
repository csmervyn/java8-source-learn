本文中的源码源于JDK 8
# String类的定义
    public final class String
        implements java.io.Serializable, Comparable<String>, CharSequence{
        ...
     }
String类被设计成final的，避免被继承。同时String类实现了Serializable、Comparable、CharSequence接口。其中Serializable接口
为标记接口，用来实现序列化和反序列化。【Comparable和CharSequence未完待续】
# String类的属性
    /** The value is used for character storage. */
    private final char value[];
    /** Cache the hash code for the string */
    private int hash; // Default to 0 
主要有2个属性，final的char数组value和默认值为0的hash。其中char数组value用来存储String对象的字符序列，并且其为final的，
这也就是大家常说的“不可变字符串”的由来；hash用来存储String对象的hash code值。       
# String类的构造方法
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

## 3 以char数组为参数的构造方法
* （1）


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
* （2）


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

![image](https://raw.githubusercontent.com/csmervyn/java8-source-learn/master/image/java8-source-learn-lang/%E5%B7%B2char%E6%95%B0%E7%BB%84%E4%B8%BA%E5%8F%82%E6%95%B0%E7%9A%84%E6%9E%84%E9%80%A0%E6%96%B9%E6%B3%951-%E5%86%85%E5%AD%98%E5%88%86%E6%9E%90.png)    
## 4 以byte数组为参数的构造方法
String构造方法大多以byte数组为参数，这些构造方法可以分为3类：指定字符集；指定字符集名称；不指定字符集。
### 4.1 指定字符集
* （1）


    public String(byte bytes[], int offset, int length, Charset charset) {
        if (charset == null)
            throw new NullPointerException("charset");
        checkBounds(bytes, offset, length);
        this.value =  StringCoding.decode(charset, bytes, offset, length);
    }
该构造方法将byte数组bytes从offset索引位置开始（包含offset位置）的length个byte，使用指定的charset字符集进行解码，生成一
个char数组，然后将该char数组的引用赋给新生成String对象的内部的char数组value。 

* （2）


    public String(byte bytes[], Charset charset) {
        this(bytes, 0, bytes.length, charset);
    }
该构造方法调用了4.1 （1）中的构造方法，将byte数组bytes，使用指定的charset字符集进行解码，从而生成一个新的String对象。    
### 4.2 指定字符集名称
* （1）


    public String(byte bytes[], int offset, int length, String charsetName)
            throws UnsupportedEncodingException {
        if (charsetName == null)
            throw new NullPointerException("charsetName");
        checkBounds(bytes, offset, length);
        this.value = StringCoding.decode(charsetName, bytes, offset, length);
    }
该构造方法类似于4.1 （1）的构造方法，区别仅是4.1 （1）的构造方法的入参为字符集，而该构造方法的入参为字符集的名称。该构造方法将
byte数组从bytes从offset索引位置（包括offset位置）开始，的length个byte，使用指定的charsetName字符集名称所对应的字符集进行
解码生成一个char数组，然后将该char数组的引用赋给新生成String对象的内部的char数组value。
 
* （2）

    public String(byte bytes[], String charsetName)
            throws UnsupportedEncodingException {
        this(bytes, 0, bytes.length, charsetName);
    }    
该构造方法调用了4.2 （1）的构造方法，将byte数组bytes，使用指定的charsetName字符集名称所对应的字符集进行解码，从而生成一个新
的String对象。   
### 4.3 不指定字符集
* （1）


    public String(byte bytes[], int offset, int length) {
        checkBounds(bytes, offset, length);
        this.value = StringCoding.decode(bytes, offset, length);
    }
该构造方法将byte数组从bytes从offset索引位置（包括offset位置）开始，的length个byte，使用平台的默认字符集进行解码生成一个char
数组，然后将该char数组的引用赋给新生成String对象的内部的char数组value。 

* （2）

   
    public String(byte bytes[]) {
        this(bytes, 0, bytes.length);
    }
该构造方法调用4.3 （1）中的构造方法将byte数组bytes，使用平台的默认字符集进行解码，从而生成一个新的String对象。
总结：对于“以byte数组为参数的构造方法”，因为不同平台的默认字符集可能不相同，为了避免不同平台中乱码问题，建议不要使用
“不指定字符集”的构造方法。
## 5 以StringBuffer为参数的构造方法
    public String(StringBuffer buffer) {
        synchronized(buffer) {
            this.value = Arrays.copyOf(buffer.getValue(), buffer.length());
        }
    }
该构造方法将StringBuffer的buffer的内部char数组value复制一份，构造一个String对象。注意该构造方法使用了synchronized语句块
保证buffer对象的线程安全性。    
## 6 以StringBuilder为参数的构造方法
    public String(StringBuilder builder) {
        this.value = Arrays.copyOf(builder.getValue(), builder.length());
    }
该构造方法将StringBuilder的builder的内部char数组value复制一份，构造一个String对象。 
## 7 以代码点int数组为参数的构造方法（待添加）
# String类常用方法
## 1 length()
    public int length() {
        return value.length;
    }
该方法是求取字符串的长度。源码比较简单，返回的是String对象内部char数组value的长度。
## 2 isEmpty()
    public boolean isEmpty() {
        return value.length == 0;
    }
该方法是判断字符串是否为空串。该方法的源码也比较简单，判断String对象内部char数组value的长度是否为0。
## 3 toString()
    public String toString() {
        return this;
    }
该方法是将对象转换成String类型的对象。因为String对象本身就是String类型的，返回的是其本身。
## 4 substring(int beginIndex, int endIndex)和substring(int beginIndex)
* substring(int beginIndex, int endIndex)


    public String substring(int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            throw new StringIndexOutOfBoundsException(beginIndex);
        }
        if (endIndex > value.length) {
            throw new StringIndexOutOfBoundsException(endIndex);
        }
        int subLen = endIndex - beginIndex;
        if (subLen < 0) {
            throw new StringIndexOutOfBoundsException(subLen);
        }
        return ((beginIndex == 0) && (endIndex == value.length)) ? this
                : new String(value, beginIndex, subLen);
    }
该方法是求当前String对象的子串。子串为截取从原始串的beginIndex索引位置开始（包含该beginIndex）到 endIndex为止（不包含
endIndex位置）的字符串。
