package com.example.jsion_samples;

import java.util.List;

/**
 * Created by pxl on 2019/9/16 0016 下午 3:50.
 * Describe:
 */
public class JsonConf {
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

    public static class Parameter {

        private String name;
        private ParameterVar parameter;
        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setParameter(ParameterVar parameter) {
            this.parameter = parameter;
        }
        public ParameterVar getParameter() {
            return parameter;
        }
    }


    public static class ParameterVar {

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
}
