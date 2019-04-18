package com.dongmodao.annoprocess;


/**
 * @author: dongmodao
 * @date: 2019/4/19
 * @time: 2:08
 */
public class FieldInfo {

    private String fieldName;

    private String val;

    public FieldInfo(String fieldName, String val) {
        this.fieldName = fieldName;
        this.val = val;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }


}
