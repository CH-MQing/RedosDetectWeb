package cn.ac.ios.Bean;

import java.util.ArrayList;
//主要用作记录批量检测的结果
public class MyResultBean {
    private String regex;
    private String TestLanguage;//使用的语言环境
    private Integer id;//每个漏洞的编号
    private Integer regexID;//每个正则表达式的编号
    private boolean reDoS;//是否是ReDOS漏洞
    private String IsReDos="RESULT-FALSE";//是否是ReDOS漏洞
    private AttackType type = AttackType.POLYNOMIAL;//即漏洞的时间复杂度是指数还是多项式
    private int vul = 0;
    private String message;//是否超时？
    //时间
    private String SumTime;
    private String CheckTime;
    private String ValidateTime;
    private String AverageTime;

    //攻击字符串以及相关信息记录
    private ArrayList<MyAttackString> attackBeanList;//该漏洞对应的所有攻击字符串
    private ArrayList<MyAttackString> successBeanList;// 表示已经验证过的攻击串


    @Override
    public String toString() {
        return "MyResultBean{" +
                "regex='" + regex + '\'' +
                ", TestLanguage='" + TestLanguage + '\'' +
                ", id=" + id +
                ", regexID=" + regexID +
                ", reDoS=" + reDoS +
                ", IsReDos='" + IsReDos + '\'' +
                ", type=" + type +
                ", vul=" + vul +
                ", message='" + message + '\'' +
                ", SumTime='" + SumTime + '\'' +
                ", CheckTime='" + CheckTime + '\'' +
                ", ValidateTime='" + ValidateTime + '\'' +
                ", AverageTime='" + AverageTime + '\'' +
                ", attackBeanList=" + attackBeanList +
                ", successBeanList=" + successBeanList +
                '}';
    }

    //初始化函数
    public MyResultBean() {
        this.reDoS = false;
        IsReDos="RESULT-FALSE";
        type = AttackType.POLYNOMIAL;
        attackBeanList = new ArrayList<>();
        successBeanList = new ArrayList<>();
    }

    public MyResultBean(String regex, int id) {
        this.regex = regex;
        this.id = id;
        this.reDoS = false;
        this.attackBeanList = new ArrayList<>();
    }

    public MyResultBean(String regex, int id, int regexID, String message) {
        this.regex = regex;
        this.id = id;
        this.regexID = regexID;
        this.reDoS = false;
        attackBeanList = new ArrayList<>();
        this.message = message;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getTestLanguage() {
        return TestLanguage;
    }

    public void setTestLanguage(String testLanguage) {
        TestLanguage = testLanguage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRegexID() {
        return regexID;
    }

    public void setRegexID(Integer regexID) {
        this.regexID = regexID;
    }

    public boolean isReDoS() {
        return reDoS;
    }

    public void setReDoS(boolean reDoS) {
        this.reDoS = reDoS;
    }

    public String getIsReDos() {
        return IsReDos;
    }

    public void setIsReDos(String isReDos) {
        IsReDos = isReDos;
    }

    public AttackType getType() {
        return type;
    }

    public void setType(AttackType type) {
        this.type = type;
    }

    public int getVul() {
        return vul;
    }

    public void setVul(int vul) {
        this.vul = vul;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSumTime() {
        return SumTime;
    }

    public void setSumTime(String sumTime) {
        SumTime = sumTime;
    }

    public String getCheckTime() {
        return CheckTime;
    }

    public void setCheckTime(String checkTime) {
        CheckTime = checkTime;
    }

    public String getValidateTime() {
        return ValidateTime;
    }

    public void setValidateTime(String validateTime) {
        ValidateTime = validateTime;
    }

    public String getAverageTime() {
        return AverageTime;
    }

    public void setAverageTime(String averageTime) {
        AverageTime = averageTime;
    }

    public ArrayList<MyAttackString> getAttackBeanList() {
        return attackBeanList;
    }

    public void setAttackBeanList(ArrayList<MyAttackString> attackBeanList) {
        this.attackBeanList = attackBeanList;
    }

    public ArrayList<MyAttackString> getSuccessBeanList() {
        return successBeanList;
    }

    public void setSuccessBeanList(ArrayList<MyAttackString> successBeanList) {
        this.successBeanList = successBeanList;
    }


}
