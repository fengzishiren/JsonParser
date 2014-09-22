JsonParser
==========

使用Java实现的Json解析器


##JsonParser有哪些功能？

* 支持IEEE浮点 eg. 5280, 0.01234, 6.336E(+)4, 1.89E-4, 2E4
* 支持转义字符 \"
\\\
\/
\b
\f
\n
\r
\t
\u four-hex-digits）
* 支持Json数据与Java对象的序列和反序列化
* 遇到解析错误可精确定位到具体的某一行的某一列 这点甚至比许多商用的解释器、编译器更完善精确
* 性能没有做过具体测评 尚在开发阶段

####关于Unicode编码
使用Emoji书写的可以被正确解析的json示例：
<br>
{<br>
&#160; &#160; &#160; &#160;":octopus:": ":oden:",<br>
&#160; &#160; &#160; &#160;":zap:": ":octocat:",<br>
&#160; &#160; &#160; &#160;":spaghetti:":  [
        ":spaghetti:",
        ":cookie:",
        ":stew:",
        ":ice_cream:",
        ":icecream:",
        ":sushi:",
        ":curry:",
        ":custard:",
        ":dango:",
        ":pizza::ramen:",
        ":fried_shrimp:",
        ":fries:",
        ":chocolate_bar:",
        ":hamburger:"
    ]<br>
}

##后续的开发
JSON is YAML ......



##有问题反馈
在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流

* 邮件(xiaoyaozi106@163.com)
* 微博: [@小疯子诗人(http://weibo.com/fengzishiren/)


#参考
* json官网：http://www.json.org/
* yaml官网：http://www.yaml.org/
* 唐鳳（Audrey Tang）: http://cpansearch.perl.org/src/TODDR/YAML-Syck-1.28_01/
* Unicode标准：http://www.unicode.org/charts/PDF/Unicode-6.1/U61-1F300.pdf
