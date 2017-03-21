package com.life.mm.framework.app.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.life.mm.framework.R;
import com.life.mm.framework.app.ActivityHelper;
import com.life.mm.framework.app.base.activity.BaseActivity;
import com.life.mm.framework.app.login.contract.LoginContract;
import com.life.mm.framework.app.login.model.LoginModel;
import com.life.mm.framework.app.login.presenter.LoginPresenter;
import com.life.mm.framework.app.register.RegisterActivity;
import com.life.mm.framework.app.register.RegisterActivityManager;
import com.life.mm.framework.app.register.RegisterResultListener;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.login <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/14 16:16. <P>
 * Function: <P>
 * Modified: <P>
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


    private AutoCompleteTextView username;
    private EditText password;
    private Button usernameLoginButton;
    private Button usernameRegisterButton;

    private RegisterResultListener registerResultListener = new RegisterResultListener() {
        @Override
        protected void onSuccess() {
            RegisterActivityManager.getInstance().removeListener(registerResultListener);
            onLogin();//注册成功后相当于登陆成功所以直接调用登陆方法。
        }
    };
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (R.id.username_login_button == v.getId()) {
                doLogin();
            } else if (R.id.username_register_button == v.getId()) {
                ActivityHelper.goActivity(mContext, RegisterActivity.class, null);
                RegisterActivityManager.getInstance().registerListener(registerResultListener);
            }
        }
    };

    private void doLogin() {
        username.setError(null);
        password.setError(null);

        final String usernameStr = username.getText().toString();
        final String passwordStr = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(usernameStr)) {
            username.setError(getString(R.string.error_field_required));
            focusView = username;
            cancel = true;
        }

        if (TextUtils.isEmpty(passwordStr) || !TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else  {
            mPresenter.doLogin(usernameStr, passwordStr);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        setToolbarTitle(R.string.action_sign_in_short);
        findWidget();
        setListener();
    }

    private void findWidget() {
        username = (AutoCompleteTextView) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        usernameLoginButton = (Button) findViewById(R.id.username_login_button);
        usernameRegisterButton = (Button) findViewById(R.id.username_register_button);
    }

    private void setListener() {
        usernameLoginButton.setOnClickListener(onClickListener);
        usernameRegisterButton.setOnClickListener(onClickListener);
    }
    @Override
    protected void initPresenter() {
        mPresenter.attach(new LoginModel(), this);
    }

    @Override
    public void onLogin() {
        finish();
    }
    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }
}
