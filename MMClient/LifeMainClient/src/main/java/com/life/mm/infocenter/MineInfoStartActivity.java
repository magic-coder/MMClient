package com.life.mm.infocenter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.avos.avoscloud.AVFile;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.life.mm.R;
import com.life.mm.framework.app.ActivityHelper;
import com.life.mm.framework.app.MMApplication;
import com.life.mm.framework.app.base.activity.BaseActivity;
import com.life.mm.framework.libwrapper.glide.GlideCircleTransform;
import com.life.mm.framework.libwrapper.glide.GlideUtils;
import com.life.mm.framework.user.CustomUser;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.infocenter.presenter <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/27 16:56. <P>
 * Function: <P>
 * Modified: <P>
 */

public class MineInfoStartActivity extends BaseActivity {

    @Bind(R.id.info_start_head_img)
    ImageView infoStartHeadImg;
    @Bind(R.id.info_head_layout)
    RelativeLayout infoHeadLayout;
    @Bind(R.id.info_detail_layout)
    RelativeLayout infoDetailLayout;
    @Bind(R.id.info_photo_album_layout)
    RelativeLayout infoPhotoAlbumLayout;
    @Bind(R.id.info_card_layout)
    RelativeLayout infoCardLayout;
    @Bind(R.id.hobby_show_settings_layout)
    RelativeLayout hobbyShowSettingsLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_info_start;
    }

    @Override
    protected void initView() {
        setToolbarTitle(R.string.common_edit);
        CustomUser customUser = MMApplication.getInstance().getCustomUser();
        if (null != customUser && !TextUtils.isEmpty(customUser.getHeadUrl())) {
            AVFile avFile = new AVFile("header.png", customUser.getHeadUrl(), new HashMap<String, Object>());
            String headThumbnailUrl = avFile.getThumbnailUrl(true, 50, 50);
            GlideUtils.loadHeadImg(headThumbnailUrl, infoStartHeadImg, false, DecodeFormat.DEFAULT, new GlideCircleTransform(mContext), DiskCacheStrategy.SOURCE);
        }
    }

    @Override
    protected void initPresenter() {
    }

    @OnClick({R.id.info_head_layout, R.id.info_detail_layout,
            R.id.info_photo_album_layout, R.id.info_card_layout, R.id.hobby_show_settings_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_head_layout:
                break;
            case R.id.info_detail_layout:
                ActivityHelper.goActivity(mContext, MineInfoDetailActivity.class, null);
                break;
            case R.id.info_photo_album_layout:
                break;
            case R.id.info_card_layout:
                break;
            case R.id.hobby_show_settings_layout:
                break;
        }
    }
}
