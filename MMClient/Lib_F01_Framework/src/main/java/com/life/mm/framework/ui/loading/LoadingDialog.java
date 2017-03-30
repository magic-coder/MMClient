package com.life.mm.framework.ui.loading;

import android.content.DialogInterface;

import com.life.mm.common.config.GlobalConfig;
import com.life.mm.common.log.MMLogManager;
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

    private String TAG = LoadingDialog.class.getSimpleName();
    private static LoadingDialog instance = null;
    private SweetAlertDialog loadingDlg = null;
    private SweetAlertDialog errorDialog = null;



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
        MMLogManager.logD(TAG + ", showLoading");
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
        MMLogManager.logD(TAG + ", dismiss");
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

    public synchronized void showErrorDlg(int status, String content) {
        if (null != errorDialog && errorDialog.isShowing()) {
            return;
        }
        errorDialog = new SweetAlertDialog(MMApplication.getInstance().getAppManager().currentActivity(), SweetAlertDialog.ERROR_TYPE);
        StringBuilder stringBuffer = new StringBuilder(content);
        if (GlobalConfig.isDebug) {
            stringBuffer.append("(status = " + status + ")");
        }
        errorDialog.setTitleText("");
        errorDialog.setContentText(stringBuffer.toString());
        errorDialog.setCancelable(false);
        errorDialog.setCanceledOnTouchOutside(false);
        errorDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                errorDialog = null;
            }
        });
        errorDialog.show();
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
