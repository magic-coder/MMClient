package com.life.mm.infocenter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.life.mm.R;
import com.life.mm.common.config.GlobalConfig;
import com.life.mm.eventbus.SimpleNotifyEvent;
import com.life.mm.framework.app.MMApplication;
import com.life.mm.framework.app.base.activity.BaseActivity;
import com.life.mm.framework.ui.loading.DlgConfirmListener;
import com.life.mm.framework.ui.loading.LoadingDialogUtil;
import com.life.mm.framework.ui.widget.CcbEditText;
import com.life.mm.framework.user.CustomUser;
import com.life.mm.framework.user.UserManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

import static com.life.mm.common.config.GlobalConfig.genderArray;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.infocenter <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/28 14:15. <P>
 * Function: <P>
 * Modified: <P>
 */

public class MineInfoEditActivity extends BaseActivity {

    @Bind(R.id.info_edit_nickname_text)
    CcbEditText infoEditNicknameText;
    @Bind(R.id.info_edit_gender_layout)
    LinearLayout infoEditNicknameLayout;
    @Bind(R.id.info_edit_gender_text)
    TextView infoEditGenderText;
    @Bind(R.id.info_edit_age_text)
    TextView infoEditAgeText;
    @Bind(R.id.info_edit_age_layout)
    LinearLayout infoEditAgeLayout;
    @Bind(R.id.info_edit_birthday_text)
    TextView infoEditBirthdayText;
    @Bind(R.id.info_edit_birthday_layout)
    LinearLayout infoEditBirthdayLayout;
    @Bind(R.id.info_edit_constellation_text)
    TextView infoEditConstellationText;
    @Bind(R.id.info_edit_constellation_layout)
    LinearLayout infoEditConstellationLayout;
    @Bind(R.id.info_edit_occupation_text)
    TextView infoEditOccupationText;
    @Bind(R.id.info_edit_occupation_layout)
    LinearLayout infoEditOccupationLayout;
    @Bind(R.id.info_edit_company_text)
    CcbEditText infoEditCompanyText;
    @Bind(R.id.info_edit_school_text)
    CcbEditText infoEditSchoolText;
    @Bind(R.id.info_edit_current_address_text)
    TextView infoEditCurrentAddressText;
    @Bind(R.id.info_edit_current_address_layout)
    LinearLayout infoEditCurrentAddressLayout;
    @Bind(R.id.info_edit_hometown_address_text)
    TextView infoEditHometownAddressText;
    @Bind(R.id.info_edit_hometown_address_layout)
    LinearLayout infoEditHometownAddressLayout;
    @Bind(R.id.info_edit_email_text)
    CcbEditText infoEditEmailText;
    @Bind(R.id.info_edit_description)
    TextView infoEditDescription;
    @Bind(R.id.info_edit_self_description_layout)
    LinearLayout infoEditSelfDescriptionLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_info_edit;
    }

    @Override
    protected void initView() {
        setToolbarTitle(R.string.info_edit_title);
        CustomUser customUser = MMApplication.getInstance().getCustomUser();
        initPersonInfo(customUser);
    }

    private void initPersonInfo(CustomUser customUser) {
        infoEditNicknameText.setText(customUser.getNickName());
        String gender = null;
        try {
            gender = genderArray.get(customUser.getGender(), "--");
        } catch (Exception e) {
            e.printStackTrace();
        }
        infoEditGenderText.setText(gender);
        String ageStr = customUser.getAge() + "";
        infoEditAgeText.setText(ageStr);
        infoEditBirthdayText.setText(customUser.getBirthDay());
        infoEditConstellationText.setText(customUser.getConstellation());

        infoEditOccupationText.setText(customUser.getOccupation());
        infoEditCompanyText.setText(customUser.getCompany());
        infoEditSchoolText.setText(customUser.getSchool());
        infoEditCurrentAddressText.setText(customUser.getAddress());
        infoEditHometownAddressText.setText(customUser.getHometownAddress());
        infoEditEmailText.setText(customUser.getEmail());
        infoEditDescription.setText(customUser.getSelfDescription());
    }

    @Override
    protected void initPresenter() {
    }

    @OnClick({R.id.info_edit_age_layout, R.id.info_edit_gender_layout, R.id.info_edit_birthday_layout,
            R.id.info_edit_constellation_layout, R.id.info_edit_occupation_layout,
            R.id.info_edit_current_address_layout, R.id.info_edit_hometown_address_layout,
            R.id.info_edit_self_description_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_edit_age_layout:
                break;
            case R.id.info_edit_birthday_layout:
                break;
            case R.id.info_edit_constellation_layout:
                break;
            case R.id.info_edit_occupation_layout:
                break;
            case R.id.info_edit_current_address_layout:
                break;
            case R.id.info_edit_hometown_address_layout:
                break;
            case R.id.info_edit_self_description_layout:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mine_info_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mine_info_edit:
                //提交修改的数据
                final CustomUser customUser = MMApplication.getInstance().getCustomUser();
                if (null != customUser) {
                    customUser.setNickName(infoEditNicknameText.getText().toString());
                    int index = GlobalConfig.genderArray.indexOfValue(infoEditGenderText.getText().toString());
                    customUser.setGender(index);
                    int age = 0;
                    try {
                        String ageStr = infoEditAgeText.getText().toString();
                        ageStr = ageStr.substring(0, ageStr.indexOf("岁"));
                        age = Integer.parseInt(ageStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    customUser.setAge(age);
                    customUser.setBirthDay(infoEditBirthdayText.getText().toString());
                    customUser.setConstellation(infoEditConstellationText.getText().toString());

                    customUser.setOccupation(infoEditOccupationText.getText().toString());
                    customUser.setCompany(infoEditCompanyText.getText().toString());
                    customUser.setSchool(infoEditSchoolText.getText().toString());
                    customUser.setAddress(infoEditCurrentAddressText.getText().toString());
                    customUser.setHometownAddress(infoEditHometownAddressText.getText().toString());
                    customUser.setEmail(infoEditEmailText.getText().toString());

                    customUser.setSelfDescription(infoEditDescription.getText().toString());
                    onBegin();
                    customUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            onFinish();
                            if (null == e) {
                                //使用EventBus发送事件通知前一个页面刷新用户详细资料数据
                                Bundle bundle = new Bundle();
                                bundle.putParcelable(GlobalConfig.data, customUser);
                                SimpleNotifyEvent event = new SimpleNotifyEvent.Builder()
                                        .setSource(MineInfoEditActivity.class.getSimpleName())
                                        .setMsg(GlobalConfig.sourceEditUserInfo)
                                        .setData(bundle)
                                        .build();
                                EventBus.getDefault().postSticky(event);

                                LoadingDialogUtil.showSuccessDlgOneBtn("", getResourceValue(R.string.common_success), new DlgConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        finish();
                                    }
                                });
                                UserManager.getInstance().saveDevUser(customUser);
                            } else {
                                LoadingDialogUtil.showErrorDlg(e.getCode(), e.getMessage());
                            }
                        }
                    });
                }
                break;

            default:
                break;
        }
        return true;
    }
}
