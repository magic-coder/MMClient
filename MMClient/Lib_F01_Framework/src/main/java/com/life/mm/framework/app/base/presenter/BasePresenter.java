package com.life.mm.framework.app.base.presenter;


import com.life.mm.framework.app.base.BaseObject;
import com.life.mm.framework.app.base.model.BaseModel;
import com.life.mm.framework.app.base.view.BaseView;

/**
 * Created by Thinkpad on 2017/2/27.
 */

public abstract class BasePresenter<M extends BaseModel, V extends BaseView> implements BaseObject {

    protected M mModel;
    protected V mView;

    public void attach(M model, V view) {
        this.mModel = model;
        this.mView = view;
    }

    public void detach() {
        this.mModel = null;
        this.mView = null;
    }
}
