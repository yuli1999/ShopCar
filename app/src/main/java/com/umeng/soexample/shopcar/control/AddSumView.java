package com.umeng.soexample.shopcar.control;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.soexample.shopcar.R;

/**
 * data:2018/11/21
 * author: HJL (磊)
 * function:
 */
public class AddSumView extends LinearLayout {

    private View view;
    private TextView add;
    private TextView sub;
    private TextView number;
    private OnNumChangedListener onNumChangedListener;

    public AddSumView(Context context) {
        this(context, null);
    }

    public AddSumView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AddSumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initListener();
    }

    private void initView(Context context) {
        view = View.inflate(context, R.layout.add_sum, this);
        add = view.findViewById(R.id.tvAdd);
        sub = view.findViewById(R.id.tvSub);
        number = view.findViewById(R.id.tvNumber);
        number.setText("1");
    }


    private void initListener() {
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        sub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sub();
            }
        });
    }

    private void add() {
        String cu = number.getText().toString();
        int parseInt = Integer.parseInt(cu);
        parseInt++;
        setCurentCount(parseInt);
    }

    private void sub() {
        String cu = number.getText().toString();
        int parseInt = Integer.parseInt(cu);
        if (parseInt > 1) {
            parseInt--;
            setCurentCount(parseInt);
        } else {
            Toast.makeText(getContext(), "再减没了 ", Toast.LENGTH_SHORT).show();
        }
    }

    public int getCurentCount() {
        return Integer.parseInt(number.getText().toString());
    }


    public void setOnNumChangedListener(OnNumChangedListener onNumChangedListener){
        this.onNumChangedListener = onNumChangedListener;
    }


    public void setCurentCount(int num) {
        number.setText(num+"");
        if (onNumChangedListener !=null){
            onNumChangedListener.onNumChanged(this,num);
        }
    }

    public interface OnNumChangedListener{
        void onNumChanged(View view,int curNum);
    }
}
