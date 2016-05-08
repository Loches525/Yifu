package com.bishe.yifu.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bishe.yifu.R;
import com.bishe.yifu.db.DBService;
import com.bishe.yifu.entity.T;
import com.bishe.yifu.entity.Yifu;
import com.bishe.yifu.image.PhotoSelectedHelper;

public class M2Fragment extends Fragment {

    private View view;
    ListView lv;
    public PhotoSelectedHelper mPhotoSelectedHelper;
    public int index = -1;
    DBService service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_m2, container, false);
        service = new DBService(getActivity());
        mPhotoSelectedHelper = new PhotoSelectedHelper(getActivity());
        lv = (ListView) view.findViewById(R.id.listView);
        final String[] str = new String[]{"长袖上衣", "短袖上衣", "外套", "毛衣", "长裤", "短裤", "长裙", "短裙"};
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, str);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                // 选择添加图片方式
                AlertDialog.Builder builder = new AlertDialog.Builder(// 实例化了一个对话框
                        getActivity()).setTitle("添加图片").setItems(
                        new String[]{"拍照", "图库", "清空"},
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                index = (position + 1);
                                if (which == 0) {

                                    mPhotoSelectedHelper// 拍照方式
                                            .imageSelection(
                                                    "cy",
                                                    "take");

                                } else if (which == 1) {

                                    mPhotoSelectedHelper
                                            .imageSelection(
                                                    "cy",
                                                    "pic");

                                } else if (which == 2) {
                                    AlertDialog.Builder d = new AlertDialog.Builder(getActivity()).setMessage("确定清空？");
                                    d.setCancelable(false);
                                    d.setPositiveButton("确定",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    service.update(new Yifu(index, "", ""));
                                                    T.Show(getActivity(), "清空成功");
                                                }
                                            });
                                    d.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    d.show();


                                }

                            }

                        });
                builder.show();


            }
        });


        return view;

    }


}
