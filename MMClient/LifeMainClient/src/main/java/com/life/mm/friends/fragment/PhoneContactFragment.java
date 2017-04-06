package com.life.mm.friends.fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.life.mm.R;
import com.life.mm.common.utils.ChineseUtils;
import com.life.mm.framework.app.base.fragment.BaseFragment;
import com.life.mm.framework.ui.fancyindexer.FancyIndexer;
import com.life.mm.framework.user.ContactInfo;
import com.life.mm.framework.utils.MMUtils;
import com.life.mm.friends.adapter.PhoneContactAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;

/**
 * ProjectName:MMClient <P>
 * PackageName: com.life.mm.friends.fragment <p>
 * ClassName: ${CLASS_NAME}<P>
 * Created by zfang on 2017/4/5 14:54. <P>
 * Function: <P>
 * Modified: <P>
 */

public class PhoneContactFragment extends BaseFragment {

    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.bar)
    FancyIndexer bar;
    @Bind(R.id.tv_index_center)
    TextView tvIndexCenter;
    private List<ContactInfo> contactInfoList = null;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_phone_contact;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(View rootView) {
        bar.setOnTouchLetterChangedListener(new FancyIndexer.OnTouchLetterChangedListener() {

            @Override
            public void onTouchLetterChanged(String letter) {
                System.out.println("letter: " + letter);
                // 从集合中查找第一个拼音首字母为letter的索引, 进行跳转
                if (MMUtils.isAvaliableList(contactInfoList)) {
                    for (int i = 0; i < contactInfoList.size(); i++) {
                        ContactInfo goodMan = contactInfoList.get(i);
                        String s = goodMan.getNamePY().charAt(0) + "";
                        if (TextUtils.equals(s, letter)) {// 匹配成功, 中断循环, 跳转到i位置
                            lvContent.setSelection(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void doLazyLoad() {
        new LoadContactTask().execute();
    }

    class LoadContactTask extends AsyncTask<Void, Integer, Boolean> {

        public LoadContactTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                Collections.sort(contactInfoList);
                lvContent.setAdapter(new PhoneContactAdapter(contactInfoList, mContext));
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                contactInfoList = getPhoneContact();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        private List<ContactInfo> getPhoneContact() {
            List<ContactInfo> contactInfoList = new ArrayList<>();
            ContentResolver resolver = mContext.getContentResolver();
            Cursor cursor = null;
            String[] projection = new String[]{
                    Phone.DISPLAY_NAME,
                    Phone.NUMBER,
                    Phone.PHOTO_URI,
                    Phone.SORT_KEY_PRIMARY,
                    Phone.PHOTO_THUMBNAIL_URI,
            };
            try {
                cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);  // 获取手机联系人
                if (null != cursor) {
                    int indexPeopleName = cursor.getColumnIndex(projection[0]);  // people name
                    int indexPhoneNum = cursor.getColumnIndex(projection[1]);
                    int indexPhotoUri = cursor.getColumnIndex(projection[2]);    // people head uri
                    int sortKey = cursor.getColumnIndex(projection[3]);
                    int photoThumbnailUri = cursor.getColumnIndex(projection[4]);

                    while (cursor.moveToNext()) {
                        ContactInfo info = new ContactInfo();

                        String strPeopleName = cursor.getString(indexPeopleName);
                        String strPhoneNum = cursor.getString(indexPhoneNum);
                        String photoUri = cursor.getString(indexPhotoUri);
                        String strSortKey = cursor.getString(sortKey);
                        String strPhotoThumbnailUri = cursor.getString(photoThumbnailUri);
                        info.setName(strPeopleName);
                        info.setNumber(strPhoneNum.replaceAll("-", ""));
                        info.setNamePY(ChineseUtils.getPinYinHead(strPeopleName));
                        info.setHeadImageUrl(photoUri);
                        contactInfoList.add(info);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != cursor) {
                    cursor.close();
                }
            }
            return contactInfoList;
        }

        public byte[] getPhoto(ContentResolver resolver, String people_id) {
            String photo_id = null;
            String selection1 = ContactsContract.Contacts._ID + " = " + people_id;
            Cursor cur1 = resolver.query(
                    ContactsContract.Contacts.CONTENT_URI, null, selection1, null, null);
            if (cur1.getCount() > 0) {
                cur1.moveToFirst();
                photo_id = cur1.getString(cur1.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
//System.out.println("photo_id:" + photo_id);
            }
            String[] projection = new String[]
                    {
                            ContactsContract.Data.DATA15
                    };
            String selection = ContactsContract.Data._ID + " = " + photo_id;
            Cursor cur = resolver.query(
                    ContactsContract.Data.CONTENT_URI, projection, selection, null, null);
            cur.moveToFirst();
            byte[] contactIcon = cur.getBlob(0);
            System.out.println("conTactIcon:" + contactIcon);
            if (contactIcon == null) {
                return null;
            } else {
                return contactIcon;
            }
        }
    }
}
