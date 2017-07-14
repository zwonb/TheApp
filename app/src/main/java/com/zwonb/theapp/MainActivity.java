package com.zwonb.theapp;

import android.Manifest;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.zwonb.theapp.base.BasePermissionActivity;
import com.zwonb.theapp.base.PermissionCallBack;
import com.zwonb.theapp.toolbar.ToolbarMenuListen;


public class MainActivity extends BasePermissionActivity implements ToolbarMenuListen {

    @Override
    protected void init(Bundle bundle) {
        super.init(bundle);
        super.checkPermissions(new PermissionCallBack() {
            @Override
            public void acceptAll() {

            }

            @Override
            public void dialogCancel() {

            }
        }, Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    protected void setTitleMenu(Menu menu) {
        super.setToolbarMenuListen(this);
        super.setTitle("这是标题", false, R.mipmap.ic_img, R.mipmap.ic_img);
        super.setToolbarMenu(menu, 2, "设置");

    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void menuOnClick1() {
        Toast.makeText(this, "点击了菜单1", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void menuOnClick2() {
        Toast.makeText(this, "点击了菜单2", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void menuOnClick3() {
        Toast.makeText(this, "点击了菜单3", Toast.LENGTH_SHORT).show();
    }

    public void start1(View view) {
        startActivity(Main2Activity.class);
    }
}
