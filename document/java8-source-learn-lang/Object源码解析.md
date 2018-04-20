本文中的源码源于JDK 8
# Object类的定义
    /**
     * Class {@code Object} is the root of the class hierarchy.
     * Every class has {@code Object} as a superclass. All objects,
     * including arrays, implement the methods of this class.
     *
     * @author  unascribed
     * @see     java.lang.Class
     * @since   JDK1.0
     */
    public class Object {
      ...
    }
  “Object类是class层次结构的根，每个class都以Object类作为其超类。所有的对象，包括数组都实现了Object类的方法。”  这两句
话几乎被每个学习Java语言的人所熟知，但如何理解这两句话也困扰了好多人。我开始理解这两句话的时候有三个疑问：一、新建一个没
有继承其它类的类，java.lang.Object是如何成为该新创建类的默认父类的？二、新建一个继承其它类的类，该新建类的父类是谁？
三、新建一个类，可以显示继承java.lang.Object？
## 一、新建一个没有继承其它类的类，java.lang.Object是如何成为该新创建类的默认父类的？
  参考文献[1]中的作者给出了一个观点：
  * （1）、在编译源代码时，当遇到没有父类的类时，编译器会将其指定一个默认的父类（一般为Object），而虚拟机在处理到这个类
  时，由于这个类已经有一个默认的父类了，因此，VM仍然会按着常规的方法来处理每一个类。对于这种情况，从编译后的二进制角度来
  看，所有的类都会有一个父类<sup>[1]</sup>。
  * （2）、编译器仍然按着实际代码进行编译，并不会做额外的处理。如果一个类没有显式地继承于其他的类，编译后的代码仍然没有
  父类。然后由虚拟机运行二进制代码时，当遇到没有父类的类时，就会自动将这个类看成是Object类的子类（一般这类语言的默认父类
  都是Object）<sup>[1]</sup>。
  我们下面通过实验来验证，这种给没有父类的类指定默认父类java.lang.Object的功能是由编译器来实现的还是由虚拟机实现的。
  首先新建一个没有继承其它类的类Person:
  
        public class Person{
            public static void main(String[] args) {
                System.out.println(new Person().toString());
            }
        }
## 常用方法分析
### getClass()
    public final native Class<?> getClass();
  getClass方法是一个本地方法，返回一个对象的运行时类。该方法不能被子类重写。
### clone() 
    protected native Object clone() throws CloneNotSupportedException;
  这个也是本地方法，同时也是一个受保护的方法。需要注意的是该方法是“浅拷贝”的。Object子类中，这个方法的行为取决于该子类
是否实现了Cloneable接口。如果一个类实现了Cloneable接口，该类继承自Object类的clone方法返回该对象的逐域拷贝，否则就会抛出
CloneNotSupportedException。如果要实现一个类对象的“深拷贝”，除非该类的所有超类都提供了行为良好的clone实现，无论是公有
的还是受保护的方法，否则，都不可能这么做<sup>[2]</sup>。
### equals(Object obj)
    public boolean equals(Object obj) {
            return (this == obj);
    }
  这不是一个非本地方法。用来比较两个Object对象是否是同一个对象。子类重写equals方法的时候，需要保证equals方法满足：①自反性
②对称性③传递性④一致性⑤对于任何非null的引用值x,x.equals(null)必须返回false<sup>[2]</sup>。另外一点需注意：覆盖equals
时总要覆盖hashCode方法。
### hashCode()
    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link java.util.HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     *     an execution of a Java application, the {@code hashCode} method
     *     must consistently return the same integer, provided no information
     *     used in {@code equals} comparisons on the object is modified.
     *     This integer need not remain consistent from one execution of an
     *     application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     *     method, then calling the {@code hashCode} method on each of
     *     the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     *     according to the {@link java.lang.Object#equals(java.lang.Object)}
     *     method, then calling the {@code hashCode} method on each of the
     *     two objects must produce distinct integer results.  However, the
     *     programmer should be aware that producing distinct integer results
     *     for unequal objects may improve the performance of hash tables.
     * </ul>
     * <p>
     * As much as is reasonably practical, the hashCode method defined by
     * class {@code Object} does return distinct integers for distinct
     * objects. (This is typically implemented by converting the internal
     * address of the object into an integer, but this implementation
     * technique is not required by the
     * Java&trade; programming language.)
     *
     * @return  a hash code value for this object.
     * @see     java.lang.Object#equals(java.lang.Object)
     * @see     java.lang.System#identityHashCode
     */
    public native int hashCode();
  这个方法是一个本地方法。子类重写这个方法的时候，需要满足注释中说的以上三点要求。并且在每个覆盖了equals方法的类中，也必
须覆盖hashCode方法。另外一点需要注意的是，提供恰当的散列函数。
### toString()
    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())native
     * </pre></blockquote>
     *
     * @return  a string representation of the object.
     */
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }
  这是一个非本法方法。建议子类都覆盖这个方法，已提供值得关注的必要信息。便于日志的打印和排查问题。    
      
    
# 参考文献
  [1] http://www.blogjava.net/nokiaguy/archive/2008/05/06/198711.html
  [2] JoshuaBloch. Effective Java中文版.第2版[M]. 机械工业出版社, 2009.


  