package com.life.mm.eventbus;

import android.os.Bundle;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.eventbus <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/29 10:13. <P>
 * Function: <P>
 * Modified: <P>
 */

public class SimpleNotifyEvent {
    private String source = null;
    private String msg = null;
    private Bundle data = null;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Bundle getData() {
        return data;
    }

    public void setData(Bundle data) {
        this.data = data;
    }

    public static class Builder {
        private String source = null;
        private String msg = null;
        private Bundle data = null;

        public Builder setSource(String souce) {
            this.source = souce;
            return this;
        }

        public Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder setData(Bundle bundle) {
            this.data = bundle;
            return this;
        }

        public SimpleNotifyEvent build() {
            SimpleNotifyEvent event = new SimpleNotifyEvent();
            event.setSource(this.source);
            event.setMsg(this.msg);
            event.setData(this.data);
            return event;
        }
    }
}
