package com.zwonb.theapp;

import android.view.Menu;

import com.zwonb.tool.toolbar.BaseToolbarActivity;

public class Main2Activity extends BaseToolbarActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//    }

    @Override
    protected void setTitleMenu(Menu menu) {
        setTitle("我的界面");
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_main2;
    }

//    @Override
//    protected void init(Bundle savedInstanceState) {
//        super.init(savedInstanceState);
//
//    }
}
