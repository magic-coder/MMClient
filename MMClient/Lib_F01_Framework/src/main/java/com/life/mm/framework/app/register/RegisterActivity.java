package com.life.mm.framework.app.register;

import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.life.mm.framework.R;
import com.life.mm.framework.app.base.activity.BaseActivity;
import com.life.mm.framework.app.register.contract.RegisterContract;
import com.life.mm.framework.app.register.model.RegisterModel;
import com.life.mm.framework.app.register.presenter.RegisterPresenter;
import com.life.mm.framework.utils.ToastUtil;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.register <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/15 10:32. <P>
 * Function: 用户名密码注册<P>
 * Modified: <P>
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    private AutoCompleteTextView username;
    private EditText password;
    private Button usernameRegisterButton;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (R.id.username_register_button == v.getId()) {
                register();
            }
        }
    };

    private void register() {
        username.setError(null);
        password.setError(null);

        String usernameStr = username.getText().toString();
        String passwordStr = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }

        if (TextUtils.isEmpty(usernameStr)) {
            username.setError(getString(R.string.error_field_required));
            focusView = username;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mPresenter.register(usernameStr, passwordStr);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        setToolbarTitle(R.string.register);
        findWidget();
        setListener();
    }

    private void findWidget() {
        username = (AutoCompleteTextView) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        usernameRegisterButton = (Button) findViewById(R.id.username_register_button);
    }

    private void setListener() {
        usernameRegisterButton.setOnClickListener(onClickListener);
    }

    @Override
    protected void initPresenter() {
        mPresenter.attach(new RegisterModel(), this);
    }

    @Override
    public void onRegister() {
        ToastUtil.show(mContext, mResources.getString(R.string.register_success));
        RegisterActivityManager.getInstance().onSuccess();
        finish();
        /*Intent intent = new Intent(mContext, MMMainActivity.class);
        startActivity(intent);*/
    }


    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }
}
