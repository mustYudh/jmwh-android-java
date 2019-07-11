package com.qsd.jmwh.module.home.user.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.ImageView;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseActivity;
import com.yu.common.ui.video.SurfaceVideoViewCreator;
import com.yu.common.utils.DensityUtil;
import com.yu.common.utils.ImageLoader;

/**
 * @author yudneghao
 * @date 2019-05-26
 */
public class PlayVideoActivity extends BaseActivity {
  private final static String FILE_COVER_URL = "file_cover_url";
  private final static String FILE_URL = "file_url";
  private SurfaceVideoViewCreator surfaceVideoViewCreator;

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
      setContentView(R.layout.play_video_activity_layout);
    surfaceVideoViewCreator = new SurfaceVideoViewCreator(getActivity(), bindView(R.id.video)) {
      @Override protected Activity getActivity() {
        return PlayVideoActivity.this;
      }

      @Override protected boolean setAutoPlay() {
        return false;
      }

      @Override protected int getSurfaceWidth() {
        return 0;
      }

      @Override protected int getSurfaceHeight() {
        return DensityUtil.getScreenHeight();
      }

      @Override protected void setThumbImage(ImageView thumbImageView) {
        ImageLoader.loadCenterCrop(getActivity(), getIntent().getStringExtra(FILE_COVER_URL), thumbImageView);
      }

      @Override protected String getSecondVideoCachePath() {
        return null;
      }

      @Override protected String getVideoPath() {
        return getIntent().getStringExtra(FILE_URL);
      }
    };
  }

  @Override protected void loadData() {
    //setTitle("视频");
  }

  public static Intent getIntent(Context context,String fileUrl,String sFileCoverUrl) {
      Intent starter = new Intent(context, PlayVideoActivity.class);
      starter.putExtra(FILE_COVER_URL,sFileCoverUrl);
      starter.putExtra(FILE_URL,fileUrl);
      return starter;
  }

  @Override protected void onPause() {
    super.onPause();
    if (surfaceVideoViewCreator != null) {
      surfaceVideoViewCreator.onPause();
    }
  }

  @Override protected void onResume() {
    super.onResume();
    if (surfaceVideoViewCreator != null) {
      surfaceVideoViewCreator.onResume();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (surfaceVideoViewCreator != null) {
      surfaceVideoViewCreator.onDestroy();
    }
  }

  @Override public boolean dispatchKeyEvent(KeyEvent event) {
    if (surfaceVideoViewCreator != null) {
      surfaceVideoViewCreator.onKeyEvent(event);
    }
    return super.dispatchKeyEvent(event);
  }

}
