package com.gogocarpon.gogocarpon._app;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.ActParameter;
import com.gogocarpon.gogocarpon._app.baseclass.AppConfig;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.gogocarpon.gogocarpon._app.baseclass.UserInfo;
import com.gogocarpon.gogocarpon._app.baseclass.Utils;
import com.gogocarpon.gogocarpon._app.models.Category;
import com.gogocarpon.gogocarpon._app.models.Deal;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class HomeActivity extends BaseActivity implements DealListFragmentActivity.DealListFragment.DealListSelectedListener, CategoryFragmentActivity.CategoryFragment.CategoryListSelectedListener {

    private MyPagerAdapter adapter;
    private SystemBarTintManager mTintManager;
    ViewPager pager;
    PagerSlidingTabStrip tabs;
    private Drawable oldBackground = null;
    private int currentColor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i("LOG APP", "HomeActivity : onCreate");

		setContentView(R.layout.act_deal_tabbar_main);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        mTintManager = new SystemBarTintManager(this);
        // enable status bar tint
        mTintManager.setStatusBarTintEnabled(true);

        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());

        pager.setPageMargin(pageMargin);
        pager.setCurrentItem(0);
        changeColor(getResources().getColor(R.color.blue_light));
	}

    private void changeColor(int newColor) {
        tabs.setBackgroundColor(newColor);
        tabs.setTextColor(Color.WHITE);
        tabs.setIndicatorColor(Color.WHITE);
//        tabs.setDividerColor(Color.WHITE);

        mTintManager.setTintColor(newColor);
        // change ActionBar color just if an ActionBar is available
        Drawable colorDrawable = new ColorDrawable(newColor);
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        if (oldBackground == null) {
            getSupportActionBar().setBackgroundDrawable(ld);
        } else {
            TransitionDrawable td = new TransitionDrawable(new Drawable[]{oldBackground, ld});
            getSupportActionBar().setBackgroundDrawable(td);
            td.startTransition(200);
        }

        oldBackground = ld;
        currentColor = newColor;
    }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
    
	//hanh edit actionbar login
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

            super.initActionBarControl(menu);
            ActionBar ab = getSupportActionBar();

            ab.setHomeButtonEnabled(true);
            getSupportActionBar().setIcon(R.drawable.emu_category);
            return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public void invalidateOptionsMenu() {
		// TODO Auto-generated method stub
		super.invalidateOptionsMenu();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    if (item.getItemId() == android.R.id.home) {
	    	// Overwrite button home function
	    	//menuCats.toggle();
            finish();
	    	return true;
	    }
	    return super.onOptionsItemSelected(item);
	}	
    
	@Override
	protected void onStart() {
		
		Log.i("LOG APP", "****** onStart : HomeActivity");
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		Log.i("LOG APP", "****** onResume : HomeActivity");
		
		UserInfo info = UserInfo.getInstance();
		Log.i("LOG APP", "" + info.getLastUpdateDb());
		long totalSecond = Utils.totalSecondToNow(info.getLastUpdateDb(), AppConfig.SERVER_TIME_FORMAT);
		if(totalSecond > AppConfig.REFRESH_DATA) {
			// Load data from wbservice and save to database
			_intentManager.OpenActivity(IntentManager.SPLASH_SCREEN_ACTIVITY);
		}
//		if(mMenu!=null){
//			this.initActionBarControl(mMenu);
//		}
		invalidateOptionsMenu();	
		super.onResume();
	}
	
	@Override
	public void onDealSelected(Deal rowDeal, int position) {
		
		ActParameter params = new ActParameter();
		params.put("rowDeal", rowDeal);
		
		Log.i("LOG APP", "deal_id = " + rowDeal._id + " ;deal_status = " + rowDeal._deal_status);
		
		_intentManager.OpenActivity(IntentManager.DEAL_DETAIL_ACTIVITY, params);
    }

    @Override
    public void setDevice() {
        Log.d("interface", "success");

    }

    @Override
	public void onCategorySelected(Category cat, int position) {

//		Toast.makeText(getApplicationContext(), "Gia tri : " + cat._id + " ; name = " + cat._name,Toast.LENGTH_LONG).show();
		// Close menu
//		menuCats.toggle();
		
		ActParameter params = new ActParameter();
		params.put("cat_id", cat._id);

		UserInfo.getInstance().cat_id = cat._id;
		
		// If user choose old cat => refresh data 
		_intentManager.OpenActivity(
				IntentManager.SPLASH_SCREEN_ACTIVITY, params);
		finish();
	}

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"FEATURE", "ALL DEALS", "UPCOMING", "EXPIRED"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {

            int deal_Position = 0;
            switch (position)   {
                case 0:
                    deal_Position = 0;
                    break;
                case 1:
                    deal_Position = 0;
                    break;
                case 2:
                    deal_Position = 3;
                    break;
                case 3:
                    deal_Position = 2;
                    break;
                default:
                    break;
            }
           return DealListFragmentActivity.DealListFragment.newInstance(deal_Position);
        }

    }
}
