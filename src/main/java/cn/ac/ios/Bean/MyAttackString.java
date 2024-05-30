package cn.ac.ios.Bean;

public class MyAttackString {
    //对应的regex
    private String regex;
    private String TestLanguage;//使用的语言环境
    //攻击时间
    private long attackTime = 0;
    //是否攻击成功
    private boolean attackSuccess;
    private String IsattackSuccess;
    //漏洞位置描述
    private String VulPosition;
    private String VulSource;
    //攻击模式
    private PatternType patternType;
    //时间复杂度
    private AttackType type = AttackType.POLYNOMIAL;//即漏洞的时间复杂度是指数还是多项式
    //所采用的攻击字符串
    private String AttackString;

    @Override
    public String toString() {
        return "MyAttackString{" +
                "attackSuccess=" + attackSuccess +
                ", IsattackSuccess='" + IsattackSuccess + '\'' +
                ", VulPosition='" + VulPosition + '\'' +
                ", VulSource='" + VulSource + '\'' +
                ", patternType=" + patternType +
                ", type=" + type +
                ", AttackString='" + AttackString + '\'' +
                '}';
    }

    //初始化
    public MyAttackString() {
        this.attackSuccess= false;
    }
    public String getVulPosition() {
        return VulPosition;
    }

    public void setVulPosition(String vulPosition) {
        VulPosition = vulPosition;
    }

    public String getVulSource() {
        return VulSource;
    }

    public void setVulSource(String vulSource) {
        VulSource = vulSource;
    }

    public PatternType getPatternType() {
        return patternType;
    }

    public void setPatternType(PatternType patternType) {
        this.patternType = patternType;
    }
    public boolean isAttackSuccess() {
        return attackSuccess;
    }

    public AttackType getType() {
        return type;
    }

    public void setType(AttackType type) {
        this.type = type;
    }

    public void setAttackSuccess(boolean attackSuccess) {
        this.attackSuccess = attackSuccess;
    }
    public String getAttackString() {
        return AttackString;
    }

    public void setAttackString(String attackString) {
        AttackString = attackString;
    }
    public String getIsattackSuccess() {
        return IsattackSuccess;
    }

    public void setIsattackSuccess(String isattackSuccess) {
        IsattackSuccess = isattackSuccess;
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

    public long getAttackTime() {
        return attackTime;
    }

    public void setAttackTime(long attackTime) {
        this.attackTime = attackTime;
    }
}
