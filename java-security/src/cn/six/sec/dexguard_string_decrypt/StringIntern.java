package cn.six.sec.dexguard_string_decrypt;

/**
 * Created by songzhw on 2016/02/08.
 * see : http://www.cnblogs.com/wanlipeng/archive/2010/10/21/1857513.html
 */
public class StringIntern {
    public static void main(String[] args) {
        String str1 = "a";
        String str2 = "b";
        String str3 = "ab";
        String str4 = str1 + str2;
        String str5 = new String("ab");

        System.out.println(str5.equals(str3));   //=> true  .  because the string content is same.
        System.out.println(str5 == str3);        //=> false .  because there are two objects.
        System.out.println(str5.intern() == str3);   //=> true.  当str5调用intern的时候，会检查字符串池中是否含有该字符串。由于之前定义的str3已经进入字符串池中，所以会得到相同的引用。
        System.out.println(str5.intern() == str4);   //=> false.
    }
}
