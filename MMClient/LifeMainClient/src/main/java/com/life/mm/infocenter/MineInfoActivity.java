package com.life.mm.infocenter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.life.mm.R;
import com.life.mm.common.config.GlobalConfig;
import com.life.mm.framework.app.base.activity.BaseCollapsingActivity;
import com.life.mm.framework.bean.SelectOption;
import com.life.mm.framework.ui.utils.SlideChooserWindowUtil;
import com.life.mm.framework.utils.MMUtils;
import com.life.mm.uicomponent.photopicker.GalleryActivity;
import com.life.mm.uicomponent.photopicker.GalleryConfig;

import java.util.ArrayList;
import java.util.List;

import static com.life.mm.infocenter.MineInfoActivity.HeadOption.LookBigImage;
import static com.life.mm.infocenter.MineInfoActivity.HeadOption.SelectFromGallery;
import static com.life.mm.infocenter.MineInfoActivity.HeadOption.TakePhotos;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.infocenter <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/16 17:08. <P>
 * Function: <P>
 * Modified: <P>
 */

public class MineInfoActivity extends BaseCollapsingActivity {

    enum HeadOption {
        SelectFromGallery(0),//从相册选择
        TakePhotos(1),//拍照
        LookBigImage(2);//查看大图
        private int value = 0;
        HeadOption(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    };
    private List<SelectOption> selectOptionList = null;
    private String headImgPath = null;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_mine_info;
    }

    @Override
    protected void initView() {
        setToolbarTitle(R.string.mine_info_title);
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void onClickHeadImg() {
        if (null == selectOptionList || selectOptionList.size() == 0) {
            selectOptionList = new ArrayList<>();
            String[] headOptions = mResources.getStringArray(R.array.head_options_array);
            if (headOptions.length > 0) {
                for (int i = 0; i < headOptions.length; i++) {
                    String item = headOptions[i];
                    selectOptionList.add(new SelectOption(item, i));
                }
            }
        }
        if (MMUtils.isAvaliableList(selectOptionList)) {
            SlideChooserWindowUtil.showBottomPopSelectDlg((Activity) mContext, selectOptionList, mResources.getString(R.string.common_cancel), new SlideChooserWindowUtil.OptionSelectedListener() {
                @Override
                public void onOptionSelected(SelectOption option) {
                    int index = (Integer) option.getValue();
                    if (SelectFromGallery.getValue() == index) {
                        selectImageFromGallery();
                    } else if (TakePhotos.getValue() == index) {
                        takePhotos();
                    } else if (LookBigImage.getValue() == index) {
                        lookBigImage();
                    }
                }
            });
        }
    }

    private void selectImageFromGallery() {
        GalleryConfig config = new GalleryConfig.Build()
                .singlePhoto(true)
                .build();
        GalleryActivity.openActivity((Activity) mContext, GlobalConfig.REQUEST_CODE_SELECT_PHOTOS, config);
    }

    private void takePhotos() {}

    private void lookBigImage() {}

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == GlobalConfig.REQUEST_CODE_SELECT_PHOTOS) {
                    List<String> listPaths = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
                    if (MMUtils.isAvaliableList(listPaths)) {
                        headImgPath = listPaths.get(0);
                    }
                    if (!TextUtils.isEmpty(headImgPath)) {
                        loadHeadImg(headImgPath);
                    }
                }
                break;

            case RESULT_CANCELED:
                break;
            default:
                break;
        }
    }
}
