package com.umeng.soexample.shopcar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.soexample.shopcar.bean.CarBean;
import com.umeng.soexample.shopcar.control.AddSumView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * data:2018/11/21
 * author: HJL (磊)
 * function:
 */
public class EBAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<CarBean.DataBean> handlist;
    private AddSumView.OnNumChangedListener onNumChangedListener;

    public EBAdapter(Context context, List<CarBean.DataBean> handlist) {
        this.context = context;
        this.handlist = handlist;
    }

    @Override
    public int getGroupCount() {
        return handlist.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return handlist.get(i).getList().size();
    }

    @Override
    public Object getGroup(int i) {
        return handlist.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return handlist.get(i).getList().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;
        if (view == null) {
            groupViewHolder = new GroupViewHolder();
            view = View.inflate(context, R.layout.eb_item, null);
            groupViewHolder.checkBox = view.findViewById(R.id.CB_checkbox);
            groupViewHolder.shopName = view.findViewById(R.id.tv_hand);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }

        groupViewHolder.shopName.setText(handlist.get(i).getSellerName());
        groupViewHolder.checkBox.setChecked(handlist.get(i).isChecked());

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view == null) {
            childViewHolder = new ChildViewHolder();
            view = View.inflate(context, R.layout.child_item, null);
            childViewHolder.cbChecked = view.findViewById(R.id.cbChecked);
            childViewHolder.GoodsName = view.findViewById(R.id.GoodsName);
            childViewHolder.Goodspre = view.findViewById(R.id.Goodspre);
            childViewHolder.Goodspic = view.findViewById(R.id.Goodspic);
            childViewHolder.addSumView = view.findViewById(R.id.addsum);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }

        final CarBean.DataBean.ListBean listBean = handlist.get(i).getList().get(i1);
        childViewHolder.cbChecked.setChecked(listBean.getSelected() == 0);
        childViewHolder.GoodsName.setText(listBean.getTitle());
        childViewHolder.Goodspre.setText("$" + listBean.getPrice() + "");
        childViewHolder.addSumView.setCurentCount(listBean.getNum());
        Glide.with(context).load(listBean.getImages().split("\\|")[0]).into(childViewHolder.Goodspic);

        //加减联动
        childViewHolder.addSumView.setOnNumChangedListener(new AddSumView.OnNumChangedListener() {
            @Override
            public void onNumChanged(View view, int curNum) {
                listBean.setNum(curNum);
                if (onNumChangedListener != null){
                    onNumChangedListener.onNumChanged(view,curNum);
                }
            }
        });

        return view;
    }

    public void setOnNumChangedListener(AddSumView.OnNumChangedListener onNumChangedListener) {
        this.onNumChangedListener = onNumChangedListener;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    class GroupViewHolder {
        CheckBox checkBox;
        TextView shopName;
    }

    class ChildViewHolder {
        CheckBox cbChecked;
        ImageView Goodspic;
        TextView GoodsName;
        TextView Goodspre;
        AddSumView addSumView;
    }

}
