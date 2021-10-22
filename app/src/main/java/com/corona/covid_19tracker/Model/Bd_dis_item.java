package com.corona.covid_19tracker.Model;

import java.io.Serializable;

public class Bd_dis_item implements Serializable {
    private String disNameEn, disNameBn;
   private int confirm;
    public Bd_dis_item(String disNameEn, String disNameBn, int confirm) {
        this.disNameEn = disNameEn;
        this.disNameBn = disNameBn;
        this.confirm = confirm;
    }

    public String getDisNameEn() {
        return disNameEn;
    }

    public String getDisNameBn() {
        return disNameBn;
    }

    public int getConfirm() {
        return confirm;
    }
}
