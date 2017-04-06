package com.life.mm.framework.ui.waveView;

import android.text.TextUtils;

import com.life.mm.framework.user.DevUser;

import java.util.Comparator;

public class LetterComparator implements Comparator<DevUser> {

    @Override
    public int compare(DevUser left, DevUser right) {
        if (left == null && right == null) {
            return 0;
        } else if (left != null && right != null) {

            String lhsSortLetters = left.getNickNamePy();
            String rhsSortLetters = right.getNickNamePy();
            if (TextUtils.isEmpty(lhsSortLetters) && TextUtils.isEmpty(rhsSortLetters)) {
                return 0;
            } else if (!TextUtils.isEmpty(lhsSortLetters) && !TextUtils.isEmpty(rhsSortLetters)) {
                return lhsSortLetters.compareTo(rhsSortLetters);
            } else if (TextUtils.isEmpty(lhsSortLetters)) {
                return -1;
            } else {//null == rhsSortLetters
                return 1;
            }
        } else if (null == left) {
            return -1;
        } else {//null == right
            return 1;
        }
    }
}