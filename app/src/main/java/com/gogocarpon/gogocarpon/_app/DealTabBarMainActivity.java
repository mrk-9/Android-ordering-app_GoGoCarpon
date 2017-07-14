package com.gogocarpon.gogocarpon._app;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon.quickaction.MenuActionItem;
import com.gogocarpon.gogocarpon.quickaction.MenuQuickAction;


public class DealTabBarMainActivity extends ActionBarActivity {
	
	private TabHost mTabHost;
	
	private static final int ID_INFO   = 4;
    private static final int ID_ERASE  = 5; 
    private static final int ID_OK     = 6;
	
	// Properties
	private static DealTabBarMainActivity DealTabBarMainActivityRef = null;
	public static DealTabBarMainActivity getRef() {
		return DealTabBarMainActivityRef;
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DealTabBarMainActivityRef = this;
		
		setContentView(R.layout.act_deal_tabbar_main);
		
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);

		LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
		mLocalActivityManager.dispatchCreate(savedInstanceState);
		mTabHost.setup(mLocalActivityManager);
		
		
//		Config MenuQickAction 
		
        MenuActionItem infoItem     = new MenuActionItem(ID_INFO, "Info", getResources().getDrawable(R.drawable.mnqa_menu_info));
        MenuActionItem eraseItem    = new MenuActionItem(ID_ERASE, "Clear", getResources().getDrawable(R.drawable.mnqa_menu_eraser));
        MenuActionItem okItem       = new MenuActionItem(ID_OK, "OK", getResources().getDrawable(R.drawable.mnqa_menu_ok));


        //create QuickAction. Use QuickAction.VERTICAL or QuickAction.HORIZONTAL param to define layout 
        //orientation
        final MenuQuickAction quickAction = new MenuQuickAction(this, MenuQuickAction.VERTICAL);

        //add action items into QuickAction
        quickAction.addActionItem(infoItem);
        quickAction.addActionItem(eraseItem);
        quickAction.addActionItem(okItem);
        
      //Set listener for action item clicked
        quickAction.setOnActionItemClickListener(new MenuQuickAction.OnActionItemClickListener() {          
            @Override
            public void onItemClick(MenuQuickAction source, int pos, int actionId) {
                //here we can filter which action item was clicked with pos or actionId parameter
            	MenuActionItem actionItem = quickAction.getActionItem(pos);

                Toast.makeText(getApplicationContext(), actionItem.getTitle() + " selected", Toast.LENGTH_SHORT).show();                
            }
        });

        //set listnener for on dismiss event, this listener will be called only if QuickAction dialog was dismissed
        //by clicking the area outside the dialog.
        quickAction.setOnDismissListener(new MenuQuickAction.OnDismissListener() {          
            @Override
            public void onDismiss() {
                Toast.makeText(getApplicationContext(), "Dismissed", Toast.LENGTH_SHORT).show();
            }
        });
        
//        --------------------------------
        
        
        
//		final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
//		//      actionBar.setHomeAction(new IntentAction(this, createIntent(this), R.drawable.ic_title_home_demo));
//		actionBar.setHomeAction(new IntentAction(this, createIntent(this), R.drawable.z_location_web_site));
//		actionBar.setTitle(getString(R.string.home));
//		
//		//      final Action shareAction = new IntentAction(this, createShareIntent(), R.drawable.ic_title_share_default);
//		//      actionBar.addAction(shareAction);
////		  final Action otherAction = new IntentAction(this, new Intent(this, MainActivity.class), R.drawable.z_social_person);
////		  actionBar.addAction(otherAction);
//		  
//		  // Them 1 ham rieng
//		actionBar.addAction(new Action() {
//			@Override
//			public void performAction(View view) {
////				Toast.makeText(MainActivity.this, "hang cua tui.", Toast.LENGTH_SHORT).show();
//				
//				quickAction.show(view);
//			}
//			
//			@Override
//			public int getDrawable() {
//				return R.drawable.z_social_person;
//			}
//		});
      
		addTab(R.string.tab_feature_deal, R.drawable.tab_info , new Intent(this, FeatureDealActivity.class));
		addTab(R.string.tab_all_deal, R.drawable.tab_info, new Intent(this, AllDealActivity.class));
		addTab(R.string.tab_upcoming_deal, R.drawable.tab_info, new Intent(this, UpcomingDealActivity.class));
		addTab(R.string.tab_expired_deal, R.drawable.tab_info, new Intent(this, ExpiredDealActivity.class));
		
//		addTab(R.string.tab_feature_deal, R.drawable.tab_info , new Intent(this, FeatureDealActivity.class));
//		addTab(R.string.tab_all_deal, R.drawable.tab_info, new Intent(this, AllDealActivity.class));
//		addTab(R.string.tab_upcoming_deal, R.drawable.tab_info, new Intent(this, FeatureDealActivity.class));
//		addTab(R.string.tab_expired_deal, R.drawable.tab_info, new Intent(this, AllDealActivity.class));
		  
//		mTabHost.setCurrentTab(0);
      
    }
    
	private void addTab(int labelId, int drawableId, Intent intent) {
//		Intent intent = new Intent(this, AllDealActivity.class);
		TabHost.TabSpec spec = mTabHost.newTabSpec("tab" + labelId);		
		
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);
		
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);
		
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		mTabHost.addTab(spec);
		
	}    
    
    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, AllDealActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }



}
