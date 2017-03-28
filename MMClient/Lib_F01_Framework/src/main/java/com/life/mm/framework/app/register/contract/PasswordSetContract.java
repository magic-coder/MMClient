package com.life.mm.framework.app.register.contract;

import com.life.mm.framework.app.base.BaseCallBack;
import com.life.mm.framework.app.base.model.BaseModel;
import com.life.mm.framework.app.base.presenter.BasePresenter;
import com.life.mm.framework.app.base.view.BaseView;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.app.register.contract <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/23 16:37. <P>
 * Function: <P>
 * Modified: <P>
 */

public interface PasswordSetContract {
    interface View extends BaseView {
        void onRegisterSuccess();
    }

    interface Model extends BaseModel {
        void register(String source, String userName, String pwd, final BaseCallBack callBack);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void register(String source, String userName, String pwd);
    }
}
