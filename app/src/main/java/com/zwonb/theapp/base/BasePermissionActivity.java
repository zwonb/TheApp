package com.zwonb.theapp.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.zwonb.theapp.toolbar.BaseToolbarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 6.0+动态权限申请
 * Created by zyb on 2017/7/11.
 */

public abstract class BasePermissionActivity extends BaseToolbarActivity {

    /**
     * isFirst 是否第一次授权(为了迎合用户拒绝权限不再提示)
     * permissionCallBack 接受所有权限后操作的回调
     */
    private boolean isFirst = true;
    private PermissionCallBack permissionCallBack;
    private String[] permissions;

    protected void checkPermissions(PermissionCallBack callBack, String... permissions) {
        permissionCallBack = callBack;
        this.permissions = permissions;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        if (!permissionList.isEmpty()) {
            String[] permission = permissionList.toArray(new String[permissionList.size()]);
            //请求授权
            ActivityCompat.requestPermissions(this, permission, 1);
        } else {
            //接受所有权限
            permissionCallBack.acceptAll();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {

                    for (int i = 0; i < permissions.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            //第一次授权shouldShowRequestPermissionRationale返回是false
                            //之后都是true，如果用户拒绝权限且不再提示将返回false
                            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                                    permissions[i]) && !isFirst) {
                                //用户拒绝权限且不再提示
                                gotoSetting();
                                return;
                            } else {
                                //用户拒绝权限
                                isFirst = false;
                                requestAgain();
                                return;
                            }
                        }
                    }

                    if (permissionCallBack != null) {
                        permissionCallBack.acceptAll();
                    }
                } else {
                    Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void requestAgain() {
        new AlertDialog.Builder(this)
                .setMessage("请授予相关权限，否则无法正常使用")
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (permissionCallBack != null) {
                            permissionCallBack.dialogCancel();
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        checkPermissions(permissionCallBack, permissions);
                    }
                })
                .show();
    }

    private void gotoSetting() {
        new AlertDialog.Builder(this)
                .setMessage("请在应用管理开启相关权限，否则无法正常使用")
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (permissionCallBack != null) {
                            permissionCallBack.dialogCancel();
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent localIntent = new Intent();
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivity(localIntent);
                    }
                })
                .show();
    }

    @Override
    public void finish() {
        permissions = null;
        super.finish();
    }
}
