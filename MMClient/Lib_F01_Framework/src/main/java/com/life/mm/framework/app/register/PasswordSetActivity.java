package com.life.mm.framework.app.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.life.mm.common.config.GlobalConfig;
import com.life.mm.framework.R;
import com.life.mm.framework.app.base.activity.BaseActivity;
import com.life.mm.framework.app.register.contract.PasswordSetContract;
import com.life.mm.framework.app.register.model.PasswordSetModel;
import com.life.mm.framework.app.register.presenter.PasswordSetPresenter;
import com.life.mm.framework.utils.ToastUtil;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.app.register <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/23 16:07. <P>
 * Function: <P>
 * Modified: <P>
 */

public class PasswordSetActivity extends BaseActivity<PasswordSetPresenter> implements PasswordSetContract.View{
    private EditText password_set_first_edit = null;
    private EditText password_set_second_edit = null;
    private Button password_set_ok_btn = null;

    private String userName = null;
    private String source = null;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (R.id.password_set_ok_btn == v.getId()) {
                String pwd1 = password_set_first_edit.getText().toString();
                String pwd2 = password_set_second_edit.getText().toString();

                if (!TextUtils.isEmpty(pwd1) && !TextUtils.isEmpty(pwd2)) {
                    if (TextUtils.equals(pwd1, pwd2)) {
                        mPresenter.register(source, userName, pwd1);
                    } else {
                        ToastUtil.show(mContext, R.string.register_password_not_equal);
                    }
                } else {
                    ToastUtil.show(mContext, R.string.sign_password_hint);
                }
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_password_set;
    }

    @Override
    protected void initView() {
        setToolbarTitle(R.string.register_set_password);
        findWidget();
        setListener();
        getBundleData();
    }

    private void getBundleData() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            if (bundle.containsKey(GlobalConfig.source)) {
                source = bundle.getString(GlobalConfig.source);
            } else {
                throw new RuntimeException("应该设置数据来源----[source]");
            }
            if (bundle.containsKey(GlobalConfig.phoneNumber) && PhoneRegisterActivity.class.getSimpleName().equals(source)) {
                userName = bundle.getString(GlobalConfig.phoneNumber);
            } else if (bundle.containsKey(GlobalConfig.email) && EmailRegisterActivity.class.getSimpleName().equals(source)) {
                userName = bundle.getString(GlobalConfig.email);
            } else {
                throw new RuntimeException("应该设置电话号码或者邮箱----[phoneNumber | email]");
            }
        }
    }

    private void findWidget() {
        password_set_first_edit = (EditText) findViewById(R.id.password_set_first_edit);
        password_set_second_edit = (EditText) findViewById(R.id.password_set_second_edit);
        password_set_ok_btn = (Button) findViewById(R.id.password_set_ok_btn);
    }

    private void setListener() {
        password_set_ok_btn.setOnClickListener(onClickListener);
    }

    @Override
    protected void initPresenter() {
        mPresenter.attach(new PasswordSetModel(), this);
    }

    @Override
    public void onRegisterSuccess() {
        ToastUtil.show(mContext, R.string.register_success);
        finish();
        RegisterActivityManager.getInstance().onSuccess();
    }
}
