package com.umeng.soexample.shopcar.presenter;

import android.view.View;

import com.umeng.soexample.shopcar.Model.CarModel;
import com.umeng.soexample.shopcar.view.CarView;

import java.util.List;

/**
 * data:2018/11/21
 * author: HJL (磊)
 * function:
 */
public class CarPresenter {
    private CarView carView;
    private CarModel carModel;

    public CarPresenter(CarView carView){
        this.carView = carView;
        carModel = new CarModel();
    }

    public void show(String url){
        carModel.show(url, new CarModel.Trans() {
            @Override
            public void fer(List list) {
                carView.OnSuccess(list);
            }
        });
    }

}
