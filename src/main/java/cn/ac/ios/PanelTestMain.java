package cn.ac.ios;

import cn.ac.ios.Bean.*;
import cn.ac.ios.Utils.multithread.ITask;
import cn.ac.ios.Utils.multithread.MultiBaseBean;
import cn.ac.ios.Utils.multithread.MultiThreadUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.ac.ios.AttackStringMain.ATTACK_MODEL_MULTI;
import static cn.ac.ios.AttackStringMain.ATTACK_MODEL_SINGLE;
import static cn.ac.ios.Utils.Utils.readFile;
import static java.lang.Boolean.FALSE;

/**
 * @author pqc
 */
public class PanelTestMain {
    public static void main(String[] args) throws IOException {
        String filePath = "data/paper_dataset";
        String fileName = "test2.txt";
//        run(filePath + "/" + fileName, fileName);
//        List<MyResultBean>res=PostPanel(filePath + "/" + fileName, fileName, "s", "java", "11111", 0, 15, 1, 60);
        List<String> tasksData = new ArrayList<>();
        String str="a+a+b";
        tasksData.add(str);
        List<MyResultBean>res=PostPanelTest(tasksData,"m", "java");
        System.out.println(res.get(1).getAttackBeanList().size());
        System.out.println(res.get(1).getSuccessBeanList());
        System.out.println(res.get(1).getSuccessBeanList().get(0).getAttackTime());
    }


//    private static void run(String s, String fileName) {
//        run(s, fileName, "s", "java", "11111", 0, 15, 1, 60);
//    }
//    //输出文件
//    public static void run(String sourceFile, String outfileName, String model, String language, String patternModel, int funcType, int checkThreadCount, int validateThreadCount, int timeout) {
//        List<String> tasksData = readFile(sourceFile);
//        long startTime = System.currentTimeMillis();
//        MultiThreadUtils<String, ReDoSBean> threadUtils = MultiThreadUtils.newInstance(checkThreadCount, timeout);
//        MultiBaseBean<List<ReDoSBean>> multiBaseBean;
//        if (tasksData == null || tasksData.isEmpty()) {
//            multiBaseBean = new MultiBaseBean<>(null);
//        } else {
//            multiBaseBean = threadUtils.execute(tasksData, null, new ITask<String, ReDoSBean>() {
//                @Override
//                public ReDoSBean execute(String regex, Map<String, Integer> params) {
//                    return (UniReDoSMain.checkReDoS(regex, params.get("id"), patternModel, language));
//                }
//            });
//        }
//
//        long checkEndTime = System.currentTimeMillis();
//
//        MultiThreadUtils<ReDoSBean, ReDoSBean> threadValidateUtils = MultiThreadUtils.newInstance(validateThreadCount, timeout);
//        MultiBaseBean<List<ReDoSBean>> validateBeans;
//        validateBeans = threadValidateUtils.execute(multiBaseBean.getData(), null, new ITask<ReDoSBean, ReDoSBean>() {
//            @Override
//            public ReDoSBean execute(ReDoSBean bean, Map<String, Integer> params) {
//                return (UniReDoSMain.validateReDoS(bean, model, language));
//            }
//        });
//
//        long validateEndTime = System.currentTimeMillis();
//
//        List<ReDoSBean> resultBeans = new ArrayList<>();
//        for (int i = 0; i < tasksData.size(); i++) {
//            resultBeans.add(new ReDoSBean(tasksData.get(i), i + 1, i + 1, "TIME-OUT"));
//        }
//        for (ReDoSBean bean : validateBeans.getData()) {
//            resultBeans.set(bean.getId() - 1, bean);
//        }
//        ArrayList<String> list = new ArrayList<>();
//        ArrayList<String> list2 = new ArrayList<>();
//
//        list2.add("real sum time = " + (validateEndTime - startTime) / 1000 + "(s)");
//        list2.add("real check time = " + (checkEndTime - startTime) / 1000 + "(s)");
//        list2.add("real validate time = " + (validateEndTime - checkEndTime) / 1000 + "(s)");
//        list2.add("real average time = " + (validateEndTime - startTime) / 1000 / (double) tasksData.size() + "(s)");
//
//        for (ReDoSBean bean : resultBeans) {
//            list.add("id:" + String.valueOf(bean.getRegexID()));
//            list.add(bean.getRegex());
//            if (bean.isReDoS()) {
//                list2.add("id:" + String.valueOf(bean.getRegexID()));
//                list2.add(bean.getRegex());
//                list2.add("RESULT-TRUE");
//                list2.add(bean.getType().name());
//                list2.add("nums:" + String.valueOf(bean.getVul()));
//                list.add("RESULT-TRUE");
//                boolean flag = true;
//                for (int i = 0; i < bean.getAttackBeanList().size(); i++) {
//                    if (bean.getAttackBeanList().get(i).isAttackSuccess()) {
//                        list.add("success TYPE: " + bean.getAttackBeanList().get(i).getType() + "\t AttackString：" + bean.getAttackBeanList().get(i).getAttackStringFormat());
//                        list.add("vulnerability Position: " + bean.getAttackBeanList().get(i).getLocateVulnerabilityRegex());
//                        list.add("vulnerability Source: " + bean.getAttackBeanList().get(i).getVulnerabilityRegexSource());
//                        if ((model.equals(ATTACK_MODEL_SINGLE) && flag) || model.equals(ATTACK_MODEL_MULTI)) {  // 修改
//                            list2.add(bean.getAttackBeanList().get(i).getAttackStringFormatType());
//                            list2.add("patternType: " + bean.getAttackBeanList().get(i).getPatternType());
//                            list2.add("vulnerability Position: " + bean.getAttackBeanList().get(i).getLocateVulnerabilityRegex());
//                            list2.add("vulnerability Source: " + bean.getAttackBeanList().get(i).getVulnerabilityRegexSource());
//                            flag = false;
//                        }
//                    } else {
//                        list.add("failed TYPE: " + bean.getAttackBeanList().get(i).getType() + "\t AttackString：" + bean.getAttackBeanList().get(i).getAttackStringFormat());
////                        list2.add(bean.getAttackBeanList().get(i).getAttackStringFormatType());
//                        list.add("patternType: " + bean.getAttackBeanList().get(i).getPatternType());
//                    }
//                }
//                list2.add("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
////                generateJavaFile(outfileName.replace(".txt", ""), bean.getRegex(), bean, "match");
////                generateJavaFile(outfileName.replace(".txt", ""), bean.getRegex(), bean, "find");
////                generateJavaScriptFile(outfileName.replace(".txt", ""), bean.getRegex(), bean, "exec");
////                generateJavaScriptFile(outfileName.replace(".txt", ""), bean.getRegex(), bean, "match");
////                generateJavaScriptFile(outfileName.replace(".txt", ""), bean.getRegex(), bean, "search");
////                generateJavaScriptFile(outfileName.replace(".txt", ""), bean.getRegex(), bean, "test");
////                generatePythonREFile(outfileName.replace(".txt", ""), bean.getRegex(), bean, "match");
////                generatePythonREFile(outfileName.replace(".txt", ""), bean.getRegex(), bean, "search");
////                generatePythonRE2File(outfileName.replace(".txt", ""), bean.getRegex(), bean, "match");
////                generatePythonRE2File(outfileName.replace(".txt", ""), bean.getRegex(), bean, "search");
//            } else {
//                list.add("RESULT-FALSE");
//                list.add(bean.getMessage());
//                for (int i = 0; i < bean.getAttackBeanList().size(); i++) {
//                    if (bean.getAttackBeanList().get(i).isAttackSuccess()) {
//                        list.add("success TYPE: " + bean.getAttackBeanList().get(i).getType() + "\t AttackString：" + bean.getAttackBeanList().get(i).getAttackStringFormat() + "\t patternType: " + bean.getAttackBeanList().get(i).getPatternType());
//                    } else {
//                        list.add("failed TYPE:" + bean.getAttackBeanList().get(i).getType() + "\t AttackString：" + bean.getAttackBeanList().get(i).getAttackStringFormat() + "\t patternType: " + bean.getAttackBeanList().get(i).getPatternType());
//
//                    }
//                }
//            }
//            list.add("-------------------------");
//        }
//        try {
//            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
//            DateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
//            String tsStr = sdf.format(timeStamp);
//            tsStr = model + "_" + language + "_" + patternModel + "_" + funcType + "_" + tsStr;
//            FileUtils.writeLines(new File("data/expr/" + outfileName.replace(".txt", "") + "_redos_" + tsStr + ".txt"), list);
//            FileUtils.writeLines(new File("data/expr/" + outfileName.replace(".txt", "") + "_only_redos_true_" + tsStr + ".txt"), list2);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.exit(0); // 新增
//    }
//
//    public static List<MyResultBean> PostPanel(String sourceFile, String outfileName, String model, String language, String patternModel, int funcType, int checkThreadCount, int validateThreadCount, int timeout){
//    //初始化结果对象
//        MyResultBean ResultMsg=new MyResultBean();
//
//        List<MyResultBean> ResultArr= new ArrayList<>();//记录最终结果,i=0是存储ResultTimeMsg，之后是存储单个Result
//        //根据路径读取上传文件
//        List<String> tasksData = readFile(sourceFile);
//        System.out.println(tasksData);
//        //记录开始时间的当前时间戳
//        long startTime = System.currentTimeMillis();
//        //分线程检测
//        MultiThreadUtils<String, ReDoSBean> threadUtils = MultiThreadUtils.newInstance(checkThreadCount, timeout);
//        MultiBaseBean<List<ReDoSBean>> multiBaseBean;
//        if (tasksData == null || tasksData.isEmpty()) {//判断上传的文件是否为空
//            multiBaseBean = new MultiBaseBean<>(null);
//        } else {
//            multiBaseBean = threadUtils.execute(tasksData, null, new ITask<String, ReDoSBean>() {
//                @Override
//                public ReDoSBean execute(String regex, Map<String, Integer> params) {
//                    return (UniReDoSMain.checkReDoS(regex, params.get("id"), patternModel, language));//调用checkRedos
//                }
//            });
//        }
//        //记录检测结束时间的当前时间戳
//        long checkEndTime = System.currentTimeMillis();
//        //分线程验证
//        MultiThreadUtils<ReDoSBean, ReDoSBean> threadValidateUtils = MultiThreadUtils.newInstance(validateThreadCount, timeout);
//        MultiBaseBean<List<ReDoSBean>> validateBeans;
//        validateBeans = threadValidateUtils.execute(multiBaseBean.getData(), null, new ITask<ReDoSBean, ReDoSBean>() {
//            @Override
//            public ReDoSBean execute(ReDoSBean bean, Map<String, Integer> params) {
//                return (UniReDoSMain.validateReDoS(bean, model, language));
//            }
//        });
//        //记录验证结束时间的当前时间戳
//        long validateEndTime = System.currentTimeMillis();
//        //初始化静态动态检测结果对象，作为中间结果
//        List<ReDoSBean> resultBeans = new ArrayList<>();
//        for (int i = 0; i < tasksData.size(); i++) {
//            resultBeans.add(new ReDoSBean(tasksData.get(i), i + 1, i + 1, "TIME-OUT"));
//        }
//        for (ReDoSBean bean : validateBeans.getData()) {
//            resultBeans.set(bean.getId() - 1, bean);
//        }
//        //
////        ArrayList<String> list = new ArrayList<>();//记录所有正则表达式的检测结果
////        ArrayList<String> list2 = new ArrayList<>();//只记录包含ReDos的检测结果
//
//        //设置执行检测和验证时间信息
//        ResultMsg.setSumTime((validateEndTime - startTime) / 1000 + "(s)");
//        ResultMsg.setCheckTime((checkEndTime - startTime) / 1000 + "(s)");
//        ResultMsg.setValidateTime((validateEndTime - checkEndTime) / 1000 + "(s)");
//        ResultMsg.setAverageTime((validateEndTime - startTime) / 1000 / (double) tasksData.size() + "(s)");
//        ResultMsg.setTestLanguage(language);
//        ResultArr.add(ResultMsg);
//        //记录结果
//        for (ReDoSBean bean : resultBeans) {
//            MyResultBean result=new MyResultBean();//中间结果
//            result.setId(bean.getRegexID());
//            result.setRegex(bean.getRegex());
//            //判断是否是漏洞的regex
//
//            if (bean.isReDoS()) {//是
//                result.setIsReDos("RESULT-TRUE");
//                result.setType(bean.getType());
//                result.setVul(bean.getVul());
//                //
////                list.add("RESULT-TRUE");
//                //记录结果，List包含所有信息，Lsist2只包含包含漏洞的所有信息（只包含成功攻击的字符串）
//                //分别记录单个bean下的所有测试的攻击字符串和成功攻击的字符串
//                boolean flag = true;
//                for (int i = 0; i < bean.getAttackBeanList().size(); i++) {
//                    MyAttackString myAttackString = new MyAttackString();
//                    MyAttackString succuessString = new MyAttackString();
//                    AttackBean currentBean = bean.getAttackBeanList().get(i);
//                    myAttackString.setType(currentBean.getType());
//                    myAttackString.setVulPosition(currentBean.getLocateVulnerabilityRegex());
//                    myAttackString.setVulSource(currentBean.getVulnerabilityRegexSource());
//
//
//
//                    //记录所有测试的攻击字符串
//                    myAttackString.setAttackSuccess(currentBean.isAttackSuccess());//是否攻击成功
//                    myAttackString.setVulPosition(bean.getAttackBeanList().get(i).getLocateVulnerabilityRegex());//漏洞位置
//                    myAttackString.setType(bean.getAttackBeanList().get(i).getType());//漏洞时间复杂度
//                    myAttackString.setAttackString(bean.getAttackBeanList().get(i).getAttackStringFormat());//成功的攻击字符串
//                    myAttackString.setVulSource(bean.getAttackBeanList().get(i).getVulnerabilityRegexSource());//漏洞原因
//
//                    if (bean.getAttackBeanList().get(i).isAttackSuccess()) {
//                        myAttackString.setIsattackSuccess("ATTACK_SUCCESS");
//                        if ((model.equals(ATTACK_MODEL_SINGLE) && flag) || model.equals(ATTACK_MODEL_MULTI)) {//判断是m还是s模式，m模式下成功的字符串不止一个，有多个，s模式只有一个
//                            // 需要判断使用的是m还是s
////                            list2.add(bean.getAttackBeanList().get(i).getAttackStringFormatType());//记录攻击字符串
////                            list2.add("patternType: " + bean.getAttackBeanList().get(i).getPatternType());
////                            list2.add("vulnerability Position: " + bean.getAttackBeanList().get(i).getLocateVulnerabilityRegex());
////                            list2.add("vulnerability Source: " + bean.getAttackBeanList().get(i).getVulnerabilityRegexSource());
//                            succuessString.setAttackSuccess(bean.getAttackBeanList().get(i).isAttackSuccess());//是否攻击成功
//                            succuessString.setIsattackSuccess("ATTACK_SUCCESS");//是否攻击成功
//                            succuessString.setVulPosition(bean.getAttackBeanList().get(i).getLocateVulnerabilityRegex());//漏洞位置
//                            succuessString.setType(bean.getAttackBeanList().get(i).getType());//漏洞时间复杂度
//                            succuessString.setAttackString(bean.getAttackBeanList().get(i).getAttackStringFormat());//成功的攻击字符串
//                            succuessString.setVulSource(bean.getAttackBeanList().get(i).getVulnerabilityRegexSource());//
//                            flag = false;
//                            result.getSuccessBeanList().add(succuessString);
//                        }
//
//                    } else {
//                        myAttackString.setIsattackSuccess("ATTACK_FAILED");
//                    }
//                    result.getAttackBeanList().add(myAttackString);
//
//                }
//            } else {//不是
//
//                result.setIsReDos("RESULT-FALSE");
//                result.setMessage(bean.getMessage());
//                result.setReDoS(FALSE);
//
//                for (int i = 0; i < bean.getAttackBeanList().size(); i++) {
//                    MyAttackString myAttackString = new MyAttackString();
//                    MyAttackString succuessString = new MyAttackString();
//                    //记录所有测试的攻击字符串
//                    myAttackString.setAttackSuccess(bean.getAttackBeanList().get(i).isAttackSuccess());//是否攻击成功
//                    myAttackString.setVulPosition(bean.getAttackBeanList().get(i).getLocateVulnerabilityRegex());//漏洞位置
//                    myAttackString.setType(bean.getAttackBeanList().get(i).getType());//漏洞时间复杂度
//                    myAttackString.setAttackString(bean.getAttackBeanList().get(i).getAttackStringFormat());//成功的攻击字符串
//                    myAttackString.setVulSource(bean.getAttackBeanList().get(i).getVulnerabilityRegexSource());
//
//                    if (bean.getAttackBeanList().get(i).isAttackSuccess()) {
//                        myAttackString.setIsattackSuccess("ATTACK_SUCCESS");//是否攻击成功
//
//                    } else {
//                        myAttackString.setIsattackSuccess("ATTACK_FAILED");//是否攻击成功
//                    }
//                    result.getAttackBeanList().add(myAttackString);
//                }
//            }
//            //加入该对象
//            ResultArr.add(result);
//        }
//        System.out.println(ResultArr);
//        return ResultArr;
//    }

