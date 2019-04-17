package com.qsd.jmwh.module.home.radio.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class LocalHomeRadioListBean implements MultiItemEntity {
    @Override
    public int getItemType() {

        return itemType;
    }
    public int itemType;
    public String headImg;
    public String userName;
    public String cTime;
    public List<String> label;
    public List<String> picList;
    public int is_like;
    public int count_num;
    public int sex;
    public int is_apply;
}
