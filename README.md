README
===========================
###　　　　　　　　　　　　Author:tyxo
###　　　　　　　　　 E-mail:tyxo842@163.com

===========================



##目录
* [开始介绍](#开始介绍)
    * 依赖库地址
* [图片](#图片)
    * 来源于网络的图片
    * GitHub仓库中的图片
* [列表](#列表)
    * 多级无序列表
    * 有序列表
* [代码高亮](#代码高亮)
* [表格](#表格) 
* [锚点](#锚点)

开始介绍
------
####依赖库地址
啥也不说,先上依赖,在dependencies内添加依赖地址:
```
    dependencies {
        compile 'com.tyxo:baseframelib:1.0.0'
    }
```
####用途
```
    这个库,为了实现baseActivity而创建,包含volley网络请求框架封装以及泛型解析.使用的时候,自己的类MyActivity继承BaseRecyclerActivity<实体bean>,
    然后重写三个方法,第一个setIds(这里面设置的界面布局,刷新控件swipeRefreshLayout和recyclerView),第二个requestNet(网络请求),第三个handleData(
    数据处理)
    例如:
        //首先设置id
        @Override
        protected void setIds() {
            this.setLayoutId(R.layout.activity_test_baseframe);//设置布局文件
            this.setRefreshLId(R.id.swipRefreshL);//设置刷新控件swipeRefreshLayout
            this.setRecyclerVId(R.id.recyclerView);//设置recyclerView
        }
    
        //处理返回数据
        @Override
        protected void handleData(TestBean beanB) {
            Log.v("tyxo","TestBaseFrame handleDta ");
        }
    
        @Override
        protected void requestNet() {
            super.requestNet();
            //做网络请求操作
        }
    
```

###文字高亮
文字高亮功能能使行内部分文字高亮(也适合做一篇文章的tag)，使用一对反引号。语法：
```
`linux` `网络编程` `socket` `Android`
```
效果：`linux` `网络编程` `socket` `Android`

####斜体、粗体、删除线
|语法|效果|
|----|-----
|`*斜体1*`|*斜体1*
|`_斜体2_`|_斜体2_
|`**粗体1**`|**粗体1**
|`__粗体2__`|__粗体2__
|`这是一个 ~~删除线~~`|这是一个 ~~删除线~~
|`***斜粗体1***`|***斜粗体1***
|`___斜粗体2___`|___斜粗体2___
|`***~~斜粗体删除线1~~***`|***~~斜粗体删除线1~~***
|`~~***斜粗体删除线2***~~`|~~***斜粗体删除线2***~~


图片
------
基本格式：
```
![alt](URL title)
```
alt和title即对应HTML中的alt和title属性（都可省略）：
- alt表示图片显示失败时的替换文本
- title表示鼠标悬停在图片时的显示文本（注意这里要加引号）

URL即图片的url地址，如果引用本仓库中的图片，直接使用**相对路径**就可了，如果引用其他github仓库中的图片要注意格式，
即：`仓库地址/raw/分支名/图片路径`，如：
```
https://github.com/tyxo842/MobileSafe/raw/master/MobileSafeApp/src/main/assets/pikaqiu.gif
```

|#|语法|效果|
|---|---|----
|1|`![baidu](http://www.baidu.com/img/bdlogo.gif "百度logo")`|![baidu](http://www.baidu.com/img/bdlogo.gif "百度logo")
|2|`![][pikaqiu]`|![][pikaqiu]

注意例2的写法使用了**URL标识符**的形式，在[链接](#链接)一节有介绍。
>在文末有pikaqiu的定义：
```
[pikaqiu]:https://github.com/tyxo842/MobileSafe/raw/master/MobileSafeApp/src/main/assets/pikaqiu.gif
```


##列表

###多级无序列表
* 语言
    * 编程语言
        * Java

###有序列表
####一般效果
就是在数字后面加一个点，再加一个空格。不过看起来起来可能不够明显。    
面向对象的三个基本特征：

1. 封装
2. 继承
3. 多态

####有序列表自动排序
也可以在第一行指定`1. `，而接下来的几行用星号`*`（或者继续用数字1. ）就可以了，它会自动显示成2、3、4……。    
面向对象的七大原则：

1. 开闭原则 , , , , , , , , , ,核心思想：对扩展开放，对修改关闭。
* 里氏替换原则 , , , , , , 核心：在任何父类出现的地方都可以用他的子类来替代。
* 依赖注入原则 , , , , , , 核心：要依赖于抽象，不要依赖于具体的实现。
* 接口分离原则 , , , , , , 核心思想：不应该强迫客户程序依赖他们不需要使用的方法。
* 组合/聚合复用原则 , , 核心思想：尽量使用对象组合，而不是继承来达到复用的目的。
* “迪米特”法则 , , , , , , ,核心：一个对象应当对其他对象有尽可能少的了解,不和陌生人说话。（类间解耦，低耦合）。
* 单一职责原则 , , , , , , 核心：解耦和增强内聚性（高内聚，低耦合）。

代码高亮
----------
在三个反引号后面加上编程语言的名字，另起一行开始写代码，最后一行再加上三个反引号。
```Java
public static void main(String[]args){} //Java
```
```c
int main(int argc, char *argv[]) //C
```
```javascript
document.getElementById("myH1").innerHTML="Welcome to my Homepage"; //javascipt
```
表格
--------

###对齐
表格可以指定对齐方式

| 左对齐 | 居中  | 右对齐 |
| :------------ |:---------------:| -----:|
| col 3 is      | some wordy text | $1600 |
| col 2 is      | centered        |   $12 |
| zebra stripes | are neat        |    $1 |

###混合其他语法
表格单元中的内容可以和其他大多数GFM语法配合使用，如：  

| 名字 | 描述          |
| ------------- | ----------- |
| Help      | ~~Display the~~ help window.|
| Close     | _Closes_ a window     |
####表格中嵌入图片（链接）
其实前面介绍图片显示、图片链接的时候为了清晰就是放在在表格中显示的。

| 图片 | 描述 |
| ---- | ---- |
|![baidu][baidu-logo] | 百度

###锚点
其实呢，每一个标题都是一个锚点，和HTML的锚点（`#`）类似。

|语法|效果|
|---|---
|`[回到顶部](#readme)`|[回到顶部](#readme)

不过要注意，标题中的英文字母都被转化为**小写字母**了。


--------------------------------
[csdn]:http://blog.csdn.net/qq_31733073 "我的博客"
[Github]:https://github.com/tyxo842 "我的github"
[weibo]:http://weibo.com
[baidu-logo]:http://www.baidu.com/img/bdlogo.gif "百度logo"
[weibo-logo]:https://github.com/tyxo842/MobileSafe/raw/master/MobileSafeApp/src/main/assets/weibo.png "点击图片进入微博"
[csdn-logo]:https://github.com/tyxo842/MobileSafe/raw/master/MobileSafeApp/src/main/assets/csdn.png "我的CSDN博客"
[pikaqiu]:https://github.com/tyxo842/MobileSafe/raw/master/MobileSafeApp/src/main/assets/pikaqiu.gif
