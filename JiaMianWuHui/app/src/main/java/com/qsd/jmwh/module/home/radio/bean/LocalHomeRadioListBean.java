package com.qsd.jmwh.module.home.radio.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class LocalHomeRadioListBean implements MultiItemEntity {
    @Override
    public int getItemType() {

        return itemType;
    }

    public int itemType;
    public int lDatingId;
    public int lUserId;
    public String headImg;
    public String userName;
    public String cTime;
    public List<String> picList;
    public int is_like;
    public int like_count;
    public int nApplyCount;
    public int count_num;
    public int sex;
    public int is_apply;
    public String sDatingTitle;
    public String sDatingRange;
    public String sDatingTime;
    public String sContent;
}
