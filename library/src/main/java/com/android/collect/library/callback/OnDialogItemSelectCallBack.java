package com.android.collect.library.callback;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by anzai on 2017/4/19.
 */

public interface OnDialogItemSelectCallBack {

    void onItemClick(AdapterView<?> parent, View view, int position, long id);

}
