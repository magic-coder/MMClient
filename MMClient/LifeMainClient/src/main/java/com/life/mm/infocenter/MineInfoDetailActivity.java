package com.life.mm.infocenter;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.life.mm.R;
import com.life.mm.common.config.GlobalConfig;
import com.life.mm.framework.app.ActivityHelper;
import com.life.mm.framework.app.MMApplication;
import com.life.mm.framework.app.base.activity.BaseActivity;
import com.life.mm.framework.user.CustomUser;

import butterknife.Bind;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.infocenter <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/28 11:01. <P>
 * Function: <P>
 * Modified: <P>
 */

public class MineInfoDetailActivity extends BaseActivity {
    @Bind(R.id.info_nickname_text)
    TextView infoNicknameText;
    @Bind(R.id.info_gender_text)
    TextView infoGenderText;
    @Bind(R.id.info_age_text)
    TextView infoAgeText;
    @Bind(R.id.info_birthday_text)
    TextView infoBirthdayText;
    @Bind(R.id.info_constellation_text)
    TextView infoConstellationText;
    @Bind(R.id.info_occupation_text)
    TextView infoOccupationText;
    @Bind(R.id.info_company_text)
    TextView infoCompanyText;
    @Bind(R.id.info_school_text)
    TextView infoSchoolText;
    @Bind(R.id.info_current_address_text)
    TextView infoCurrentAddressText;
    @Bind(R.id.info_hometown_address_text)
    TextView infoHometownAddressText;
    @Bind(R.id.info_email_text)
    TextView infoEmailText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_info_detail;
    }

    @Override
    protected void initView() {
        setToolbarTitle(R.string.info_detail_title);
        CustomUser customUser = MMApplication.getInstance().getCustomUser();
        initPersonInfo(customUser);
    }

    private void initPersonInfo(CustomUser customUser) {
        infoNicknameText.setText(customUser.getNickName());
        String gender = null;
        try {
            gender = GlobalConfig.genderArray.get(customUser.getGender(), "--");
        } catch (Exception e) {
            e.printStackTrace();
        }
        infoGenderText.setText(gender);
        String age = customUser.getAge() + "";
        infoAgeText.setText(age);
        infoBirthdayText.setText(customUser.getBirthDay());
        infoConstellationText.setText(customUser.getConstellation());

        infoOccupationText.setText(customUser.getOccupation());
        infoCompanyText.setText(customUser.getCompany());
        infoSchoolText.setText(customUser.getSchool());
        infoCurrentAddressText.setText(customUser.getAddress());
        infoHometownAddressText.setText(customUser.getHometownAddress());
        infoEmailText.setText(customUser.getEmail());
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mine_info_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mine_info_detail_edit:
                ActivityHelper.goActivity(mContext, MineInfoEditActivity.class, null);
                break;

            default:
                break;
        }
        return true;
    }
}
