package com.zwonb.tool.toolbar;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zwonb.tool.R;
import com.zwonb.tool.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 有Toolbar的Activity
 * Created by zyb on 2017/7/6.
 */

public abstract class BaseToolbarActivity extends BaseActivity {

    private List<MenuItem> menuList = new ArrayList<>();
    private ToolbarMenuListen menuListen;
    private TextView titleCenter;
    protected FrameLayout mContentViewGroup;

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.base_activity);
        setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        Toolbar toolbar = getViewById(R.id.base_toolbar);
        setSupportActionBar(toolbar);
        titleCenter = getViewById(R.id.base_toolbar_title_centre);
        //中间标题默认隐藏
        titleCenter.setVisibility(View.GONE);

        mContentViewGroup = (FrameLayout) findViewById(R.id.base_content_layout);
        LayoutInflater.from(this).inflate(setContentLayout(), mContentViewGroup);
    }

    /**
     * 这个方法是设置标题菜单的，必须在onPrepareOptionsMenu方法中执行
     * setTitle各种重载方法在此方法内执行
     */
    protected abstract void setTitleMenu(Menu menu);

    protected abstract @LayoutRes int setContentLayout();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menuList.add(menu.findItem(R.id.toolbar_menu1));
        menuList.add(menu.findItem(R.id.toolbar_menu2));
        menuList.add(menu.findItem(R.id.toolbar_menu3));
        setMenuGone();
        setTitleMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    //--------------------设置普通的标题--star-----------------------//

    /**
     * 此方法默认有返回关闭按钮
     */
    protected void setTitle(String title) {
        setTitle(title, true);
    }

    /**
     * @param title    标题
     * @param haveBack 是否有返回图标
     */
    protected void setTitle(String title, boolean haveBack) {
        haveBackFinish(title, haveBack, false);
    }

    /**
     * 设置带有文字的菜单栏
     *
     * @param rightText 长度不能大于3
     */
    protected void setTitle(String title, boolean haveBack, String... rightText) {
        setTitleTextMenu(title, haveBack, rightText, false);
    }

    /**
     * 设置带有图标的菜单栏
     *
     * @param iconRes 长度不能大于3
     */
    protected void setTitle(String title, boolean haveBack, @DrawableRes int... iconRes) {
        setTitleIconMenu(title, haveBack, iconRes, false);

    }
    //--------------------设置普通的标题--star-----------------------//


    //--------------------设置中间的标题--star-----------------------//
    protected void setTitleCenter(String title) {
        setTitleCenter(title, true);
    }

    protected void setTitleCenter(String title, boolean haveBack) {
        haveBackFinish(title, haveBack, true);
    }

    /**
     * @param rightList 长度不能大于3
     */
    protected void setTitleCenter(String title, boolean haveBack, String... rightList) {
        setTitleTextMenu(title, haveBack, rightList, true);
    }

    /**
     * @param iconRes 长度不能大于3
     */
    protected void setTitleCenter(String title, boolean haveBack, @DrawableRes int... iconRes) {
        setTitleIconMenu(title, haveBack, iconRes, true);
    }
    //--------------------设置中间的标题--end-----------------------//

    /**
     * 单独设置右边菜单的文字
     * 可以跟上面的setTitle、setTitleCenter配合使用
     */
    protected void setToolbarMenu(Menu menu, @IntRange(from = 0, to = 2) int position, String menuText) {
        MenuItem menuItem = menu.getItem(position);
        menuItem.setVisible(true);
        menuItem.setTitle(menuText);
    }

    /**
     * 单独设置右边菜单的图标
     * 可以跟上面的setTitle、setTitleCenter配合使用
     */
    protected void setToolbarMenu(Menu menu, @IntRange(from = 0, to = 2) int position, @DrawableRes int icon) {
        MenuItem menuItem = menu.getItem(position);
        menuItem.setVisible(true);
        menuItem.setIcon(icon);
    }


    /**
     * @param haveBack 是否有返回键
     * @param isCenter 标题是否居中
     */
    private void haveBackFinish(String title, boolean haveBack, boolean isCenter) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(haveBack);
            if (isCenter) {
                actionBar.setDisplayShowTitleEnabled(false);
                titleCenter.setVisibility(View.VISIBLE);
                titleCenter.setText(title);
            } else {
                actionBar.setTitle(title);
            }
        }

    }

    private void setTitleTextMenu(String title, boolean haveBack, String[] rightText, boolean isCenter) {
        haveBackFinish(title, haveBack, isCenter);
        if (rightText.length > 3) {
            throw new IllegalArgumentException("传入的 rightText 的长度不能大于3");
        }
        //遍历所有右边的菜单
        for (int i = 0; i < rightText.length; i++) {
            MenuItem menuItem = menuList.get(i);
            menuItem.setVisible(true);
            menuItem.setTitle(rightText[i]);
        }
    }

    private void setTitleIconMenu(String title, boolean haveBack, @DrawableRes int[] iconRes, boolean isCenter) {
        if (iconRes.length > 3) {
            throw new IllegalArgumentException("传入的 iconRes 的长度不能大于3");
        }
        haveBackFinish(title, haveBack, isCenter);
        //遍历所有右边的菜单
        for (int i = 0; i < iconRes.length; i++) {
            MenuItem menuItem = menuList.get(i);
            menuItem.setVisible(true);
            menuItem.setIcon(iconRes[i]);
        }
    }

    //隐藏所有右边的菜单
    private void setMenuGone() {
        for (int i = 0; i < menuList.size(); i++) {
            menuList.get(i).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
        } else if (itemId == R.id.toolbar_menu1) {
            if (menuListen != null) {
                menuListen.menuOnClick1();
            }
        } else if (itemId == R.id.toolbar_menu2) {
            if (menuListen != null) {
                menuListen.menuOnClick2();
            }
        } else if (itemId == R.id.toolbar_menu3) {
            if (menuListen != null) {
                menuListen.menuOnClick3();
            }
        }
        return true;
    }

    protected void setToolbarMenuListen(ToolbarMenuListen menuListen) {
        this.menuListen = menuListen;
    }

    @Override
    public void finish() {
        menuList = null;
        super.finish();
    }
}
