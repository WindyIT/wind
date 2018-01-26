package com.example.windy.wind;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.windy.wind.adapter.ViewPagerAdapter;
import com.example.windy.wind.data.local.ZhihuDailyNewsLocalDs;
import com.example.windy.wind.data.preferences.PreferencesHelper;
import com.example.windy.wind.data.remote.ZhihuDailyNewsRemoteDs;
import com.example.windy.wind.data.repository.ZhihuDailyNewsRepository;
import com.example.windy.wind.database.cache.CacheManager;
import com.example.windy.wind.database.cache.ClearCacheInterface;
import com.example.windy.wind.timeline.meiriYiwen.MeiRiYiWenFragment;
import com.example.windy.wind.timeline.zhihuDaily.ZhihuDailyFragment;
import com.example.windy.wind.timeline.zhihuDaily.ZhihuDailyPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private String[] mTabTitles = {"知乎日报", "每日一文"};
    private List<Fragment> mFragments;
    private ZhihuDailyFragment mZhihuDailyFragment;
    private MeiRiYiWenFragment mMeiRiYiWenFragment;

    private CacheManager cacheManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v("ON_CREATE", "on create");

        mZhihuDailyFragment = new ZhihuDailyFragment();
        mMeiRiYiWenFragment = new MeiRiYiWenFragment();
        mFragments = new ArrayList<>();
        mFragments.add(mZhihuDailyFragment);
        mFragments.add(mMeiRiYiWenFragment);

        new ZhihuDailyPresenter(mZhihuDailyFragment,
                ZhihuDailyNewsRepository.getInstance(ZhihuDailyNewsRemoteDs.getInstance(), ZhihuDailyNewsLocalDs.getInstance(this)));

        cacheManager = CacheManager.getInstance();

        initView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_user) {

        } else if (id == R.id.nav_night_mode) {
            boolean isNightMode = PreferencesHelper.getInstance(this).getBoolean(PreferencesHelper.NIGHT_MODE);
            boolean newMode = !isNightMode;
            PreferencesHelper.getInstance(this).getEditor().putBoolean(PreferencesHelper.NIGHT_MODE, newMode).apply();

            Log.v("NIGHT", "Mode is " + (isNightMode ? "night-mode" : "light-mode"));
            AppCompatDelegate.setDefaultNightMode(isNightMode ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);
            recreate(); //重启activity使夜间模式生效
        } else if (id == R.id.nav_clear_cache) {
            cacheManager.clearCache(new ClearCacheInterface.ClearCacheCallback() {
                @Override
                public void onNotCacheExist() {
                    Toast.makeText(getApplicationContext(), "缓存已清空", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onOcurrError() {
                    Toast.makeText(getApplicationContext(), "清空缓存出现错误", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClearSuccessfullt(int deletedItemsCnt) {
                    Toast.makeText(getApplicationContext(), "清除" + deletedItemsCnt + "条缓存", Toast.LENGTH_SHORT).show();
                }
            });
        }
//        else if (id == R.id.nav_collect) {
//
//        } else if (id == R.id.nav_history) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout);
        mViewPager = (ViewPager)findViewById(R.id.view_pager);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments, mTabTitles));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
