本文中的源码源于JDK 8
## 1.Serializable类的定义
    /**
     * Serializability of a class is enabled by the class implementing the
     * java.io.Serializable interface. Classes that do not implement this
     * interface will not have any of their state serialized or
     * deserialized.  All subtypes of a serializable class are themselves
     * serializable.  The serialization interface has no methods or fields
     * and serves only to identify the semantics of being serializable. <p>
     *
     * To allow subtypes of non-serializable classes to be serialized, the
     * subtype may assume responsibility for saving and restoring the
     * state of the supertype's public, protected, and (if accessible)
     * package fields.  The subtype may assume this responsibility only if
     * the class it extends has an accessible no-arg constructor to
     * initialize the class's state.  It is an error to declare a class
     * Serializable if this is not the case.  The error will be detected at
     * runtime. <p>
     *
     * During deserialization, the fields of non-serializable classes will
     * be initialized using the public or protected no-arg constructor of
     * the class.  A no-arg constructor must be accessible to the subclass
     * that is serializable.  The fields of serializable subclasses will
     * be restored from the stream. <p>
     *
     * When traversing a graph, an object may be encountered that does not
     * support the Serializable interface. In this case the
     * NotSerializableException will be thrown and will identify the class
     * of the non-serializable object. <p>
     *
     * Classes that require special handling during the serialization and
     * deserialization process must implement special methods with these exact
     * signatures:
     *
     * <PRE>
     * private void writeObject(java.io.ObjectOutputStream out)
     *     throws IOException
     * private void readObject(java.io.ObjectInputStream in)
     *     throws IOException, ClassNotFoundException;
     * private void readObjectNoData()
     *     throws ObjectStreamException;
     * </PRE>
     *
     * <p>The writeObject method is responsible for writing the state of the
     * object for its particular class so that the corresponding
     * readObject method can restore it.  The default mechanism for saving
     * the Object's fields can be invoked by calling
     * out.defaultWriteObject. The method does not need to concern
     * itself with the state belonging to its superclasses or subclasses.
     * State is saved by writing the individual fields to the
     * ObjectOutputStream using the writeObject method or by using the
     * methods for primitive data types supported by DataOutput.
     *
     * <p>The readObject method is responsible for reading from the stream and
     * restoring the classes fields. It may call in.defaultReadObject to invoke
     * the default mechanism for restoring the object's non-static and
     * non-transient fields.  The defaultReadObject method uses information in
     * the stream to assign the fields of the object saved in the stream with the
     * correspondingly named fields in the current object.  This handles the case
     * when the class has evolved to add new fields. The method does not need to
     * concern itself with the state belonging to its superclasses or subclasses.
     * State is saved by writing the individual fields to the
     * ObjectOutputStream using the writeObject method or by using the
     * methods for primitive data types supported by DataOutput.
     *
     * <p>The readObjectNoData method is responsible for initializing the state of
     * the object for its particular class in the event that the serialization
     * stream does not list the given class as a superclass of the object being
     * deserialized.  This may occur in cases where the receiving party uses a
     * different version of the deserialized instance's class than the sending
     * party, and the receiver's version extends classes that are not extended by
     * the sender's version.  This may also occur if the serialization stream has
     * been tampered; hence, readObjectNoData is useful for initializing
     * deserialized objects properly despite a "hostile" or incomplete source
     * stream.
     *
     * <p>Serializable classes that need to designate an alternative object to be
     * used when writing an object to the stream should implement this
     * special method with the exact signature:
     *
     * <PRE>
     * ANY-ACCESS-MODIFIER Object writeReplace() throws ObjectStreamException;
     * </PRE><p>
     *
     * This writeReplace method is invoked by serialization if the method
     * exists and it would be accessible from a method defined within the
     * class of the object being serialized. Thus, the method can have private,
     * protected and package-private access. Subclass access to this method
     * follows java accessibility rules. <p>
     *
     * Classes that need to designate a replacement when an instance of it
     * is read from the stream should implement this special method with the
     * exact signature.
     *
     * <PRE>
     * ANY-ACCESS-MODIFIER Object readResolve() throws ObjectStreamException;
     * </PRE><p>
     *
     * This readResolve method follows the same invocation rules and
     * accessibility rules as writeReplace.<p>
     *
     * The serialization runtime associates with each serializable class a version
     * number, called a serialVersionUID, which is used during deserialization to
     * verify that the sender and receiver of a serialized object have loaded
     * classes for that object that are compatible with respect to serialization.
     * If the receiver has loaded a class for the object that has a different
     * serialVersionUID than that of the corresponding sender's class, then
     * deserialization will result in an {@link InvalidClassException}.  A
     * serializable class can declare its own serialVersionUID explicitly by
     * declaring a field named <code>"serialVersionUID"</code> that must be static,
     * final, and of type <code>long</code>:
     *
     * <PRE>
     * ANY-ACCESS-MODIFIER static final long serialVersionUID = 42L;
     * </PRE>
     *
     * If a serializable class does not explicitly declare a serialVersionUID, then
     * the serialization runtime will calculate a default serialVersionUID value
     * for that class based on various aspects of the class, as described in the
     * Java(TM) Object Serialization Specification.  However, it is <em>strongly
     * recommended</em> that all serializable classes explicitly declare
     * serialVersionUID values, since the default serialVersionUID computation is
     * highly sensitive to class details that may vary depending on compiler
     * implementations, and can thus result in unexpected
     * <code>InvalidClassException</code>s during deserialization.  Therefore, to
     * guarantee a consistent serialVersionUID value across different java compiler
     * implementations, a serializable class must declare an explicit
     * serialVersionUID value.  It is also strongly advised that explicit
     * serialVersionUID declarations use the <code>private</code> modifier where
     * possible, since such declarations apply only to the immediately declaring
     * class--serialVersionUID fields are not useful as inherited members. Array
     * classes cannot declare an explicit serialVersionUID, so they always have
     * the default computed value, but the requirement for matching
     * serialVersionUID values is waived for array classes.
     *
     * @author  unascribed
     * @see java.io.ObjectOutputStream
     * @see java.io.ObjectInputStream
     * @see java.io.ObjectOutput
     * @see java.io.ObjectInput
     * @see java.io.Externalizable
     * @since   JDK1.1
     */
    public interface Serializable {
    }
