package cn.ac.ios;

import cn.ac.ios.Bean.*;
import cn.ac.ios.Patterns.SLQ.PatternSLQUtils;
import cn.ac.ios.TreeNode.TreeNode;
import cn.ac.ios.Patterns.EOA.PatternEOAUtils;
import cn.ac.ios.Patterns.EOD.PatternEODUtils;
import cn.ac.ios.Patterns.NQ.PatternNQUtils;
import cn.ac.ios.Patterns.POA.PatternPOAUtils;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.ac.ios.TreeNode.Utils.createReDoSTree;
import static cn.ac.ios.Utils.Constant.EXTENDED_COUNTING;
import static cn.ac.ios.Utils.FlagsUtils.*;

/**
 * @author pqc
 */
public class UniReDoSMain {

    public static String PYTHON = "python3";
    public static String JS = "node";

    public static void main(String[] args) throws IOException, InterruptedException {
        //输入
        String regex;
        Scanner scan=new Scanner(System.in);
        regex=scan.nextLine();
        //检测
//        ReDoSBean bean = validateReDoS(checkReDoS(regex, 1, "11111", "java"), "s", "java");
        //输出结果
        String result=RedosResult(regex, 1, "11111", "m", "java");
        System.out.println("RedosResult " + result);
    }
    /**
    * 输出结果
    */
    public static String RedosResult(String regex, Integer id, String options, String model,String language){
        //核心代码：动静态结合，动态检查静态分析的字符串
        ReDoSBean bean = validateReDoS(checkReDoS(regex, id, options, language), model, language);
        StringBuilder result=new StringBuilder();
        //测试的表达式
        result.append("Regex"+bean.getRegex()+"\n");
        //测试语言环境
        result.append("TestLanguage: " +bean.getTestLanguage()+"\n");
        //获得需要测试的攻击字符串的个数
        result.append("NeedTestNum: " +bean.getAttackBeanList().size()+"\n");
        //输出每个字符串的信息
        for (int i = 0; i < bean.getAttackBeanList().size(); i++) {//输出
            if (bean.getAttackBeanList().get(i).isAttackSuccess()) {
                //System.out.println("Test222");
                result.append("Is attack success: " + bean.getAttackBeanList().get(i).isAttackSuccess()+"\n");
                result.append("Attack time: " + bean.getAttackBeanList().get(i).getAttackTime() + " (ms)\n");
                result.append("Vulnerability Position: " + bean.getAttackBeanList().get(i).getLocateVulnerabilityRegex()+"\n");
                result.append("Attack String: " + bean.getAttackBeanList().get(i).getAttackStringFormat()+"\n");
                result.append("Vulnerability Source: " + bean.getAttackBeanList().get(i).getVulnerabilityRegexSource()+"\n");
                result.append("Vulnerability Degree: " + bean.getAttackBeanList().get(i).getType()+"\n");
                result.append("-------------------------------------------------\n ");
            }
        }
        //
        return result.toString();
    }
    /**
     * 获取正则的判断结果（攻击字符串）
     * 阶段一：静态检测
     *
     * @param regex
     * @param id
     * @return
     */
    public static ReDoSBean checkReDoS(String regex, Integer id, String options, String language) {
        //定义
        ReDoSBean bean = new ReDoSBean();
        bean.setRegex(regex);
        bean.setId(id);
        bean.setRegexID(id);
        bean.setTestLanguage(language);
        //做简单判断，筛选肯定不是redos的正则
        if (mustBeNoReDoS(regex)) {
            bean.setMessage("MUST_NOT_BE_REDOS");
            return bean;
        }
        //静态检测，判断需要检测的模式，需要检测的为1，不需要的检测为0
        if (options.charAt(0) == '1') {
            ReDoSBean bean1 = PatternNQUtils.getNQReDoSBean(regex, language);
            bean.getAttackBeanList().addAll(bean1.getAttackBeanList());
        }
        if (options.charAt(1) == '1') {
            ReDoSBean bean2 = PatternEODUtils.getEODReDoSBean(regex, language);
            bean.getAttackBeanList().addAll(bean2.getAttackBeanList());
        }
        if (options.charAt(2) == '1') {
            ReDoSBean bean3 = PatternEOAUtils.getEOAReDoSBean(regex, language);
            bean.getAttackBeanList().addAll(bean3.getAttackBeanList());
        }
        if (options.charAt(3) == '1') {
            ReDoSBean bean4 = PatternPOAUtils.getPOAReDoSBean(regex, language);
            bean.getAttackBeanList().addAll(bean4.getAttackBeanList());
        }
        if (options.charAt(4) == '1') {
            ReDoSBean bean5 = PatternSLQUtils.getSLQReDoSBean(regex, language);
            bean.getAttackBeanList().addAll(bean5.getAttackBeanList());
        }

        return bean;
    }

    /**
     * 获取正则的判断结果
     * 阶段一：检测
     *
     * @param regex
     * @param id
     * @return
     */
    public static ReDoSBean checkReDoS(String regex, Integer id) {
        return checkReDoS(regex, id, "11111", "java");
    }

    /**
     * 利用攻击串验证判断结果
     * 阶段二：验证
     *
     * @param bean
     * @return
     */
    public static ReDoSBean validateReDoS(ReDoSBean bean, String model, String language) {
        bean.setReDoS(false);
        //判断该正则表达式是否有攻击字符串
        if (bean.getAttackBeanList().isEmpty()) {
            return bean;
        }
        //对攻击字符串去重
        bean.duplicate();
        //调用对应语言的Regex引擎进行测试
        if ("js".equals(language)) {
            return getJS(bean, model);//调用js引擎去测试
        }else {
            return getJava(bean, model);//调用Java8引擎去测试
        }
    }

