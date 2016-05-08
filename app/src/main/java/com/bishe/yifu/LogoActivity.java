package com.bishe.yifu;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.bishe.yifu.db.DBService;
import com.bishe.yifu.entity.Yifu;
import com.bishe.yifu.util.SharedPreferencesUtil;

public class LogoActivity extends Activity {

    DBService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_logo);
        service = new DBService(this);
        //"长袖上衣", "短袖上衣", "外套", "毛衣", "长裤", "短裤", "长裙", "短裙"
        if (SharedPreferencesUtil.getBoolean(this, "yf", "yf", true)) {//第一次启动初始化一下数据库内容
            service.save(new Yifu(1, "长袖上衣", ""));
            service.save(new Yifu(2, "短袖上衣", ""));
            service.save(new Yifu(3, "外套", ""));
            service.save(new Yifu(4, "毛衣", ""));
            service.save(new Yifu(5, "长裤", ""));
            service.save(new Yifu(6, "短裤", ""));
            service.save(new Yifu(7, "长裙", ""));
            service.save(new Yifu(8, "短裙", ""));
            SharedPreferencesUtil.setBoolean(this, "yf", "yf", false);
        }

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);


                    startActivity(new Intent(LogoActivity.this, MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