## 序列化与反序列化
序列化是指将数据结构或对象转化成特定的格式，以留待后续在相同或另一台计算机环境中，能恢复原先状态的过程。这种特定的格式可以是字节序列、XML或者JSON
等格式。反序列化则与序列化的过程相反，即从特定的格式数据中恢复出之前的数据结构或对象的状态。
## 为什么要实现序列化和反序列化
使用序列化和反序列化的目的是为了将数据结构或对象转化成特定的格式，便于存储或网络传输。存储的例子，如Ehcache在将对象缓存到本地的磁盘的时候，就需要所缓存的对象实现序列化。网络传输的例子，RESTful接口需要将数据
对象以JSON格式传输给被调方，这里的序列化就是将对象转化成JSON格式的过程。
## Java如何实现序列化与反序列化
序列化和反序列化并不是Java所专有的功能，Python和PHP等语言也具有这样的功能。Java实现序列化需实现Serializable接口。Serializable是一个标记接口，即表明实现Serializable接口的类的对象可序列化。
### serialVersionUID
serialVersionUID在序列化和反序列化的过程中用来标明可序列化类的版本号。可序列化类的对象在序列化的过程中，会将该序列化类的serialVersionUID
写入到字节序列中；反序列化的过程中，会从字节序列中读取serialVersionUID，并验证该serialVersionUID与将要使用的实体类的serialVersionUID
是否一致。若一致，则允许反序列化。否则，反序列化失败，抛出InvalidClassException。serialVersionUID存在的目的是为了保证序列化和反序列化过程中使用的可序列化类的版本是一致的。

    private static final long serialVersionUID = 42L
Serializable接口的说明文档里，建议每一个实现Serializable接口的类，均需要明确声明一个私有的、静态的、不可变的long类型的serialVersionUID
。若不明确声明，序列化的过程中会根据可序列化类的多个方面的信息生成一个默认的serialVersionUID。而默认的serialVersionUID会因Java
编译器不同而受影响，从而直接影响反序列化能否成功。另外因为数组类常常有一个默认的计算出的serialVersionUID值，并且不需要与字节序列中的serialVersionUID
匹配，所以数组类不需要明确声明一个serialVersionUID。
。   
    
    
