package com.umeng.soexample.shopcar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.umeng.soexample.shopcar.bean.CarBean;
import com.umeng.soexample.shopcar.control.AddSumView;
import com.umeng.soexample.shopcar.presenter.CarPresenter;
import com.umeng.soexample.shopcar.view.CarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CarView {

    private ExpandableListView mEb;
    private List<CarBean.DataBean> handlist = new ArrayList<>();
    private CarPresenter carPresenter;
    private CheckBox mCbAll;
    private TextView mTvSum;
    private EBAdapter ebAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }


    private void initView() {
        carPresenter = new CarPresenter(this);
        carPresenter.show("http://120.27.23.105/product/getCarts?source=android&uid=99");
        mEb = findViewById(R.id.eb);
        mCbAll = findViewById(R.id.cbAll);
        mTvSum = findViewById(R.id.tvSum);

        mEb.setGroupIndicator(null);


        mCbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    setCheckAll(0);
                } else {
                    setCheckAll(1);
                }
            }
        });

    }

    private void setCheckAll(int s) {
        int itemCount = ebAdapter.getGroupCount();
        for (int i = 0; i < itemCount; i++) {
            CarBean.DataBean item = (CarBean.DataBean) ebAdapter.getGroup(i);
            List<CarBean.DataBean.ListBean> itemList = item.getList();
            for (int j = 0; j < itemList.size(); j++) {
                CarBean.DataBean.ListBean listBean = itemList.get(j);
                listBean.setSelected(s);
            }
        }
        ebAdapter.notifyDataSetChanged();
        getTotal();
    }

    private void getTotal() {
        double total = 0;
        int itemCount = ebAdapter.getGroupCount();
        for (int i = 0; i < itemCount; i++) {
            CarBean.DataBean item = (CarBean.DataBean) ebAdapter.getGroup(i);
            List<CarBean.DataBean.ListBean> list = item.getList();
            for (int j = 0; j < list.size(); j++) {
                CarBean.DataBean.ListBean listBean = list.get(j);

                boolean checked = listBean.isChecked();

                if (!checked) {
                    double price = listBean.getPrice();
                    total += price * listBean.getNum();
                }

                ebAdapter.setOnNumChangedListener(new AddSumView.OnNumChangedListener() {
                    @Override
                    public void onNumChanged(View view, int curNum) {
                        getTotal();
                    }
                });


            }
        }
        mTvSum.setText("合集:" + total);
    }


    @Override
    public void OnSuccess(final List data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ebAdapter = new EBAdapter(MainActivity.this, data);
                mEb.setAdapter(ebAdapter);
                //默认展开分组展开分组
                for (int a = 0; a < ebAdapter.getGroupCount(); a++) {

                    mEb.expandGroup(a);
                }
            }
        });
        //头部点击
        initClickListener();
    }

    //头部点击
    private void initClickListener() {
        mEb.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                CarBean.DataBean group = (CarBean.DataBean) ebAdapter.getGroup(i);
                group.setChecked(!group.isChecked());
                int c = 0;

                if (!group.isChecked()) {
                    c = 1;
                }

                List<CarBean.DataBean.ListBean> list = group.getList();
                for (int j = 0; j < list.size(); j++) {
                    CarBean.DataBean.ListBean listBean = list.get(j);
                    listBean.setSelected(c);
                }
                ebAdapter.notifyDataSetChanged();
                getTotal();
                return true;
            }
        });

        mEb.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                CarBean.DataBean.ListBean child = (CarBean.DataBean.ListBean) ebAdapter.getChild(i, i1);
                boolean checked = child.isChecked();
                if (checked) {
                    child.setSelected(0);
                } else {
                    child.setSelected(1);
                }
                ebAdapter.notifyDataSetChanged();
                getTotal();

                return true;
            }
        });
    }

    @Override
    public void OnFaile(String msg) {

    }

}
