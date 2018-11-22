package com.umeng.soexample.shopcar.Model;

import com.google.gson.Gson;
import com.umeng.soexample.shopcar.bean.CarBean;
import com.umeng.soexample.shopcar.net.HttpOk;

import java.util.List;

/**
 * data:2018/11/21
 * author: HJL (磊)
 * function:
 */
public class CarModel {

    public void show(String url, final Trans trans){
        new HttpOk().get(url).Result(new HttpOk.HttpOkListener() {

            @Override
            public void success(String data) {
                Gson gson = new Gson();
                CarBean carBean = gson.fromJson(data, CarBean.class);
                List<CarBean.DataBean> data1 = carBean.getData();
                trans.fer(data1);
            }

            @Override
            public void Faile(String msg) {

            }
        });
    }
    public interface Trans{
        void fer(List list);
    }

}
