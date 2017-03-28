package com.life.mm.framework.app.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.life.mm.common.config.GlobalConfig;
import com.life.mm.framework.R;
import com.life.mm.framework.app.ActivityHelper;
import com.life.mm.framework.app.base.activity.BaseActivity;
import com.life.mm.framework.app.register.contract.PhoneRegisterContract;
import com.life.mm.framework.app.register.model.PhoneRegisterModel;
import com.life.mm.framework.app.register.presenter.PhoneRegisterPresenter;
import com.life.mm.framework.utils.MMUtils;
import com.life.mm.framework.utils.ToastUtil;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.app.register <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/23 14:51. <P>
 * Function: 手机号码注册<P>
 * Modified: <P>
 */

public class PhoneRegisterActivity extends BaseActivity<PhoneRegisterPresenter> implements PhoneRegisterContract.View{

    private EditText phone_register_number = null;
    private EditText phone_register_verify_code = null;
    private Button phone_register_request_verify_code = null;
    private Button phone_register_next = null;

    private RegisterResultListener registerResultListener = new RegisterResultListener() {
        @Override
        protected void onSuccess() {
            finish();
        }
    };
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (R.id.phone_register_request_verify_code == v.getId()) {
                String phoneNumber = phone_register_number.getText().toString();
                if (MMUtils.isValidPhoneNumber(phoneNumber)) {
                    //mPresenter.getVerifyCode(phoneNumber);
                    go();
                } else {
                    ToastUtil.show(mContext, R.string.register_phone_number_error);
                }
            } else if (R.id.phone_register_next == v.getId()) {
                String phoneNumber = phone_register_number.getText().toString();
                if (!MMUtils.isValidPhoneNumber(phoneNumber)) {
                    ToastUtil.show(mContext, R.string.register_phone_number_error);
                    return;
                }
                go();
                /*String smsCode = phone_register_verify_code.getText().toString();
                if (!TextUtils.isEmpty(smsCode)) {
                    mPresenter.verifyCode(phoneNumber, smsCode);
                } else {
                    ToastUtil.show(mContext, R.string.register_phone_verify_code_error);
                }*/
            }
        }
    };
    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_register;
    }

    @Override
    protected void initView() {
        setToolbarTitle(R.string.sign_menu_phone_register);
        findWidget();
        setListener();
    }

    private void findWidget() {
        phone_register_number = (EditText) findViewById(R.id.phone_register_number);
        phone_register_verify_code = (EditText) findViewById(R.id.phone_register_verify_code);
        phone_register_request_verify_code = (Button) findViewById(R.id.phone_register_request_verify_code);
        phone_register_next = (Button) findViewById(R.id.phone_register_next);
    }

    private void setListener() {
        phone_register_request_verify_code.setOnClickListener(onClickListener);
        phone_register_next.setOnClickListener(onClickListener);
    }

    @Override
    protected void initPresenter() {
        mPresenter.attach(new PhoneRegisterModel(), this);
    }

    @Override
    public void onGetVerifyCode() {
        ToastUtil.show(mContext, R.string.register_phone_request_code_success);
    }

    @Override
    public void onVerifySuccess() {
        finish();
        RegisterActivityManager.getInstance().onSuccess();
    }

    private void go() {
        Bundle bundle = new Bundle();
        bundle.putString(GlobalConfig.phoneNumber, phone_register_number.getText().toString());
        bundle.putString(GlobalConfig.source, PhoneRegisterActivity.class.getSimpleName());
        ActivityHelper.goActivity(this, PasswordSetActivity.class, bundle);
        RegisterActivityManager.getInstance().registerListener(registerResultListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RegisterActivityManager.getInstance().removeListener(registerResultListener);
    }
}
