package com.life.mm.framework.ui.utils;

import android.app.Activity;

import com.life.mm.framework.bean.SelectOption;
import com.life.mm.framework.ui.widget.BottomPopSelectWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.framework.ui.utils <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/3/20 15:35. <P>
 * Function: <P>
 * Modified: <P>
 */

public class SlideChooserWindowUtil {
    /**
     * 选择结果监听器
     */
    public interface OptionSelectedListener {

        void onOptionSelected(SelectOption option);
    }
    /**
     * 跳转弹窗
     *
     * @param context
     * @param options
     * @param cancelStr
     * @param listener
     */

    public static void showBottomPopSelectDlg(Activity context, List<? extends SelectOption> options, String cancelStr,
                                              final OptionSelectedListener listener) {
        BottomPopSelectWindow bottomPopSelectWindow = new BottomPopSelectWindow(context, cancelStr);
        java.util.ArrayList<String> list = new ArrayList<String>();
        ArrayList<Object> extra = new ArrayList<Object>();
        for (SelectOption ntOption : options) {
            list.add(ntOption.getLabel());
            extra.add(ntOption.getValue());
        }
        bottomPopSelectWindow.setData(list, extra);
        bottomPopSelectWindow.setItemSelectedListener(new BottomPopSelectWindow.OnBottomWindowItemSelectedListener() {
            @Override
            public void onSelected(int position, String label, Object value) {
                listener.onOptionSelected(new SelectOption(label, value));
            }
        });
        bottomPopSelectWindow.show();
    }
}
