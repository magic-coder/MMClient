package com.life.mm.framework.ui.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.life.mm.framework.R;

import java.util.ArrayList;
import java.util.List;

public class BottomPopSelectWindow extends Dialog {

    private Activity context = null;
    private String title = null;
    private List<String> listKeys = new ArrayList<>();
    private List<Object> listValues = new ArrayList<>();
    private ListView bottom_pop_select_list_view = null;
    private Button bottom_pop_select_cancel_btn = null;

    private OnBottomWindowItemSelectedListener itemSelectedListener = null;
    private OnBottomWindowCancel onBottomWindowCancel = null;

    public void setData(List<String> listLabel, List<Object> listData) {
        this.listKeys = listLabel;
        this.listValues = listData;
        initAdapter(context);
    }
    public void setItemSelectedListener(OnBottomWindowItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    public void setOnBottomWindowCancel(OnBottomWindowCancel onBottomWindowCancel) {
        this.onBottomWindowCancel = onBottomWindowCancel;
    }

    @SuppressWarnings("deprecation")
    public BottomPopSelectWindow(Activity context, String cancelStr) {
        super(context, R.style.transparentFrameWindowStyle);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 加载popupWindow的布局文件
        final View contentView = inflater.inflate(R.layout.bottom_pop_select_window_layout, null);
        bottom_pop_select_list_view = (ListView) contentView.findViewById(R.id.bottom_pop_select_list_view);
        bottom_pop_select_cancel_btn = (Button) contentView.findViewById(R.id.bottom_pop_select_cancel_btn);

        bottom_pop_select_cancel_btn.setText(cancelStr);
        bottom_pop_select_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (null != onBottomWindowCancel) {
                    onBottomWindowCancel.onCancel();
                }
            }
        });

        this.setContentView(contentView);
        initView(context);
        initAdapter(context);
    }

    private void initAdapter(Activity activity) {
        PopSelectWindowItemAdapter adapter = new PopSelectWindowItemAdapter(activity, listKeys, listValues);
        bottom_pop_select_list_view.setAdapter(adapter);
        setListener();
    }

    private void setListener() {
        bottom_pop_select_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                if (null != itemSelectedListener) {
                    itemSelectedListener.onSelected(position, listKeys.get(position), listValues.get(position));
                }
            }
        });
    }

    private void initView(Context context) {
        Window window = getWindow();
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        onWindowAttributesChanged(wl);
        setCanceledOnTouchOutside(true);
    }

    class PopSelectWindowItemAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater = null;
        private List<String> listLabel = null;
        private List<Object> listData = null;
        public PopSelectWindowItemAdapter(Activity activity, List<String> listLabel, List<Object> listData) {
            super();
            layoutInflater = LayoutInflater.from(activity);
            this.listLabel = listLabel;
            this.listData = listData;
        }

        @Override
        public int getCount() {
            return this.listLabel.size();
        }

        @Override
        public Object getItem(int position) {
            return this.listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (null == convertView) {
                convertView = layoutInflater.inflate(R.layout.bottom_pop_select_window_list_item, parent, false);
                viewHolder = new ViewHolder((TextView) convertView.findViewById(R.id.bottom_pop_select_list_item_tips));
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.bottom_pop_select_list_item_tips.setText(listLabel.get(position));

            return convertView;
        }

        class ViewHolder {
            public TextView bottom_pop_select_list_item_tips = null;


            ViewHolder(TextView tips) {
                this.bottom_pop_select_list_item_tips = tips;
            }
        }
    }

    public interface OnBottomWindowItemSelectedListener {
        void onSelected(int position, String label, Object value);
    }

    public interface OnBottomWindowCancel {
        void onCancel();
    }
}