    /**
     * 使用java语言验证
     *
     * @param bean
     * @param model
     * @return
     */
    public static ReDoSBean getJava(ReDoSBean bean, String model) {
        //获取正则表达式的FLAG值
        RegexBean divideRegexByFlagsBean = divideRegexByFlags(bean.getRegex());
        String newRegex = divideRegexByFlagsBean.getRegex();
        String allFlags = divideRegexByFlagsBean.getAllFlags();
        //判断FLAG值，判断该表达式采用哪种匹配模式
        if (allFlags.contains("s")) {
            newRegex = "(?s)" + newRegex;//？s这种模式下, .可以匹配所有字符，包括换行符，默认 .匹配除换行符以外的任意字符
        }
        if (allFlags.contains("i")) {
            newRegex = "(?i)" + newRegex;//？i表示正则匹配的时候忽略大小写
        }
        if (allFlags.contains("m")) {
            newRegex = "(?m)" + newRegex;//？m即多行模式，对每一行（这行的第一个字符至每一行的结束处）都进行匹配。
        }
        if (allFlags.contains("x")) {
            newRegex = "(?x)" + newRegex;//？x表示空白和 #开始直到行末的注释会被忽略掉
        }
        bean.setRegex(newRegex);

        try {
            //将上述正则表达式编译成Pattern对象
            Pattern.compile(bean.getRegex());
        } catch (Exception e) {//若无法编译，则需要再进一步处理该表达式进行JAVA语言下的标准化
            try {//
                // 最开头的预处理
                String regex = bean.getRegex();
                // 建树
                TreeNode ReDoSTree = createReDoSTree(regex);
                // 删除注释
                ReDoSTree.deleteAnnotation();
                // 去group name
                ReDoSTree.deleteGroupName();
                // 针对数据集中出现的{?写法 需要在{前加\ 暂不知是否还有其他需要加斜杠的
                ReDoSTree.addBackslashBeforeSomeCharacters();
                // 将方括号中的\0~\777重写为\u0000~\u0777
                ReDoSTree.rewriteUnicodeNumberInBracketNode();
                // 将方括号中的\b删除 因为方括号中的\b表示退格符
                ReDoSTree.reWriteBackspace();
                // 转换[\w-.] -> [\w\-.] 而 [a-z]保留 为了regexlib
                ReDoSTree.rewriteIllegalBarSymbol();
                // 处理特殊斜杠字符 根据不同的语言
                ReDoSTree.rewriteSpecialBackslashCharacterForDifferentLanguage("java");

                regex = ReDoSTree.getData();
                bean.setRegex(regex);
            } catch (Exception exception) {//证明不是正则表达式
                bean.setReDoS(false);
                return bean;
            }
        }
        //动态验证攻击字符串
        bean.attack(model);
        return bean;
    }


    /**
     * 使用js语言验证
     *
     * @param bean
     * @param model
     * @return
     */
    public static ReDoSBean getJS(ReDoSBean bean, String model) {
        //提醒用户环境配置node，才能运行此
        System.out.println("waring:Your environment must support the command \"node\"");
        //
        List<String> list = new ArrayList<>();
        String regex = divideRegexByFlags(bean.getRegex()).getRegex();
        String flags = divideRegexByFlags(bean.getRegex()).getFlags();
        list.add(regex);
        list.add(flags);
        list.add(model);
        //获得攻击字符串
        for (int i = 0; i < bean.getAttackBeanList().size(); i++) {
            AttackBean attackBean = bean.getAttackBeanList().get(i);
            list.add(attackBean.getType().name());
            list.add(attackBean.getAttackStringFormatSp());
        }
        //中间文件（存储静态分析结果）
        String name = System.currentTimeMillis() + "js_attack.txt";
        try {
            FileUtils.writeLines(new File("js/" + name), list);//先将静态分析的结果使用一个中间文件存储
            //验证攻击字符串
            Process proc;
            String[] args = new String[]{JS, "js/attack.js", "js/" + name};
            proc = Runtime.getRuntime().exec(args);// 执行js文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
            ArrayList<String> results = (ArrayList<String>) FileUtils.readLines(new File("js/" + name.replace(".txt", "_result.txt")), "utf-8");
            //将结果转换为bean对象属性
            for (int i = 0; i < bean.getAttackBeanList().size(); i++) {
                String[] s = results.get(i).toLowerCase().split("IOS_AC_CN".toLowerCase());
                bean.getAttackBeanList().get(i).setAttackSuccess(Boolean.parseBoolean(s[0]));
                if (Boolean.parseBoolean(s[0])) {
                    bean.setReDoS(true);
                }
                bean.getAttackBeanList().get(i).setRepeatTimes(Integer.parseInt(s[1]));
                bean.getAttackBeanList().get(i).setAttackTime(Integer.parseInt(s[2]));
                bean.getAttackBeanList().get(i).confirmType();

            }
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //删除中间文件
            FileUtils.deleteQuietly(new File("js/" + name));
            FileUtils.deleteQuietly(new File("js/" + name.replace(".txt", "_result.txt")));
        }
        return bean;
    }



    /**
     * 筛选肯定不是redos的正则
     *
     * @param regex
     * @return
     */
    public static boolean mustBeNoReDoS(String regex) {
        Matcher matcher = Pattern.compile(EXTENDED_COUNTING).matcher(regex);
        return !matcher.find();
        //分为以下几个步骤：1、调用Pattern.compile函数，将正则表达式编译成Pattern对象 2、调用Pattern对象的matcher函数，创建一个Matcher对象 3、调用Matcher对象的find函数，进行匹配操作
    }
}
