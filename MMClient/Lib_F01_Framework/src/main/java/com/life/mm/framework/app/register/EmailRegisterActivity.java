package com.life.mm.framework.app.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.life.mm.common.config.GlobalConfig;
import com.life.mm.framework.R;
import com.life.mm.framework.app.ActivityHelper;
import com.life.mm.framework.app.base.activity.BaseActivity;
import com.life.mm.framework.app.register.contract.EmailRegisterContract;
import com.life.mm.framework.app.register.model.EmailRegisterModel;
import com.life.mm.framework.app.register.presenter.EmailRegisterPresenter;
import com.life.mm.framework.ui.loading.DlgConfirmListener;
import com.life.mm.framework.ui.loading.LoadingDialogUtil;
import com.life.mm.framework.utils.ToastUtil;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.app.register <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/23 14:52. <P>
 * Function: 邮箱注册入口<P>
 * Modified: <P>
 */

public class EmailRegisterActivity extends BaseActivity<EmailRegisterPresenter> implements EmailRegisterContract.View{

    private EditText email_register_account = null;
    private Button email_register_verify_btn = null;
    private Button email_register_next = null;


    private String emailAddress = null;

    private RegisterResultListener registerResultListener = new RegisterResultListener() {
        @Override
        protected void onSuccess() {
            finish();
        }
    };


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (R.id.email_register_verify_btn == v.getId()) {
                emailAddress = email_register_account.getText().toString();
                if (!TextUtils.isEmpty(emailAddress) && emailAddress.contains("@")) {
                    //mPresenter.sendEmailVerify(emailAddress);
                    go();
                } else {
                    ToastUtil.show(mContext, R.string.register_email_account_error_tips);
                }
            } else if (R.id.email_register_next == v.getId()) {
                emailAddress = email_register_account.getText().toString();
                if (!TextUtils.isEmpty(emailAddress) && emailAddress.contains("@")) {
                    //mPresenter.sendEmailVerify(emailAddress);
                    go();
                } else {
                    ToastUtil.show(mContext, R.string.register_email_account_error_tips);
                }
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_email_register;
    }

    @Override
    protected void initView() {
        setToolbarTitle(R.string.sign_menu_email_register);
        findWidget();
        setListener();
    }

    private void findWidget() {
        email_register_account = (EditText) findViewById(R.id.email_register_account);
        email_register_verify_btn = (Button) findViewById(R.id.email_register_verify_btn);
        email_register_next = (Button) findViewById(R.id.email_register_next);
    }

    private void setListener() {
        email_register_verify_btn.setOnClickListener(onClickListener);
        email_register_next.setOnClickListener(onClickListener);
    }

    @Override
    protected void initPresenter() {
        mPresenter.attach(new EmailRegisterModel(), this);
    }

    @Override
    public void onSendEmailVerifySuccess() {
        String tips = String.format(mResources.getString(R.string.register_email_send_verify_success), emailAddress);
        LoadingDialogUtil.showSuccessDlgOneBtn("", tips, new DlgConfirmListener() {
            @Override
            public void onConfirm() {
                go();
            }
        });
    }

    private void go() {
        Bundle bundle = new Bundle();
        bundle.putString(GlobalConfig.source, EmailRegisterActivity.class.getSimpleName());
        bundle.putString(GlobalConfig.email, emailAddress);
        ActivityHelper.goActivity(mContext, PasswordSetActivity.class, bundle);
        RegisterActivityManager.getInstance().registerListener(registerResultListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RegisterActivityManager.getInstance().removeListener(registerResultListener);
    }
}
