package com.life.mm.framework.app.register.contract;

import com.life.mm.framework.app.base.BaseCallBack;
import com.life.mm.framework.app.base.model.BaseModel;
import com.life.mm.framework.app.base.presenter.BasePresenter;
import com.life.mm.framework.app.base.view.BaseView;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.app.register.contract <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/24 10:16. <P>
 * Function: 邮箱注册模型类<P>
 * Modified: <P>
 */

public interface EmailRegisterContract {
    interface View extends BaseView {
        void onSendEmailVerifySuccess();
    }

    interface Model extends BaseModel {
        void sendEmailVerify(String emailAddress, final BaseCallBack callBack);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void sendEmailVerify(String emailAddress);
    }
}
