package com.life.mm.map.contract;

import com.life.mm.framework.app.base.model.BaseModel;
import com.life.mm.framework.app.base.presenter.BasePresenter;
import com.life.mm.framework.app.base.view.BaseView;

/**
 * Created by Thinkpad on 2017/2/28.
 */

public class MapContract {

    public interface Model extends BaseModel {}

    public interface View extends BaseView {}

    public static class Presenter<M extends Model, V extends  View> extends BasePresenter {
        Presenter() {
            super();
        }
    }
}
