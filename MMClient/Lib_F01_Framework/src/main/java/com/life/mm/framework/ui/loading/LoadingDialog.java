package com.life.mm.framework.ui.loading;

import com.life.mm.common.config.GlobalConfig;
import com.life.mm.framework.app.MMApplication;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.common.utils <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/15 13:14. <P>
 * Function: <P>
 * Modified: <P>
 */

class LoadingDialog {

    private static LoadingDialog instance = null;
    private SweetAlertDialog loadingDlg = null;



    static public LoadingDialog getInstance() {
        if (null == instance) {
            synchronized (LoadingDialog.class) {
                if (null == instance) {
                    instance = new LoadingDialog();
                }
            }
        }
        return instance;
    }

    private LoadingDialog() {
    }

    public void showLoading() {
        if (null == loadingDlg) {
            loadingDlg = new SweetAlertDialog(MMApplication.getInstance().getAppManager().currentActivity(), SweetAlertDialog.PROGRESS_TYPE);
            loadingDlg.setContentText("");
            loadingDlg.setTitleText("");
        }
        if (!loadingDlg.isShowing()) {
            loadingDlg.show();
        }
    }

    public void dismiss() {
        if (null != loadingDlg && loadingDlg.isShowing()) {
            loadingDlg.dismiss();
        }
        loadingDlg = null;
    }

    public void showLoading(String content) {
        if (null == loadingDlg) {
            loadingDlg = new SweetAlertDialog(MMApplication.getInstance().getAppManager().currentActivity(), SweetAlertDialog.PROGRESS_TYPE);
        }
        loadingDlg.setContentText(content);
        showLoading();
    }

    public void showLoading(int status, String content) {
        StringBuilder stringBuffer = new StringBuilder(content);
        if (GlobalConfig.isDebug) {
            stringBuffer.append("(status = " + status + ")");
        }
        loadingDlg.setContentText(stringBuffer.toString());
        showLoading();
    }

    public void showErrorDlg(int status, String content) {
        SweetAlertDialog dlg = new SweetAlertDialog(MMApplication.getInstance().getAppManager().currentActivity(), SweetAlertDialog.ERROR_TYPE);
        StringBuilder stringBuffer = new StringBuilder(content);
        if (GlobalConfig.isDebug) {
            stringBuffer.append("(status = " + status + ")");
        }
        dlg.setTitleText("");
        dlg.setContentText(stringBuffer.toString());
        dlg.setCancelable(false);
        dlg.setCanceledOnTouchOutside(false);
        dlg.show();
    }

    public void showSuccessDlgOneBtn(String title, String content, final DlgConfirmListener confirmListener) {
        SweetAlertDialog dlg = new SweetAlertDialog(MMApplication.getInstance().getAppManager().currentActivity(), SweetAlertDialog.SUCCESS_TYPE);
        dlg.setTitleText(title);
        dlg.setContentText(content);
        dlg.setCancelable(false);
        dlg.setCanceledOnTouchOutside(false);
        dlg.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dismiss();
                if (null != confirmListener) {
                    confirmListener.onConfirm();
                }
            }
        });
        dlg.show();
    }
}
