<center><h1>guava简介</h1></center>
#Optional使用：
```java
package com.andy.guava;

import java.util.Set;

import com.google.common.base.Optional;

public class OptionalDemo {
	public static void main(String[] args) {
		/*Optional创建*/
		//创建指定引用的Optional,若引用为空则快速失败
		Optional<Integer> optional = Optional.of(1);
		//创建引用缺失的Optional
		Optional<Integer> optional2 = Optional.absent();
		//创建指定引用的Optional，若引用为null则表示引用缺失
		Optional<Integer> optional3 = Optional.fromNullable(null);
		Optional<Integer> optional4 = Optional.fromNullable(4);
		//判断引用是否为null
		boolean present = optional3.isPresent();
		
		//get()获取Optional包含的引用，若引用缺失，则抛出异常
		Integer integer1 = optional4.get();
		
		//or(T)获取optional中包含的引用，若引用缺失则返回特定的值
		Integer integer2 = optional3.or(3);
		System.out.println(integer2);
		
		//orNull()返回optional包含的引用，若引用缺失，则返回null
		Integer orNull = optional3.orNull();
		
		//asSet():返回 Optional 所包含引用的单例不可变集，如果引用存在，返回一个只有
		//单一元素的集合，如果引用缺失，返回一个空集合。
		Set<Integer> asSet = optional2.asSet();
		Set<Integer> asSet2 = optional4.asSet();
		
		
	}
}

```

#前置条件的使用
```java
package com.andy.guava;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Preconditions.checkPositionIndex;

public class PreconditionsDemo {
	public static void main(String[] args) {
		/*
		 * 每个方法都有三个变种： 
		 * 1.没有额外参数：抛出的异常中没有错误消息； 
		 * 2.有一个 Object 对象作为额外参数：抛出的异常使用Object.toString() 作为错误消息；  
		 * 3.有一个 String 对象作为额外参数，并且有一组任意数量的附加 Object
		 * 对象：这个变种处理异常消息的方式 有点类似 printf，但考虑 GWT 的兼容性和效率，只支持%s 指示符
		 */
		int i= 2;
		int j= 3;
		
		String nullString = null;
		String string = "";
		
		//没有任何错误消息
		checkArgument(i>j); 
		
		//object对象做为额外参数，object.toString()做为错误消息
		checkArgument(i>j,"检查异常");
		
		//有一个 String 对象作为额外参数，并且有一组任意数量的附加 Object
		checkArgument(i>j,"expected i>j,but %s<%s",i,j);
		
		checkNotNull(nullString);
		
		//checkState()用来检查对象的某些状态
		checkState(i>j);
		
		checkPositionIndex(3, 5);
	}
}


```

#常见Objects方法
```java
package com.andy.guava;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

public class ObjectsDemo {
	public static void main(String[] args) {
		// 常见Objects方法
		/*1.equal(a,b)
		 * 当一个对象中的字段可以为 null 时，实现 Object.equals 方法会很痛苦，因为不得不分别对它们进行 null 检 查。使用
		 * Objects.equal 帮助你执行 null 敏感的 equals 判断，从而避免抛出 NullPointerException。
		 */
		Objects.equal(1, 2); //return false
		Objects.equal(null, 3); //return false
		Objects.equal("a", null); //return false
		Objects.equal(null, null); //return true
		
		/*
		 * 2.toString()方法
		 */
		// Returns "ClassName{x=1}"
		Objects.toStringHelper(this).add("x", 1).toString();
		// Returns "MyObject{x=1}"
		Objects.toStringHelper("MyObject").add("x", 1).toString();
	}
}
```
#强大的集合工具类
