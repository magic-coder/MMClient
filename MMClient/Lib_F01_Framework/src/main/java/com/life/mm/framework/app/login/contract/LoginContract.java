package com.life.mm.framework.app.login.contract;


import com.life.mm.framework.app.base.BaseCallBack;
import com.life.mm.framework.app.base.model.BaseModel;
import com.life.mm.framework.app.base.presenter.BasePresenter;
import com.life.mm.framework.app.base.view.BaseView;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.login.contract <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/14 16:18. <P>
 * Function: <P>
 * Modified: <P>
 */

public interface LoginContract {

    interface View extends BaseView {
        /**
         * 已经调用登陆接口成功，这里执行页面跳转
         */
        void onLogin();
    }

    interface Model extends BaseModel {
        void doLogin(String userName, String pwd, final BaseCallBack callBack);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void doLogin(String userName, String pwd);
    }
}
