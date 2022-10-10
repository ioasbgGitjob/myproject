## lambda所有的接口
* java.util.function提供了大量的函数式接口
* Predicate 接收参数T对象，返回一个boolean类型结果 示例传送门
* Consumer 接收参数T对象，没有返回值
* Function 接收参数T对象，返回R对象
* Supplier 不接受任何参数，直接通过get()获取指定类型的对象
* UnaryOperator 接口参数T对象，执行业务处理后，返回更新后的T对象
* BinaryOperator 接口接收两个T对象，执行业务处理后，返回一个T对象

## 自定义一个lambda函数接口
SelfLambda 接口类  
SelfMain lambda的具体实现

