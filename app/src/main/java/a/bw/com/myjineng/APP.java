package a.bw.com.myjineng;

import android.app.Application;

import org.xutils.x;

/**
 * Created by admin on 2017/8/25.
 */

public class APP extends Application {
    @Override
    public void onCreate() {
        x.Ext.init(this);

    }
}
