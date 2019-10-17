# [遍历map的四种方法](https://www.cnblogs.com/QQ846300233/p/6043326.html)

### 方法一   在for循环中使用entries实现Map的遍历 

推荐，尤其是容量大时


在for-each循环中使用entries来遍历这是最常见的并且在大多数情况下也是最可取的遍历方式。在键值都需要时使用。
注意：for-each循环在Java 5中被引入所以该方法只能应用于java 5或更高的版本中。                                       

 如果你遍历的是一个空的map对象，for-each循环将抛出NullPointerException，因此在遍历前你总是应该检查空引用。  foreach entrySet耗时和for iterator entrySet接近。 foreach底层也是iterator。

```
 Map<Integer, Integer> map = new HashMap<Integer, Integer>();
 for (Map.Entry<Integer, Integer> entry : map.entrySet())
 {  
       System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
 }  
```

### 方法二  通过Map.keySet遍历key和value

（1）一般适用于只需要map中的key或者value时使用，在性能上比使用entrySet较好 
通过键找值遍历（效率低）
作为方法一的替代，这个代码看上去更加干净；但实际上它相当慢且无效率。因为从键取值是耗时的操作（与方法一相比，在不同的Map实现中该方法慢了20%~200%）。如果你安装了FindBugs，它会做出检查并警告你关于哪些是低效率的遍历。所以尽量避免使用。

```
  for (Integer key : map.keySet())
  {            
     Integer value = map.get(key);            
     System.out.println("Key = " + key + ", Value = " + value);          
  } 
```

### 方法三、通过Map.values()遍历所有的value，但不能遍历key（只遍历键或者值）
在for-each循环中遍历keys或values。
如果只需要map中的键或者值，你可以通过keySet或values来实现遍历，而不是用entrySet。
该方法比entrySet遍历在性能上稍好（快了10%），而且代码更加干净。



```
 //遍历map中的键 
 for (Integer key : map.keySet())
 {                    
     System.out.println("Key = " + key);                    
 }                    
 //遍历map中的值                
 for (Integer value : map.values())
 {            
     System.out.println("Value = " + value);        
 } 
```



### 方法四        使用Iterator遍历 

你也可以在keySet和values上应用同样的方法。该种方式看起来冗余却有其优点所在。

首先，在老版本java中这是惟一遍历map的方式。         

另一个好处是，你可以在遍历时调用iterator.remove()来删除entries，另两个方法则不能。根据javadoc的说明，如果在for-each遍历中尝试使用此方法，结果是不可预测的。从性能方面看，该方法类同于for-each遍历（即方法二）的性能。

```
Map<Integer, Integer> map = new HashMap<Integer, Integer>(); 
Iterator<Map.Entry<Integer, Integer>> entries = map.entrySet().iterator();
while (entries.hasNext())
{ 
    Map.Entry<Integer, Integer> entry = entries.next(); 
        System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
}
```

总结
如果仅需要键(keys)或值(values)使用方法三。如果你使用的语言版本低于java 5，或是打算在遍历时删除entries，必须使用方法四。否则使用方法一(键值都要)。



```java
public static void main(String[] args) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("1", "value1");
    map.put("2", "value2");
    map.put("3", "value3");
    // 第一种：普遍使用，二次取值
    System.out.println("通过Map.keySet遍历key和value：");
    for (String key : map.keySet()) {
        System.out.println("key= " + key + " and value= " + map.get(key));
    }
    // 第二种
    System.out.println("通过Map.entrySet使用iterator遍历key和value：");
    Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry<String, String> entry = it.next();
        System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
    }
    // 第三种：推荐，尤其是容量大时
    System.out.println("通过Map.entrySet遍历key和value");
    for (Map.Entry<String, String> entry : map.entrySet()) {
        System.out.println("key= " + entry.getKey() + " and value= "  + entry.getValue());
    }
    // 第四种
    System.out.println("通过Map.values()遍历所有的value，但不能遍历key");
    for (String v : map.values()) {
        System.out.println("value= " + v);
    }
}
```

