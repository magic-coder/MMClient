package com.life.mm.friends.contract;

import com.life.mm.framework.app.base.model.BaseModel;
import com.life.mm.framework.app.base.presenter.BasePresenter;
import com.life.mm.framework.app.base.view.BaseView;
import com.life.mm.map.contract.MapContract;

/**
 * Created by Thinkpad on 2017/2/28.
 */

public class FriendsContract {
    public interface Model extends BaseModel {}

    public interface View extends BaseView {}

    public static class Presenter<M extends MapContract.Model, V extends MapContract.View> extends BasePresenter {
        Presenter() {
            super();
        }
    }
}
