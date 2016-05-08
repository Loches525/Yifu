package com.bishe.yifu.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bishe.yifu.R;
import com.bishe.yifu.ShowImageActivity;
import com.bishe.yifu.db.DBService;
import com.bishe.yifu.entity.Location;
import com.bishe.yifu.entity.Weather;
import com.bishe.yifu.entity.Yifu;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class M1Fragment extends Fragment {

    private View view;
    Location lt;
    TextView tv_location;
    TextView tv_tianqi;
    Button btn;

    RecyclerView rv;
    DBService service;
    ArrayList<Yifu> list;
    ArrayList<Yifu> lists;
    HomeAdapter mAdapter;
    int index = -1;
    public boolean bool = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_m1, container, false);
        service = new DBService(getActivity());
        tv_location = (TextView) view.findViewById(R.id.tv_location);
        tv_tianqi = (TextView) view.findViewById(R.id.tv_tianqi);
        btn= (Button) view.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUi();
            }
        });
        rv = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(linearLayoutManager);
        lt = new Location(getActivity(), ul);

        return view;

    }


    class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder = null;

            holder = new MyViewHolder(LayoutInflater.from(
                    getActivity()).inflate(R.layout.m1_item_layout, parent,
                    false));


            return holder;
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof MyViewHolder) {
                MyViewHolder holder1 = (MyViewHolder) holder;
                holder1.tv.setText(lists.get(position).getName());
                if (lists.get(position).getImage().equals("")) {

                } else {
                    if (bool) {
                        if (position == lists.size() - 1) {
                            bool = false;
                        }
                        final String[] str = lists.get(position).getImage().split(";");
                        if (str.length > 1) {
                            final int i = new Random().nextInt(str.length);
                            Picasso.with(getActivity()).load(new File(str[i])).resize(300, 300).centerCrop().into(holder1.iv);
                            holder1.iv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onc.onclick(str[i]);
                                }
                            });
                        } else {
                            Picasso.with(getActivity()).load(new File(str[0])).resize(300, 300).centerCrop().into(holder1.iv);
                            holder1.iv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onc.onclick(str[0]);
                                }
                            });
                        }


                    }

                }
            }


        }

        @Override
        public int getItemCount() {
            return lists.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;
            ImageView iv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.tv);
                iv = (ImageView) view.findViewById(R.id.iv);
            }
        }
    }

    public Onc onc = new Onc() {
        @Override
        public void onclick(String path) {


            Intent i = new Intent(getActivity(), ShowImageActivity.class);
            i.putExtra("path", path);
            startActivity(i);
        }
    };


    public interface Onc {
        void onclick(String path);
    }

    /**
     * 监听天气的回调方法
     */
    Location.update_location ul = new Location.update_location() {

        @Override
        public void update_dizhi(String location) {

        }

        @Override
        public void update_tianqi(Weather w) {
            tv_location.setText(w.getCity());
            tv_tianqi.setText(w.getWendu() + "℃" + "\t" + w.getWeather());
            if (w.getWendu() != null && !w.getWendu().equals("")) {
                int int_du = Integer.parseInt(w.getWendu());
                index = int_du;
                updateUi();
            }


        }

    };

    public void updateUi() {
        bool = true;
        list = service.search();
        lists = new ArrayList<>();
        if (index >= 25) {
            lists.add(list.get(1));
            final int i = new Random().nextInt(2);
            if (i == 0) {
                lists.add(list.get(5));
            } else {
                lists.add(list.get(7));
            }


        } else if (index >= 18 && index <= 24) {
            lists.add(list.get(0));
            final int i = new Random().nextInt(2);
            if (i == 0) {
                lists.add(list.get(4));
            } else {
                lists.add(list.get(6));
            }

        } else if (index >= 15 && index < 18) {
            lists.add(list.get(0));
            final int i = new Random().nextInt(2);
            if (i == 0) {
                lists.add(list.get(4));
            } else {
                lists.add(list.get(6));
            }
            lists.add(list.get(3));
        } else if (index < 15) {
            lists.add(list.get(0));
            final int i = new Random().nextInt(2);
            if (i == 0) {
                lists.add(list.get(4));
            } else {
                lists.add(list.get(6));
            }
            lists.add(list.get(3));
            lists.add(list.get(2));
        }
        rv.setAdapter(mAdapter = new HomeAdapter());
    }


}
