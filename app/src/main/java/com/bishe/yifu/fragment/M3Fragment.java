package com.bishe.yifu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bishe.yifu.R;
import com.bishe.yifu.ShowImageActivity;
import com.bishe.yifu.db.DBService;
import com.bishe.yifu.entity.Yifu;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class M3Fragment extends Fragment {

    private View view;
    ListView lv1 = null;
    ListView lv2 = null;

    DBService service;
    ArrayList<Yifu> list;
    String[] lists;
    Adapter1 adapter1;
    Adapter2 adapter2;
    //    TextView tv;
//    ImageView iv;
//    PhotoViewAttacher mAttacher;
    int index = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_m3, container, false);
        service = new DBService(getActivity());

        lv1 = (ListView) view.findViewById(R.id.listView1);
        lv2 = (ListView) view.findViewById(R.id.listView2);

        list = service.search();
        adapter1 = new Adapter1();

        lv1.setAdapter(adapter1);
        lv1.setOnItemClickListener(new MyItemClick());
        lv2.setOnItemClickListener(new MyItemClick2());

        return view;

    }

    public void updateUI() {
        list = service.search();
        if(index!=-1){
            lists = list.get(index).getImage().split(";");
            adapter2.notifyDataSetChanged();
        }

        adapter1.notifyDataSetChanged();


    }

    public void updateUI(int position) {
//        if (position == index) {
//            if (list.get(position).getImage().equals("暂无图片")) {
//                tv.setVisibility(View.VISIBLE);
//                iv.setVisibility(View.GONE);
//            } else {
//                tv.setVisibility(View.GONE);
//                iv.setVisibility(View.VISIBLE);
//                Picasso.with(getActivity()).load(new File(list.get(position).getImage())).resize(500, 500).centerCrop().into(iv);
//            }
//        }
    }

    class Adapter1 extends BaseAdapter {
        int mPosition = -1;// 选中的位置

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            ViewHolder holder = null;

            // 初始化布局控件
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.m3_item1_layout, null);
                holder.tv = (TextView) convertView
                        .findViewById(R.id.group_textView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // 设置控件内容

            if (mPosition == position) {
                holder.tv.setTextColor(getResources().getColor(
                        R.color.deepBlue));
                convertView.setBackgroundColor(getResources().getColor(
                        R.color.group_item_pressed_bg));
            } else {
                holder.tv.setTextColor(getResources().getColor(
                        android.R.color.black));
                convertView.setBackgroundColor(getResources().getColor(
                        R.color.group_item_bg));
            }
            holder.tv.setText(list.get(position).getName());
            updateUI(position);

            return convertView;

        }

        /**
         * 设置选择的位置
         *
         * @param position
         */
        public void setSelectedPosition(int position) {
            this.mPosition = position;
            notifyDataSetChanged();
        }

        class ViewHolder {

            TextView tv;
        }
    }

    class Adapter2 extends BaseAdapter {


        @Override
        public int getCount() {
            return lists.length;
        }

        @Override
        public Object getItem(int position) {
            return lists[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            ViewHolder holder = null;

            // 初始化布局控件
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.m3_item2_layout, null);
                holder.iv = (ImageView) convertView
                        .findViewById(R.id.iv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // 设置控件内容

            Picasso.with(getActivity()).load(new File(lists[position])).resize(200, 200).centerCrop().into(holder.iv);


            return convertView;

        }


        class ViewHolder {

            ImageView iv;
        }
    }


    class MyItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            index = position;
            adapter1.setSelectedPosition(position);
            lists=list.get(index).getImage().split(";");
            lv2.setAdapter(new Adapter2());
            adapter2=new Adapter2();
//            layout.setVisibility(View.VISIBLE);
//            if (list.get(position).getImage().equals("暂无图片")) {
//                tv.setVisibility(View.VISIBLE);
//                iv.setVisibility(View.GONE);
//            } else {
//                tv.setVisibility(View.GONE);
//                iv.setVisibility(View.VISIBLE);
//                Picasso.with(getActivity()).load(new File(list.get(position).getImage())).resize(200, 200).centerCrop().into(iv);
//            }

        }

    }
    class MyItemClick2 implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent i = new Intent(getActivity(), ShowImageActivity.class);
                    i.putExtra("path", lists[position]);
            startActivity(i);

        }

    }


}
