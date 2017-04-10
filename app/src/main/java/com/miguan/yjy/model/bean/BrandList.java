package com.miguan.yjy.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (c) 2017/4/9. LiaoPeiKun Inc. All rights reserved.
 */

public class BrandList implements Serializable {
    private List<Brand> hotCosmetics;

    private List<Brand>[] otherCosmetics;

    public List<Brand> getHotCosmetics() {
        return hotCosmetics;
    }

    public void setHotCosmetics(List<Brand> hotCosmetics) {
        this.hotCosmetics = hotCosmetics;
    }

    public List<Brand>[] getOtherCosmetics() {
        return otherCosmetics;
    }

    public void setOtherCosmetics(List<Brand>[] otherCosmetics) {
        this.otherCosmetics = otherCosmetics;
    }
}
