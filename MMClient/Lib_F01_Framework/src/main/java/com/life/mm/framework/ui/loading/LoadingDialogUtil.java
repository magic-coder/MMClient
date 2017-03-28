package com.life.mm.framework.ui.loading;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.ui.loading <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/15 14:06. <P>
 * Function: <P>
 * Modified: <P>
 */

public class LoadingDialogUtil {

    public static void showLoading() {
        LoadingDialog.getInstance().showLoading();
    }

    public static void showLoading(String content) {
        LoadingDialog.getInstance().showLoading(content);
    }


    public static void showLoading(int status, String content) {
        LoadingDialog.getInstance().showLoading(status, content);
    }

    public static void showErrorDlg(int status, String content) {
        LoadingDialog.getInstance().showErrorDlg(status, content);
    }

    public static void showSuccessDlgOneBtn(String title, String content, final DlgConfirmListener confirmListener) {
        LoadingDialog.getInstance().showSuccessDlgOneBtn(title, content, confirmListener);
    }


    public static void dismiss() {
        LoadingDialog.getInstance().dismiss();
    }
}
