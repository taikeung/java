<center><h2>markdown简介</h2></center>
# 1.认识markdown
> Markdown 是一种用来写作的轻量级「标记语言」,它用简洁的语法代替排版，而不像一般我们用的字处理软件 Word 或 Pages 有大量的排版、字体设置。它使我们专心于码字，用「标记」语法，来代替常见的排版格式。

# 2.使用markdown的优点
> * 专注你的文字内容而不是排版样式，安心写作。
> * 轻松的导出 HTML、PDF 和本身的 .md 文件。
> * 纯文本内容，兼容所有的文本编辑器与字处理软件。
> * 随时修改你的文章版本，不必像字处理软件生成若干文件版本导致混乱。
> * 可读、直观、学习成本低。

# 3.markdown语法简介
## (1)标题*(建议在`#`的后面加上空格)*
> `# 一级标题`
> `## 二级标题`
> `### 三级标题`
> `#### 四级标题`
> `##### 五级标题`
> `###### 六级标题`

## (2)列表
### 1)无序列表(在文字的前面加上`-`或者`*`)
> `* 1.`
> `* 2.`
> `* 3.`

### 2)有序列表(在文字的前面加上`1.` `2.` `3.`
> `1. 1`
> `2. 2`
> `3. 3`

## (3)引用(在文字的前面加上`>`)
> `> 你好`

## (4)图片和链接
### 1)图片
> `![Mou icon](http://mouapp.com/Mou_128.png)`

##### 插入图片
![Mou icon](http://mouapp.com/Mou_128.png)

### 2)链接
> `[baidu](http://www.baidu.com)`

##### 插入链接
[baidu](http://www.baidu.com)

## (5)粗体和斜体
### 1)粗体
> `**粗体**`

**粗体**
### 2)斜体
> `*斜体*`

*斜体*

## (6)表格
| Tables        | Are           | Cool  |
| ------------- |:-------------:| -----:|
| col 3 is      | right-aligned | $1600 |
| col 2 is      | centered      |   $12 |
| zebra stripes | are neat      |    $1 |

## (7)代码框
### 1)行内
`System.out.println("你好");`

### 2)代码块
``` java
public class HelloWorld {
	public static void main(String[] args){
    	int i = 0;
        int j = 2;
        System.out.println(i+j);
    }
}
```

## (8)分割线`***`
***


