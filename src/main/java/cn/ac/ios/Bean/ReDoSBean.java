package cn.ac.ios.Bean;

import cn.ac.ios.TreeNode.TreeNode;
import cn.ac.ios.Utils.timeout.TimeoutTask;
import cn.ac.ios.Utils.timeout.TimeoutTaskUtils;

import java.util.ArrayList;

import static cn.ac.ios.AttackStringMain.ATTACK_MODEL_SINGLE;
import static cn.ac.ios.Bean.AttackBean.*;
import static cn.ac.ios.TreeNode.Utils.createReDoSTree;

/**
 * @author pqc
 */
public class ReDoSBean {

    private String regex;
    private String TestLanguage;//使用的语言环境
    private Integer id;//每个漏洞的编号
    private Integer regexID;//每个正则表达式的编号
    private boolean reDoS;//是否是ReDOS漏洞
    private ArrayList<AttackBean> attackBeanList;//该漏洞对应的攻击字符串
    // 表示已经验证过的攻击串
    private ArrayList<AttackBean> successBeanList = new ArrayList<>();
    private String message;//漏洞位置描述？
    private AttackType type = AttackType.POLYNOMIAL;//即漏洞的时间复杂度是指数还是多项式
    private int vul = 0;//
    private TreeNode root;

    public ArrayList<AttackBean> getSuccessBeanList() {
        return successBeanList;
    }

    public void setSuccessBeanList(ArrayList<AttackBean> successBeanList) {
        this.successBeanList = successBeanList;
    }

    public void setType(AttackType type) {
        this.type = type;
    }

    public void setVul(int vul) {
        this.vul = vul;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    @Override
    public String toString() {




        return "ReDoSBean{" +
                "regex='" + regex + '\'' +
                ", TestLanguage='" + TestLanguage + '\'' +
                ", id=" + id +
                ", regexID=" + regexID +
                ", reDoS=" + reDoS +
                ", attackBeanList=" + attackBeanList +
                ", successBeanList=" + successBeanList +
                ", message='" + message + '\'' +
                ", type=" + type +
                '}';
    }

    public ReDoSBean() {
        this.reDoS = false;
        attackBeanList = new ArrayList<>();
    }

    public ReDoSBean(String regex, int id) {
        this.regex = regex;
        this.id = id;
        this.reDoS = false;
        this.attackBeanList = new ArrayList<>();
    }

    public ReDoSBean(String regex, int id, int regexID, String message) {
        this.regex = regex;
        this.id = id;
        this.regexID = regexID;
        this.reDoS = false;
        attackBeanList = new ArrayList<>();
        this.message = message;
    }


    public boolean isReDoS() {
//        return reDoS;
        // 只靠isReDoS不大行
        // 例如 ^(?=^.{1,254}$)(^(?:(?!\.|-)([a-z0-9\-\*]{1,63}|([a-z0-9\-]{1,62}[a-z0-9]))\.)+(?:[a-z]{2,})$)$
        // AttackString:""+"a0a."*2+" "
        // 是成功的 通过bean.getAttackBeanList().get(i).isAttackSuccess()是可以获取到成功的
        // 因此要加上这个判断
        if (reDoS) {
            return true;
        } else {
            for (AttackBean attackBean : attackBeanList) {
                if (attackBean.isAttackSuccess()) {
                    return true;
                }
            }
            return false;
        }
    }


    //bean属性初始化函数
    //是否是ReDos漏洞
    public void setReDoS(boolean reDoS) {
        this.reDoS = reDoS;
    }
    //返回当前测试的Regex
    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
    //测试语言
    public String getTestLanguage(){return this.TestLanguage;}

    public void setTestLanguage(String language) {
        this.TestLanguage = language;
    }
    //获得攻击
    public ArrayList<AttackBean> getAttackBeanList() {
        return attackBeanList;
    }

    public void setAttackBeanList(ArrayList<AttackBean> attackBeanList) {
        this.attackBeanList = attackBeanList;
    }

    /**
     * @param model 攻击模式 s 表示攻击成功则退出，m 表示会测试完所有的攻击串
     */
    public void attack(String model) {
        reDoS = false;
        for (int i = 0; i < attackBeanList.size(); ) {
            //如果正在执行TimeoutTask，中断异常会被TimeoutTask捕获，此处不再捕获
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            //获取攻击字符串
            AttackBean attackBean = attackBeanList.get(i);
            if (successBeanList.contains(attackBean)) {
                for (AttackBean bean : successBeanList) {
                    if (bean.equals(attackBean)) {
                        attackBean.setAttackSuccess(bean.isAttackSuccess());
                        attackBean.setRepeatTimes(bean.getRepeatTimes());
                        attackBean.setAttackTime(bean.getAttackTime());
                        attackBean.confirmType();
                        break;
                    }
                }
                successBeanList.add(attackBean);
            } else {
                Pair<Boolean, Integer> pair = TimeoutTaskUtils.execute(new TimeoutTask(attackBean, regex));
                Boolean timeout = pair.getKey();
                int time = pair.getValue();
                if (time == STACK_ERROR) {
                    if (attackBean.getType() != AttackType.STACK_ERROR) {
                        attackBean.initType(AttackType.STACK_ERROR);
                        attackBean.increase();
                        if (!attackBean.isTerminal()) {
                            continue;
                        }
                    }
                }
                if (time == REPEAT_INCREASE) {
                    attackBean.increase();
                    if (!attackBean.isTerminal()) {
                        continue;
                    }
                }
                attackBean.setAttackSuccess(timeout);
                attackBean.setAttackTime(time);
                if (timeout) {
                    type = attackBean.confirmType();
                    if (time >= TIME_OUT) {
                        reDoS = secondaryValidation(attackBean, regex);
                        attackBean.reset();
                    } else {
                        reDoS = true;
                    }
                    if (reDoS) {
//                        locateVenture(attackBean);
                        attackBean.locateVenture();//给出该漏洞的原因和位置
                        if (model.equals(ATTACK_MODEL_SINGLE)) {//判断是否式S模式，是则退出循环
                            break;
                        }
                    }
                }
                successBeanList.add(attackBean);
                //正则错误
                if (time == REGEX_ERROR || time == INTERRUPTED) {
                    break;
                }
            }
            i++;
        }
    }



    /**
     * 二次验证 防止误判
     *
     * @param attackBean
     * @param regex
     * @return
     */
    private boolean secondaryValidation(AttackBean attackBean, String regex) {
        attackBean.secondaryValidation();
        Pair<Boolean, Integer> pair = TimeoutTaskUtils.execute(new TimeoutTask(attackBean, regex));
        // 二次验证可能栈溢出, 直接返回true
        if (pair.getValue() == STACK_ERROR) {
            return true;
        }
        return pair.getKey();
    }

    /**
     * 对攻击串去重
     */
    public void duplicate() {
        ArrayList<AttackBean> list = new ArrayList<>();
        for (AttackBean attackBean : attackBeanList) {
            if (!list.contains(attackBean)) {
                list.add(attackBean);
            }
        }
        attackBeanList = list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AttackType getType() {
        return type;
    }

    public int getVul() {
        for (int i = 0; i < attackBeanList.size(); i++) {
            if (attackBeanList.get(i).isAttackSuccess()) {
                vul++;
            }
        }
        return vul;
    }

    public Integer getRegexID() {
        return regexID;
    }

    public void setRegexID(Integer regexID) {
        this.regexID = regexID;
    }
}
