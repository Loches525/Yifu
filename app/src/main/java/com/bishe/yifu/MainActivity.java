package com.bishe.yifu;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.yifu.db.DBService;
import com.bishe.yifu.entity.T;
import com.bishe.yifu.entity.Yifu;
import com.bishe.yifu.fragment.M1Fragment;
import com.bishe.yifu.fragment.M2Fragment;
import com.bishe.yifu.fragment.M3Fragment;
import com.bishe.yifu.image.SetImageUtil;

import java.util.List;

/**
 * 主界面
 *
 * @author Administrator
 */
public class MainActivity extends FragmentActivity implements OnClickListener {

    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;


    M1Fragment m1;
    M2Fragment m2;
    M3Fragment m3;
    DBService service;
    List<Yifu> list;


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        initialView();


    }

    /**
     * 初始化控件
     */
    private void initialView() {
        service = new DBService(this);

        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);


        tv_1.setOnClickListener(this);
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);


        selection(0);
    }

    /**
     * 初始化图片,文字
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initialImage() {
        tv_1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.mipmap.home_gray, 0, 0);
        tv_1.setTextColor(getResources().getColor(R.color.tab_text_bg));
        tv_2.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.add_gray, 0, 0);
        tv_2.setTextColor(getResources().getColor(R.color.tab_text_bg));
        tv_3.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.mipmap.closet_gray, 0, 0);
        tv_3.setTextColor(getResources().getColor(R.color.tab_text_bg));

    }

    /**
     * 点击不同的按钮做出不同的处理
     */
    private void selection(int index) {

        initialImage();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                ft.hide(f);
            }
        }

        Fragment fragment;

        switch (index) {

            case 0:

                tv_1.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.home_green, 0, 0);
                tv_1.setTextColor(getResources().getColor(R.color.colorAccent));
                fragment = getSupportFragmentManager().findFragmentByTag("tv_1");
                if (fragment == null) {
                    m1 = new M1Fragment();
                    ft.add(R.id.fg_content, m1, "tv_1");
                } else {
                   // m1.updateUi();
                    ft.show(fragment);
                }
                break;
            case 1:

                tv_2.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.add_green, 0, 0);
                tv_2.setTextColor(tv_2.getResources().getColor(R.color.colorAccent));
                fragment = getSupportFragmentManager().findFragmentByTag("tv_2");
                if (fragment == null) {
                    m2 = new M2Fragment();
                    ft.add(R.id.fg_content, m2, "tv_2");
                } else {
                    ft.show(fragment);
                }
                break;
            case 2:

                tv_3.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.closet_green, 0, 0);
                tv_3.setTextColor(getResources().getColor(R.color.colorAccent));
                fragment = getSupportFragmentManager().findFragmentByTag("tv_3");
                if (fragment == null) {
                    m3 = new M3Fragment();
                    ft.add(R.id.fg_content, m3, "tv_3");
                } else {
                    m3.updateUI();
                    ft.show(fragment);
                }
                break;

        }
        ft.commit();
    }

    int i = 0;

    /**
     * 监听按钮
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_1:
                selection(0);
                break;
            case R.id.tv_2:
                selection(1);
                break;
            case R.id.tv_3:
                selection(2);
                break;


        }
    }


    /**
     * 拍照和图库选完后结果在此处理
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == m2.mPhotoSelectedHelper.TAKE_PHOTO) {
            if (!(resultCode == RESULT_OK)) {
                return;
            }

            if (data != null) {
                m2.mPhotoSelectedHelper.cropImageUri(data.getData(), 500, 500,
                        "cy");
            } else {
                m2.mPhotoSelectedHelper.cropImageUri(
                        m2.mPhotoSelectedHelper.getCaptureUri(), 500, 500,
                        "cy");
            }

        } else if (requestCode == m2.mPhotoSelectedHelper.PHOTO_CROP) {
            if (!(resultCode == RESULT_OK)) {
                return;
            }
            final String cropPath = m2.mPhotoSelectedHelper.getCropPath();
            if (cropPath != null) {
                T.Show(MainActivity.this, "图片添加成功");
                list = service.search();
                String str = "";
                if (list.get((m2.index - 1)).getImage().equals("")) {
                    str = cropPath;
                } else {
                    str = list.get((m2.index - 1)).getImage() + ";" + cropPath;
                }
                if (service.update(new Yifu(m2.index, "", str))) {
                    T.Show(MainActivity.this, "添加成功");
                } else {
                    T.Show(MainActivity.this, "添加失败");
                }

            }

        } else if (requestCode == m2.mPhotoSelectedHelper.PIC_PHOTO) {
            if (data == null) {
                return;
            } else {
                Uri uri = data.getData();
                if (uri != null) {
                    String path = SetImageUtil.getPath(MainActivity.this, uri);
                    if (path != null) {
                        list = service.search();
                        String str = "";
                        if (list.get((m2.index - 1)).getImage().equals("")) {
                            str = path;
                        } else {
                            str = list.get((m2.index - 1)).getImage() + ";" + path;
                        }
                        if (service.update(new Yifu(m2.index, "", str))) {
                            T.Show(MainActivity.this, "添加成功");
                        } else {
                            T.Show(MainActivity.this, "添加失败");
                        }
                    }

                }
            }
        }

    }

    long newTime;

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - newTime > 2000) {
            newTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
        } else {

            finish();

        }
    }

}
