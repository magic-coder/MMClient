package com.life.mm.app.contract;


import com.life.mm.framework.app.base.model.BaseModel;
import com.life.mm.framework.app.base.presenter.BasePresenter;
import com.life.mm.framework.app.base.view.BaseView;

/**
 * Created by Thinkpad on 2017/2/27.
 */

public interface MainContract {

    interface View extends BaseView {
        void onLoadData();
    }

    interface Model extends BaseModel {
        void loadData();
    }

    abstract class Presenter<M extends Model, V extends View> extends BasePresenter {

        public Presenter() {
            super();
        }
    }
}
