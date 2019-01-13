package com.android.collect.data.other;

import com.android.collect.bean.LearnDataDetail;
import com.android.collect.data.BaseData;
import com.android.collect.library.common.FileConstant;

import java.util.ArrayList;

/**
 * 安全
 * Created by qinshunan on 2017/9/28.
 */
public class SaveData extends BaseData {
    @Override
    public ArrayList<LearnDataDetail> getData() {
        ArrayList<LearnDataDetail> data = new ArrayList<>();

        data.add(new LearnDataDetail("完整性校验", "应用完整性校验",
                "https://my.oschina.net/u/2323218/blog/406860"));
        data.add(new LearnDataDetail("注意事项", "记录需要注意的安全问题",
                FileConstant.APP_ASSET_DIR + "other/save/应用安全细节总结.html"));

        return data;
    }
}