    public static List<MyResultBean> PostPanelTest(List<String> tasksData, String model, String language){
        return PostPanelTest(tasksData,model,language,"11111",  15, 1, 60);
    }

    public static List<MyResultBean> PostPanelTest(List<String> tasksData, String model, String language, String patternModel, int checkThreadCount, int validateThreadCount, int timeout) {
        //初始化结果对象
        MyResultBean ResultMsg=new MyResultBean();

        List<MyResultBean> ResultArr= new ArrayList<>();//记录最终结果,i=0是存储ResultTimeMsg，之后是存储单个Result
//        根据路径读取上传文件
//        List<String> tasksData = readFile(sourceFile);
        //记录开始时间的当前时间戳
        long startTime = System.currentTimeMillis();
        //分线程检测
        MultiThreadUtils<String, ReDoSBean> threadUtils = MultiThreadUtils.newInstance(checkThreadCount, timeout);
        MultiBaseBean<List<ReDoSBean>> multiBaseBean;
        if (tasksData == null || tasksData.isEmpty()) {//判断上传的文件是否为空
            multiBaseBean = new MultiBaseBean<>(null);
        } else {
            multiBaseBean = threadUtils.execute(tasksData, null, new ITask<String, ReDoSBean>() {
                @Override
                public ReDoSBean execute(String regex, Map<String, Integer> params) {
                    return (UniReDoSMain.checkReDoS(regex, params.get("id"), patternModel, language));//调用checkRedos
                }
            });
        }
        //记录检测结束时间的当前时间戳
        long checkEndTime = System.currentTimeMillis();
        //分线程验证
        MultiThreadUtils<ReDoSBean, ReDoSBean> threadValidateUtils = MultiThreadUtils.newInstance(validateThreadCount, timeout);
        MultiBaseBean<List<ReDoSBean>> validateBeans;
        validateBeans = threadValidateUtils.execute(multiBaseBean.getData(), null, new ITask<ReDoSBean, ReDoSBean>() {
            @Override
            public ReDoSBean execute(ReDoSBean bean, Map<String, Integer> params) {
                return (UniReDoSMain.validateReDoS(bean, model, language));
            }
        });
        //记录验证结束时间的当前时间戳
        long validateEndTime = System.currentTimeMillis();
        //初始化静态动态检测结果对象，作为中间结果
        List<ReDoSBean> resultBeans = new ArrayList<>();
        for (int i = 0; i < tasksData.size(); i++) {
            resultBeans.add(new ReDoSBean(tasksData.get(i), i + 1, i + 1, "TIME-OUT"));
        }
        for (ReDoSBean bean : validateBeans.getData()) {
            resultBeans.set(bean.getId() - 1, bean);
        }
        //

        //设置执行检测和验证时间信息
        ResultMsg.setSumTime((validateEndTime - startTime) / 1000 + "(s)");
        ResultMsg.setCheckTime((checkEndTime - startTime) / 1000 + "(s)");
        ResultMsg.setValidateTime((validateEndTime - checkEndTime) / 1000 + "(s)");
        ResultMsg.setAverageTime((validateEndTime - startTime) / 1000 / (double) tasksData.size() + "(s)");
        ResultMsg.setTestLanguage(language);
        ResultArr.add(ResultMsg);
        //输出成文件
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("real sum time = " + ResultMsg.getSumTime());
        list2.add("real check time = " + ResultMsg.getCheckTime());
        list2.add("real validate time = " + ResultMsg.getValidateTime());
        list2.add("real average time = " + ResultMsg.getAverageTime());
        list2.add("real language = " + ResultMsg.getTestLanguage());
        list2.add("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        //记录结果
        for (ReDoSBean bean : resultBeans) {
            MyResultBean result=new MyResultBean();//中间结果
            result.setRegexID(bean.getRegexID());
            result.setId(bean.getRegexID());
            result.setRegex(bean.getRegex());
            //判断是否是漏洞的regex
            if (bean.isReDoS()) {//是
                result.setReDoS(true);
                result.setIsReDos("RESULT-TRUE");
                result.setType(bean.getType());
                result.setVul(bean.getVul());
                //
                //分别记录单个bean下的所有测试的攻击字符串和成功攻击的字符串
                boolean flag = true;
                for (int i = 0; i < bean.getAttackBeanList().size(); i++) {
                    MyAttackString myAttackString = new MyAttackString();
                    MyAttackString succuessString = new MyAttackString();
                    AttackBean currentBean = bean.getAttackBeanList().get(i);
                    //基本信息
                    myAttackString.setRegex(bean.getRegex());
                    myAttackString.setTestLanguage(bean.getTestLanguage());
                    //记录所有测试的攻击字符串
                    myAttackString.setAttackSuccess(currentBean.isAttackSuccess());//是否攻击成功
                    myAttackString.setAttackTime(currentBean.getAttackTime());//攻击时间
                    myAttackString.setVulPosition(currentBean.getLocateVulnerabilityRegex());//漏洞位置
                    myAttackString.setType(currentBean.getType());//漏洞时间复杂度
                    myAttackString.setAttackString(currentBean.getAttackStringFormat());//成功的攻击字符串
                    myAttackString.setVulSource(currentBean.getVulnerabilityRegexSource());//漏洞原因
                    myAttackString.setPatternType(currentBean.getPatternType());//漏洞模式


                    if (bean.getAttackBeanList().get(i).isAttackSuccess()) {
                        myAttackString.setIsattackSuccess("ATTACK_SUCCESS");
                        if ((model.equals(ATTACK_MODEL_SINGLE) && flag) || model.equals(ATTACK_MODEL_MULTI)) {//判断是m还是s模式，m模式下成功的字符串不止一个，有多个，s模式只有一个
                            //
                            succuessString.setRegex(bean.getRegex());
                            succuessString.setTestLanguage(bean.getTestLanguage());
                            //
                            succuessString.setAttackSuccess(bean.getAttackBeanList().get(i).isAttackSuccess());//是否攻击成功
                            succuessString.setIsattackSuccess("ATTACK_SUCCESS");//是否攻击成功
                            System.out.println(currentBean.getAttackTime());
                            succuessString.setAttackTime(currentBean.getAttackTime());//攻击时间
                            succuessString.setVulPosition(bean.getAttackBeanList().get(i).getLocateVulnerabilityRegex());//漏洞位置
                            succuessString.setType(bean.getAttackBeanList().get(i).getType());//漏洞时间复杂度
                            succuessString.setAttackString(bean.getAttackBeanList().get(i).getAttackStringFormat());//成功的攻击字符串
                            succuessString.setVulSource(bean.getAttackBeanList().get(i).getVulnerabilityRegexSource());//原因
                            succuessString.setPatternType(bean.getAttackBeanList().get(i).getPatternType());
                            flag = false;
                            result.getSuccessBeanList().add(succuessString);
                        }

                    } else {
                        myAttackString.setIsattackSuccess("ATTACK_FAILED");
                    }
                    result.getAttackBeanList().add(myAttackString);

                }
            } else {//不是
                result.setReDoS(false);
                result.setIsReDos("RESULT-FALSE");
                result.setMessage(bean.getMessage());
                result.setReDoS(FALSE);

                for (int i = 0; i < bean.getAttackBeanList().size(); i++) {
                    MyAttackString myAttackString = new MyAttackString();
//                    MyAttackString succuessString = new MyAttackString();
                    //记录所有测试的攻击字符串
                    myAttackString.setAttackSuccess(bean.getAttackBeanList().get(i).isAttackSuccess());//是否攻击成功
                    myAttackString.setAttackTime(bean.getAttackBeanList().get(i).getAttackTime());//攻击时间
                    myAttackString.setVulPosition(bean.getAttackBeanList().get(i).getLocateVulnerabilityRegex());//漏洞位置
                    myAttackString.setType(bean.getAttackBeanList().get(i).getType());//漏洞时间复杂度
                    myAttackString.setAttackString(bean.getAttackBeanList().get(i).getAttackStringFormat());//成功的攻击字符串
                    myAttackString.setVulSource(bean.getAttackBeanList().get(i).getVulnerabilityRegexSource());

                    if (bean.getAttackBeanList().get(i).isAttackSuccess()) {
                        myAttackString.setIsattackSuccess("ATTACK_SUCCESS");//是否攻击成功

                    } else {
                        myAttackString.setIsattackSuccess("ATTACK_FAILED");//是否攻击成功
                    }
                    result.getAttackBeanList().add(myAttackString);
                }
            }
            //加入该对象
            ResultArr.add(result);
        }
//        System.out.println(ResultArr);
        //写成文件输出
        for (MyResultBean res:ResultArr) {
//            if(res.getRegex().equals(null)){
//                System.out.println("dangqianshinullvalue--------------");
//            }

            list.add("id:" + res.getRegexID());
            list.add("正则表达式:" + res.getRegex());
            list.add("是否是ReDos漏洞：" + res.getIsReDos());
//            list.add("语言环境：" + res.getTestLanguage());
            list.add("攻击字符串如下:");
            if (res.isReDoS()) {
                int i=1;
                for (MyAttackString resattack : res.getSuccessBeanList()) {
                    list.add("------------------------");
                    list.add("编号："+i);
                    list.add("攻击字符串：" + resattack.getAttackString());//成功的攻击字符串
                    list.add("攻击是否成功：" + resattack.getIsattackSuccess());
                    list.add("攻击时间：" + resattack.getAttackTime());//攻击时间
                    list.add("时间复杂度：" + resattack.getType());//漏洞时间复杂度
                    list.add("漏洞模式：" + resattack.getPatternType());
                    list.add("漏洞位置：" + resattack.getVulPosition());//漏洞位置
                    list.add("漏洞原因：" + resattack.getVulSource());
                    list.add("------------------------");
                    if (i+1>res.getSuccessBeanList().size()){
                        i=0;
                    }else{
                        i++;
                    }
                }

                list2.add("id:" + res.getRegexID());
                list2.add("正则表达式:" + res.getRegex());
                list2.add("是否是ReDos漏洞：" + res.getIsReDos());
//                list2.add("语言环境：" + res.getTestLanguage());
                list2.add("攻击字符串如下:");
                i=1;
                for (MyAttackString resscsattack : res.getSuccessBeanList()) {
                    list2.add("------------------------");
                    list2.add("攻击字符串编号："+i);
                    list2.add("攻击字符串：" + resscsattack.getAttackString());//成功的攻击字符串
                    list2.add("攻击是否成功：" + resscsattack.getIsattackSuccess());
                    list2.add("攻击时间：" + resscsattack.getAttackTime());//攻击时间
                    list2.add("时间复杂度：" + resscsattack.getType());//漏洞时间复杂度
                    list2.add("漏洞模式：" + resscsattack.getPatternType());
                    list2.add("漏洞位置：" + resscsattack.getVulPosition());//漏洞位置
                    list2.add("漏洞原因：" + resscsattack.getVulSource());
                    list2.add("------------------------");
                    if (i>res.getSuccessBeanList().size()){
                        i=0;
                    }else{
                        i++;
                    }
                }
                list2.add("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            }else{
                list.add("并非ReDos,攻击字符串为空");
            }
            list.add("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
        try {
            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
            DateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String tsStr = sdf.format(timeStamp);
            tsStr = model + "_" + language + "_" + patternModel + "_" + "0" + "_" + tsStr;
            FileUtils.writeLines(new File("data/expr/" + "mytest" + "_redos_" + tsStr + ".txt"), list);
            FileUtils.writeLines(new File("data/expr/" + "mytest" + "_only_redos_true_" + tsStr + ".txt"), list2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultArr;
    }


//    public static String PanelTest(String sourceFile, String outfileName, String model, String language, String patternModel, int funcType, int checkThreadCount, int validateThreadCount, int timeout) {
//        //根据路径读取上传文件
//        List<String> tasksData = readFile(sourceFile);
//        //记录开始时间的当前时间戳
//        long startTime = System.currentTimeMillis();
//        //分线程检测
//        MultiThreadUtils<String, ReDoSBean> threadUtils = MultiThreadUtils.newInstance(checkThreadCount, timeout);
//        MultiBaseBean<List<ReDoSBean>> multiBaseBean;
//        if (tasksData == null || tasksData.isEmpty()) {//判断上传的文件是否为空
//            multiBaseBean = new MultiBaseBean<>(null);
//        } else {
//            multiBaseBean = threadUtils.execute(tasksData, null, new ITask<String, ReDoSBean>() {
//                @Override
//                public ReDoSBean execute(String regex, Map<String, Integer> params) {
//                    return (UniReDoSMain.checkReDoS(regex, params.get("id"), patternModel, language));//调用checkRedos
//                }
//            });
//        }
//        //记录检测结束时间的当前时间戳
//        long checkEndTime = System.currentTimeMillis();
//        //分线程验证
//        MultiThreadUtils<ReDoSBean, ReDoSBean> threadValidateUtils = MultiThreadUtils.newInstance(validateThreadCount, timeout);
//        MultiBaseBean<List<ReDoSBean>> validateBeans;
//        validateBeans = threadValidateUtils.execute(multiBaseBean.getData(), null, new ITask<ReDoSBean, ReDoSBean>() {
//            @Override
//            public ReDoSBean execute(ReDoSBean bean, Map<String, Integer> params) {
//                return (UniReDoSMain.validateReDoS(bean, model, language));
//            }
//        });
//        //记录验证结束时间的当前时间戳
//        long validateEndTime = System.currentTimeMillis();
//        //初始化结果函数
//        List<ReDoSBean> resultBeans = new ArrayList<>();
//        for (int i = 0; i < tasksData.size(); i++) {
//            resultBeans.add(new ReDoSBean(tasksData.get(i), i + 1, i + 1, "TIME-OUT"));
//        }
//        for (ReDoSBean bean : validateBeans.getData()) {
//            resultBeans.set(bean.getId() - 1, bean);
//        }
//        ArrayList<String> list = new ArrayList<>();//记录所有正则表达式的检测结果
//        ArrayList<String> list2 = new ArrayList<>();//只记录包含ReDos的检测结果
//
//        list2.add("real sum time = " + (validateEndTime - startTime) / 1000 + "(s)");
//        list2.add("real check time = " + (checkEndTime - startTime) / 1000 + "(s)");
//        list2.add("real validate time = " + (validateEndTime - checkEndTime) / 1000 + "(s)");
//        list2.add("real average time = " + (validateEndTime - startTime) / 1000 / (double) tasksData.size() + "(s)");
//
//        for (ReDoSBean bean : resultBeans) {
//            list.add("id:" + String.valueOf(bean.getRegexID()));
//            list.add("REGEX=" + String.valueOf(bean.getRegex()));
//            //判断是否是漏洞的regex
//            if (bean.isReDoS()) {//是
//                list2.add("id:" + String.valueOf(bean.getRegexID()));
//                list2.add("REGEX=" + String.valueOf(bean.getRegex()));
//                list2.add("RESULT-TRUE");
//                list2.add(bean.getType().name());//时间复杂度
//                list2.add("nums:" + String.valueOf(bean.getVul()));//
//                //
//                list.add("RESULT-TRUE");
//                //记录结果，List包含所有信息，List2只包含包含漏洞的所有信息（只包含成功攻击的字符串）
//                boolean flag = true;
//                for (int i = 0; i < bean.getAttackBeanList().size(); i++) {
//                    if (bean.getAttackBeanList().get(i).isAttackSuccess()) {
//                        list.add("success TYPE: " + bean.getAttackBeanList().get(i).getType() + "\t AttackString：" + bean.getAttackBeanList().get(i).getAttackStringFormat());
//                        list.add("vulnerability Position: " + bean.getAttackBeanList().get(i).getLocateVulnerabilityRegex());
//                        list.add("vulnerability Source: " + bean.getAttackBeanList().get(i).getVulnerabilityRegexSource());
//                        if ((model.equals(ATTACK_MODEL_SINGLE) && flag) || model.equals(ATTACK_MODEL_MULTI)) {  // 需要判断使用的是m还是s
//                            list2.add(bean.getAttackBeanList().get(i).getAttackStringFormatType());//记录攻击字符串
//                            list2.add("patternType: " + bean.getAttackBeanList().get(i).getPatternType());
//                            list2.add("vulnerability Position: " + bean.getAttackBeanList().get(i).getLocateVulnerabilityRegex());
//                            list2.add("vulnerability Source: " + bean.getAttackBeanList().get(i).getVulnerabilityRegexSource());
//                            flag = false;
//                        }
//                    } else {
//                        list.add("failed TYPE: " + bean.getAttackBeanList().get(i).getType() + "\t AttackString：" + bean.getAttackBeanList().get(i).getAttackStringFormat());
//                        list.add("patternType: " + bean.getAttackBeanList().get(i).getPatternType());
//                    }
//                }
//                list2.add("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
////
//            } else {//不是
//                list.add("RESULT-FALSE");
//                list.add(bean.getMessage());
//                for (int i = 0; i < bean.getAttackBeanList().size(); i++) {
//                    if (bean.getAttackBeanList().get(i).isAttackSuccess()) {
//                        list.add("success TYPE: " + bean.getAttackBeanList().get(i).getType() + "\t AttackString：" + bean.getAttackBeanList().get(i).getAttackStringFormat() + "\t patternType: " + bean.getAttackBeanList().get(i).getPatternType());
//                    } else {
//                        list.add("failed TYPE:" + bean.getAttackBeanList().get(i).getType() + "\t AttackString：" + bean.getAttackBeanList().get(i).getAttackStringFormat() + "\t patternType: " + bean.getAttackBeanList().get(i).getPatternType());
//
//                    }
//                }
//            }
//            list.add("-------------------------");
//        }
////        try {
////            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
////            DateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
////            String tsStr = sdf.format(timeStamp);
////            tsStr = model + "_" + language + "_" + patternModel + "_" + funcType + "_" + tsStr;
////            FileUtils.writeLines(new File("data/expr/" + outfileName.replace(".txt", "") + "_redos_" + tsStr + ".txt"), list);
////            FileUtils.writeLines(new File("data/expr/" + outfileName.replace(".txt", "") + "_only_redos_true_" + tsStr + ".txt"), list2);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//        System.out.println(list);
//        System.out.println(list2);
//        return list2.toString();
//
//    }

//    public static void onlyCheck(String sourceFile, String outfileName, String model, String language, String patternModel, int funcType, int checkThreadCount, int validateThreadCount, int timeout) {
//        List<String> tasksData = readFile(sourceFile);
//        MultiThreadUtils<String, ReDoSBean> threadUtils = MultiThreadUtils.newInstance(checkThreadCount, timeout);
//        MultiBaseBean<List<ReDoSBean>> multiBaseBean;
//        if (tasksData == null || tasksData.isEmpty()) {
//            multiBaseBean = new MultiBaseBean<>(null);
//        } else {
//            multiBaseBean = threadUtils.execute(tasksData, null, new ITask<String, ReDoSBean>() {
//                @Override
//                public ReDoSBean execute(String regex, Map<String, Integer> params) {
//                    return (UniReDoSMain.checkReDoS(regex, params.get("id"), patternModel, language));
//                }
//            });
//        }
//        List<ReDoSBean> list = multiBaseBean.getData();
//
//        try {
//            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
//            DateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
//            String tsStr = sdf.format(timeStamp);
//            tsStr = model + "_" + language + "_" + patternModel + "_" + funcType + "_" + tsStr;
//            ArrayList<Output> outputs = new ArrayList<>();
//            for (int i = 0; i < list.size(); i++) {
//                ReDoSBean reDoSBean = list.get(i);
//                ArrayList<Attack> attackArrayList = new ArrayList<>();
//                int k = 0;
//                for (AttackBean bean : reDoSBean.getAttackBeanList()) {
//                    Attack attack = new Attack(bean.getPrefix(), bean.getInfix(), bean.getSuffix(), bean.getType(), bean.getPatternType());
//                    attackArrayList.add(attack);
//                    k++;
//                }
//                outputs.add(new Output(reDoSBean.getRegexID(), reDoSBean.getRegex(), attackArrayList));
//            }
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            String json = gson.toJson(outputs);
////            String json= JSON.toJSONString(outputs);
//            FileUtils.write(new File("C:\\Users\\pengqc\\Desktop\\pqc\\csharp_only_check\\" + outfileName.replace(".txt", "") + "_only_check_" + tsStr + ".txt"), json, "utf-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



//    public static void pkg(String[] args) throws ParseException {
//
//        CommandLineParser parser = new BasicParser();
//        Options options = new Options();
//        options.addOption("h", "help", false, "Print this usage information");
//        options.addOption("s", "source file", true, "file to run");
//        options.addOption("o", "output file", true, "file to save program output to");
//        options.addOption("m", "model", false, "attack model s or m");
//        options.addOption("l", "language", false, "validate language such as java python or js");
//        options.addOption("p", "pattern model", false, "check model such 11111 is all pattern model");
//        options.addOption("f", "attack func", false, "attack func 0-all,  1-match or 2-find");
//        options.addOption("c", "check", false, "check thread num");
//        options.addOption("v", "validate", false, "validate thread num");
//        options.addOption("t", "time", false, "time_out second");
//        CommandLine commandLine = parser.parse(options, args);
//
//        String sourcefile = "";
//        String outfileName = "";
//        String model = "s";
//        String language = "java";
//        String patternModel = "11111";
//        int funcType = 0;
//        int checkThreadCount = 15;
//        int validateThreadCount = 1;
//        int time = 60;
//        if (commandLine.hasOption('h')) {
//            HelpFormatter formatter = new HelpFormatter();
//            PrintWriter writer = new PrintWriter(System.out);
//            formatter.printUsage(writer, 80, "ReDoSHunter", options);
//            formatter.printHelp("ReDoSHunter", options);
//            writer.flush();
//            System.exit(0);
//        }
//        if (commandLine.hasOption('s')) {
//            sourcefile = commandLine.getOptionValue("s");
//        }
//        if (commandLine.hasOption('o')) {
//            outfileName = commandLine.getOptionValue("o");
//        }
//        if (commandLine.hasOption('m')) {
//            model = commandLine.getOptionValue('m');
//        }
//        if (commandLine.hasOption('l')) {
//            language = commandLine.getOptionValue('l');
//        }
//        if (commandLine.hasOption('p')) {
//            patternModel = commandLine.getOptionValue('p');
//        }
//        if (commandLine.hasOption('f')) {
//            funcType = Integer.parseInt(commandLine.getOptionValue('f'));
//        }
//        if (commandLine.hasOption('c')) {
//            checkThreadCount = Integer.parseInt(commandLine.getOptionValue('c'));
//        }
//        if (commandLine.hasOption('v')) {
//            validateThreadCount = Integer.parseInt(commandLine.getOptionValue('v'));
//        }
//
//        if (commandLine.hasOption('t')) {
//            time = Integer.parseInt(commandLine.getOptionValue('t'));
//        }
//        System.out.println(sourcefile);
//        System.out.println(outfileName);
//        System.out.println(model);
//        System.out.println(language);
//        System.out.println(patternModel);
//        System.out.println(funcType);
//        System.out.println(checkThreadCount);
//        System.out.println(validateThreadCount);
//        System.out.println(time);
//
//        run(sourcefile, outfileName, model, language, patternModel, funcType, checkThreadCount, validateThreadCount, time);
//        System.out.println("exit");
//        System.exit(0);
//    }
}