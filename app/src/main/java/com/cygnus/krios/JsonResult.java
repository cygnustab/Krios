package com.cygnus.krios;

/**
 * Created by Mitesh on 6/21/2016.
 */
public class JsonResult {
    String iconName = "";
    int iconImg = -1; // menu icon resource id


   String  CodeDesc;

    public String getCodeDesc() {
        return CodeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        CodeDesc = codeDesc;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public int getIconImg() {
        return iconImg;
    }

    public void setIconImg(int iconImg) {
        this.iconImg = iconImg;
    }
}
