package com.qsd.jmwh.module.register.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author yudneghao
 * @date 2019/4/17
 */
public class RangeData implements Serializable {
  public List<Range> cdoList;

  public class Range implements Serializable {
    public int lId;
    public int ParentId;
    public String sName;
    public int nStatus;
    public int nLevel;
    public boolean selected;
  }
}
