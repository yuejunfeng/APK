package a.bw.com.myjineng;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private boolean flag;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void gengxin(View v){
        String url = "http://down11.zol.com.cn/suyan/lulutong3.6.5g.apk";
        String path = Environment.getExternalStorageDirectory().getPath() + "/teme1/myapk.apk";
        File file = new File(path);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdir();
        }

        RequestParams params = new RequestParams(url);
        params.setAutoRename(false);
        params.setAutoResume(true);

        //设置保存路径
        params.setSaveFilePath(path);
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                Toast.makeText(MainActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                installDownloadApk(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(MainActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                cancleProgressDialog();
            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                showProgressDialog();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                int progress = (int) (current * 100/ total );
                if (progress >= 0 && progress <= 100) {
                    updataProgressDialog(progress);
                }
            }
        });
    }
    //安卓开发 apk安装
    private void installDownloadApk(File result) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
        startActivity(intent);
    }
    private void cancleProgressDialog() {
        if (progressDialog == null) {
            return;
        }
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void updataProgressDialog(int progress) {
        if (progressDialog == null) {
            return;
        }
        progressDialog.setProgress(progress);
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);

        //设置progressDialog显示样式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("我正在下载东西");
        progressDialog.setTitle("请等待");
        progressDialog.setProgress(0);
        progressDialog.show();
    }
}
