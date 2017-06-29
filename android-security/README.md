





## N. proguard's trick
目前 baksmali 对 utf-8 字符的编码存在问题，因此使用中文作为字典可以略微提升二次打包的难度。
弊端在于会略微增加 apk 文件的体积。

```
[proguard-rules.pro]

#指定外部模糊字典
-obfuscationdictionary dictionary.txt

```

reference:

https://github.com/heruoxin/proguard-elder-dictionary
https://github.com/qbeenslee/gradle-proguard-dictionary
https://www.zhihu.com/question/41368839
