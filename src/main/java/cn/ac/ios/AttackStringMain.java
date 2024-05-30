package cn.ac.ios;

import cn.ac.ios.Bean.ReDoSBean;
import cn.ac.ios.Utils.multithread.ITask;
import cn.ac.ios.Utils.multithread.MultiBaseBean;
import cn.ac.ios.Utils.multithread.MultiThreadUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 生成攻击字符串的主程序入口
 *
 * @author pqc
 */
public class AttackStringMain {

    public static final String ATTACK_MODEL_SINGLE = "s";
    public static final String ATTACK_MODEL_MULTI = "m";
    public static final String DEFAULT_LANGUAGE = "java";
    public static final String HELP_FLAG = "-h";
    public static final String TIMEOUT_SETTING = "-t";
    public static final String ATTACK_MODEL = "-a";
    public static final String LANGUAGE = "-l";
    /* default settings */
    public static final int DEFAULT_TIMEOUT = 60;
    public static HashMap<String, String> commandLineSettings;


    /**
     * TODO
     *
     * @param args
     */
    public static void main(String[] args) {
        Logger.getGlobal().setLevel(Level.OFF);
        commandLineSettings = new HashMap<>();
        for (String arg : args) {
            if (arg.contains(HELP_FLAG)) {
                System.out.println(printUsage());
                System.exit(0);
            }
            if (arg.startsWith("-")) {
                if (arg.contains("=")) {
                    int settingLastIndex = arg.indexOf("=");
                    String settingName = arg.substring(0, settingLastIndex);
                    String settingValue = arg.substring(settingLastIndex + 1);
                    commandLineSettings.put(settingName, settingValue);
                }
            }
        }


        String version = System.getProperty("java.version");
        if (!(version.startsWith("1.8") || version.startsWith("1.7") || version.startsWith("1.6"))) {
            System.out.println("Warning: The current Java version(" + version + ") is not support,please use Java(8)");
            System.exit(0);
        }


        System.out.println("please input regex ");
        Scanner scanner = new Scanner(System.in);
        String regex = scanner.nextLine();
        System.out.println("input:" + regex);
        System.out.println("output: \n" + getResult(regex));

    }


    public static String getResult(String regex) {
        int timeout = Integer.parseInt(commandLineSettings.getOrDefault(TIMEOUT_SETTING, String.valueOf(DEFAULT_TIMEOUT)));
        String model = commandLineSettings.getOrDefault(ATTACK_MODEL, ATTACK_MODEL_MULTI);
        String language = commandLineSettings.getOrDefault(LANGUAGE, DEFAULT_LANGUAGE);
        ArrayList<String> tasksData = new ArrayList<>();
        tasksData.add(regex);
        MultiThreadUtils<String, ReDoSBean> threadUtils = MultiThreadUtils.newInstance(1, timeout);
        MultiBaseBean<List<ReDoSBean>> multiBaseBean;
        if (tasksData == null || tasksData.isEmpty()) {
            multiBaseBean = new MultiBaseBean<>(null);
        } else {
            multiBaseBean = threadUtils.execute(tasksData, null, new ITask<String, ReDoSBean>() {
                @Override
                public ReDoSBean execute(String regex, Map<String, Integer> params) {
                    return (UniReDoSMain.checkReDoS(regex, params.get("id")));
                }
            });
        }
        MultiThreadUtils<ReDoSBean, ReDoSBean> threadValidateUtils = MultiThreadUtils.newInstance(1, 0);
        MultiBaseBean<List<ReDoSBean>> validateBeans;
        validateBeans = threadValidateUtils.execute(multiBaseBean.getData(), null, new ITask<ReDoSBean, ReDoSBean>() {
            @Override
            public ReDoSBean execute(ReDoSBean bean, Map<String, Integer> params) {
                return (UniReDoSMain.validateReDoS(bean, model, "java"));
            }
        });
        //生成结果，返回给前端
       StringBuilder result = new StringBuilder();

        for (ReDoSBean bean : validateBeans.getData()) {
            if (bean.isReDoS()) {
                //result.append("Vulnerable\n");
                for (int i = 0; i < bean.getAttackBeanList().size(); i++) {
                    if (bean.getAttackBeanList().get(i).isAttackSuccess()) {
                        result.append("Attack String :" + bean.getAttackBeanList().get(i).getAttackStringFormat() + "\n");
                    }
                }
            } else {
                result.append("Safe\n");
            }
        }
        return result.toString();
//        System.exit(0); // 新增
    }


    public static String printUsage() {
        return ("usage: type the command \"java -jar lcp.jar\", Press enter\n       then type your regex\n       (optional)-t:  set the timeout to d seconds for check phase. If d <= 0, timeout is disabled. default is 60s;\n       (optional)-a:  attack model: s (for vulnerable only), M (for validating all attack strings). default is s;");
//        System.out.println("\t (optional)-l:  programming language environment which used for regex，default in Java");
    }
}
