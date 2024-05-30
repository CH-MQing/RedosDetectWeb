package cn.ac.ios.Bean;

/**
 * 对于java js语言正则表达式的调整
 * @author MQing
 */
public class RegexBean {

    private String regex;
    private String flags;

    public RegexBean(String regex, String flags) {
        this.regex = regex;
        this.flags = flags;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
    //获取FLAG值,FLAG就是 Pattern 类中的常量,代表不同的匹配模式
    public String getFlags() {
        if (flags.contains("s") && flags.contains("i")) {
            return "is";
        }
        if (flags.contains("s")) {
            return "s";
        }
        if (flags.contains("i")) {
            return "i";
        }
        return "";
    }

    public String getAllFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }
}
