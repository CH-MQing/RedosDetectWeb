package web;

import cn.ac.ios.Bean.MyAttackString;
import cn.ac.ios.Bean.MyResultBean;
import cn.ac.ios.Bean.ReDoSBean;
import org.apache.commons.io.IOUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static cn.ac.ios.PanelTestMain.*;
import static cn.ac.ios.UniReDoSMain.*;

@Controller
public class IndexController {



    @GetMapping("/")
    public String index(){
        return "DetectIndex";
    }

    @PostMapping("/upload")

    public String doLogin(Model dataModel,@Nullable String data, String model, String language) {
        System.out.println("model="+model+",language="+language+",data="+data);
        Logger.getGlobal().setLevel(Level.OFF);
        //检测
        ReDoSBean bean = validateReDoS(checkReDoS(data, 1, "11111", language), model, language);
        System.out.println("bean="+bean);
        dataModel.addAttribute("bean", bean);
        return "UniCheckres";
    }

    @PostMapping("/uploadFlie")
    public String FileCheck(Model model,@RequestParam("file") MultipartFile file, String checkmodel, String language){
        //读取上传文件内容
        BufferedReader reader = null;
        List<String> tasksData = new ArrayList<String>();
        try {
            //用流读取文件
            reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;
            StringBuffer content = new StringBuffer();
            // 读取想定文件对象
            while ((line = reader.readLine()) != null) {
                content.append(line);
                //逐行压入taskData作为检测的参数
                tasksData.add(line);//做检测输入
            }
            System.out.println("content = " + tasksData);
        } catch (IOException e) {

        } finally {
            if (reader != null) {
                IOUtils.closeQuietly(reader);
            }

        }
        System.out.println("task="+tasksData+",model="+checkmodel+",lan="+language);
        //检测
        List<MyResultBean> res =  PostPanelTest(tasksData,checkmodel, language);
        System.out.println(res);
        MyResultBean checkmsg=res.get(0);
        ArrayList<MyAttackString> attackstringList=new ArrayList<>();
        ArrayList<MyAttackString> successstringList=new ArrayList<>();
//
        for (MyResultBean myres:res) {
//            System.out.println(myres);
            for (MyAttackString mas:myres.getAttackBeanList()) {
                attackstringList.add(mas);
            }
        }
        System.out.println("================");
        for (MyResultBean myres:res) {
//            System.out.println(myres);
            for (MyAttackString mas:myres.getSuccessBeanList()) {
                successstringList.add(mas);
            }
        }
        System.out.println("attackstringList.size="+attackstringList.size());
//        System.out.println(attackstringList);
        model.addAttribute("res", res);
        model.addAttribute("checkmsg",checkmsg);
        model.addAttribute("attackstringList",attackstringList);
        model.addAttribute("successstringList",successstringList);
        //返回结果页面
        return "PanelTest";
    }
}
