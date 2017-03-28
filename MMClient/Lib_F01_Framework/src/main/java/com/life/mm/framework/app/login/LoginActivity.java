package com.life.mm.framework.app.login;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.life.mm.framework.R;
import com.life.mm.framework.app.ActivityHelper;
import com.life.mm.framework.app.base.activity.BaseActivity;
import com.life.mm.framework.app.login.contract.LoginContract;
import com.life.mm.framework.app.login.model.LoginModel;
import com.life.mm.framework.app.login.presenter.LoginPresenter;
import com.life.mm.framework.app.register.EmailRegisterActivity;
import com.life.mm.framework.app.register.PhoneRegisterActivity;
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



    private EditText etUsername;
    private EditText etPassword;
    private TextView tvForgetPassword;
    private Button btnSignIn;
    private ImageView ivQqLogin;
    private ImageView ivWechatLogin;
    private ImageView ivWeiboLogin;

    private RegisterResultListener registerResultListener = new RegisterResultListener() {
        @Override
        protected void onSuccess() {
            onLogin();//注册成功后相当于登陆成功所以直接调用登陆方法。
        }
    };
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (R.id.btn_sign_in == v.getId()) {
                doLogin();
            } else if (R.id.username_register_button == v.getId()) {
                ActivityHelper.goActivity(mContext, RegisterActivity.class, null);
                RegisterActivityManager.getInstance().registerListener(registerResultListener);
            } else if (R.id.iv_qq_login == v.getId()) {

            } else if (R.id.iv_wechat_login == v.getId()) {

            } else if (R.id.iv_weibo_login == v.getId()) {

            }
        }
    };

    private void doLogin() {
        etUsername.setError(null);
        etPassword.setError(null);

        final String usernameStr = etUsername.getText().toString();
        final String passwordStr = etPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(usernameStr)) {
            etUsername.setError(getString(R.string.error_field_required));
            focusView = etUsername;
            cancel = true;
        }

        if (TextUtils.isEmpty(passwordStr) || !TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mPresenter.doLogin(usernameStr, passwordStr);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_login;
    }

    @Override
    protected void initView() {
        setToolbarTitle(R.string.action_sign_in_short);
        findWidget();
        setListener();
    }

    private void findWidget() {
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        tvForgetPassword = (TextView) findViewById(R.id.tv_forget_password);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        ivQqLogin = (ImageView) findViewById(R.id.iv_qq_login);
        ivWechatLogin = (ImageView) findViewById(R.id.iv_wechat_login);
        ivWeiboLogin = (ImageView) findViewById(R.id.iv_weibo_login);
    }

    private void setListener() {
        btnSignIn.setOnClickListener(onClickListener);
        ivQqLogin.setOnClickListener(onClickListener);
        ivWechatLogin.setOnClickListener(onClickListener);
        ivWeiboLogin.setOnClickListener(onClickListener);
        tvForgetPassword.setOnClickListener(onClickListener);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.login_menu_phone_register == item.getItemId()) {
            ActivityHelper.goActivity(this, PhoneRegisterActivity.class, null);
            RegisterActivityManager.getInstance().registerListener(registerResultListener);
        } else if (R.id.login_menu_email_register == item.getItemId()) {
            ActivityHelper.goActivity(this, EmailRegisterActivity.class, null);
            RegisterActivityManager.getInstance().registerListener(registerResultListener);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RegisterActivityManager.getInstance().removeListener(registerResultListener);
    }
}
