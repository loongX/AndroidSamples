package com.example.jsion_samples;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pxl on 2019/9/16 0016 下午 3:50.
 * Describe:
 */
public class JsonConfMain {
   public static String jsonString1 = "{\"name\":\"配置文件\",\n" +
            " \"parameter\":[\n" +
            "\t{\"par0\":\"1\",\"par1\":\"\",\"par2\":\"\",\"par3\":\"\",\"check\":\"1666\"},\n" +
            "\t{\"par0\":\"TSGJ\",\"par1\":\"测试\",\"par2\":\"\",\"par3\":\"\",\"check\":\"3251\"}\n" +
            "\t ]\n" +
            "}";

   public static String jsonString2 = "{\"name\":\"配置文件\",\n" +
           " \"parameter\":[\n" +
           "\t{\"name\":\"文件版本\",\"parameter\":{\"par0\":\"1\",\"par1\":\"\",\"par2\":\"\",\"par3\":\"\",\"check\":\"1666\"}},\n" +
           "\t{\"name\":\"生成文件名\",\"parameter\":{\"par0\":\"TSGJ\",\"par1\":\"测试\",\"par2\":\"\",\"par3\":\"\",\"check\":\"3251\"}}\n" +
           "\t\t ]\n" +
           "}";
    private String name;
    private List<Parameter> parameter;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setParameter(List<Parameter> parameter) {
        this.parameter = parameter;
    }
    public List<Parameter> getParameter() {
        return parameter;
    }

    public class Parameter {

        private String par0;
        private String par1;
        private String par2;
        private String par3;
        private String check;
        public void setPar0(String par0) {
            this.par0 = par0;
        }
        public String getPar0() {
            return par0;
        }

        public void setPar1(String par1) {
            this.par1 = par1;
        }
        public String getPar1() {
            return par1;
        }

        public void setPar2(String par2) {
            this.par2 = par2;
        }
        public String getPar2() {
            return par2;
        }

        public void setPar3(String par3) {
            this.par3 = par3;
        }
        public String getPar3() {
            return par3;
        }

        public void setCheck(String check) {
            this.check = check;
        }
        public String getCheck() {
            return check;
        }

    }

    /**
     * 测试用例
     * @param args
     */
    public static void main(String[] args) {
        Gson gson = new Gson();
        JsonConfMain user = gson.fromJson(jsonString1, JsonConfMain.class);
        System.out.println("name：" + user.getName());
        List<Parameter> parameterList = user.getParameter();
        for (Parameter signParma : parameterList) {
            String par0 = signParma.getPar0();
            String par1 = signParma.getPar1();
            String par2 = signParma.getPar2();
            String par3 = signParma.getPar3();
            System.out.println("par0：" + par0 + "par1：" + par1 + "par2：" + par2 + "par3：" + par3);
        }

        Gson gson2 = new Gson();
        JsonConf user2 = gson.fromJson(jsonString2, JsonConf.class);
        System.out.println("name：" + user.getName());
        List<com.example.jsion_samples.JsonConf.Parameter> parameterList2 = user2.getParameter();
        for (com.example.jsion_samples.JsonConf.Parameter signParma : parameterList2) {
            String name = signParma.getName();
            JsonConf.ParameterVar param = signParma.getParameter();
            String par0 = param.getPar0();
            String par1 = param.getPar1();
            String par2 = param.getPar2();
            String par3 = param.getPar3();
            System.out.println("解析2 name：" + name);
            System.out.println("par0：" + par0 + "par1：" + par1 + "par2：" + par2 + "par3：" + par3);

        }



        com.example.jsion_samples.JsonConf.ParameterVar parameterVar3_1 = new com.example.jsion_samples.JsonConf.ParameterVar();
        parameterVar3_1.setCheck("000111");
        parameterVar3_1.setPar0("hello3_1");
        parameterVar3_1.setPar1("hello3_1_1");
        parameterVar3_1.setPar2("hello3_1_2");
        parameterVar3_1.setPar3("hello3_1_3");

        com.example.jsion_samples.JsonConf.ParameterVar parameterVar3_2 = new com.example.jsion_samples.JsonConf.ParameterVar();
        parameterVar3_2.setCheck("dfdf");
        parameterVar3_2.setPar0("dfcddd");
        parameterVar3_2.setPar1("dlfjdlf");
        parameterVar3_2.setPar2("732947509384");
        parameterVar3_2.setPar3("cvjldjf");


        com.example.jsion_samples.JsonConf.Parameter parameter3_1 = new com.example.jsion_samples.JsonConf.Parameter();
        parameter3_1.setName("文件版本");
        parameter3_1.setParameter(parameterVar3_1);

        com.example.jsion_samples.JsonConf.Parameter parameter3_2 = new com.example.jsion_samples.JsonConf.Parameter();
        parameter3_2.setName("生成文件名");
        parameter3_2.setParameter(parameterVar3_2);

        List<com.example.jsion_samples.JsonConf.Parameter> parameterList3 = new ArrayList<>();
        parameterList3.add(parameter3_1);
        parameterList3.add(parameter3_2);


        JsonConf jsonConf = new JsonConf();
        jsonConf.setName("配置文件");
        jsonConf.setParameter(parameterList3);

        Gson gson3 = new Gson();
        String buildjson = gson3.toJson(jsonConf);
        System.out.println("生成json：" + buildjson);

    }
}



