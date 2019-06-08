package com.netease.nim.uikit;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @author yudneghao
 * @date 2019-06-07
 */

@GlideModule
public class MyAppGlideModule extends AppGlideModule {

  @Override public boolean isManifestParsingEnabled() {
    return false;
  }
}
