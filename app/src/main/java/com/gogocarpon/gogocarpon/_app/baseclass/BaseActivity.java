/*
 * Copyright 2010 Rapture Egde, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gogocarpon.gogocarpon._app.baseclass;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.ui.listeners.IGrabRequestUrlListener;


public abstract class BaseActivity extends ActionBarActivity implements
        IGrabRequestUrlListener<String, Integer> {

	public static final int ID_LOGIN = 1;
	public static final int ID_SIGNUP = ID_LOGIN + 1;
	public static final int ID_LOGOUT = ID_SIGNUP + 1;

	public static final int ID_CHANGE_PROFILE = ID_LOGOUT + 1;
	public static final int ID_PURCHASE_HISTORY = ID_CHANGE_PROFILE + 1;
	
	
	public static final int MNI_HOME = 1;
	public static final int MNI_CHANGE_LANG = MNI_HOME + 1;	
	public static final int MNI_CHANGE_CAT = MNI_CHANGE_LANG + 1;
	public static final int MNI_USER = MNI_CHANGE_CAT + 1;

	public static final int MNI_USER_LOGIN = MNI_USER + 1;
    public static final int MNI_USER_ABOUT = MNI_USER_LOGIN +1;
	public static final int MNI_USER_LOGOUT = MNI_USER_ABOUT + 1;
	public static final int MNI_USER_SIGNUP = MNI_USER_LOGOUT + 1;
	public static final int MNI_USER_PROFILE = MNI_USER_SIGNUP + 1;	
	public static final int MNI_USER_PURCHASE_HISTORY = MNI_USER_PROFILE + 1;
	

	protected ActionBar actionBar;
	// protected MenuQuickAction quickAction;

	protected String LOG_TAG = "Enmasse's User";

	protected ActParameter _intentParams = null;
	protected IntentManager _intentManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_intentManager = new IntentManager(this);
		_intentParams = _intentManager.getIntentParams();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
//		ActionBar ab = getSupportActionBar();		
//		
//		ab.setHomeButtonEnabled(true);
////			ab.setDisplayHomeAsUpEnabled(true); // Hien thi bieu tuong nut back
//		getSupportActionBar().setIcon(R.drawable.emu_home);
//			
//		SubMenu sub = menu.addSubMenu("").setIcon(R.drawable.z_social_person);
//		
//		if (!UserInfo.getInstance().isLogin()) {
//			
//			sub.add(1, MNI_USER_LOGIN, 0, R.string.em_login).setIcon(R.drawable.emu_edit_user);
//			
//			sub.add(1, MNI_USER_SIGNUP, 0, R.string.em_signup).setIcon(
//					R.drawable.emu_add_user);
//			
//		} else {
//
//			sub.add(1, MNI_USER_PROFILE, 0, R.string.em_update_profile).setIcon(R.drawable.emu_edit_user);
//			
//			sub.add(1, MNI_USER_PURCHASE_HISTORY, 0, R.string.em_purchase_history).setIcon(
//					R.drawable.emu_purchase_history);
//			
//			sub.add(1, MNI_USER_LOGOUT, 0, R.string.em_logout).setIcon(R.drawable.emu_disconnect);
//			
//		}
//
//		sub.getItem().setShowAsAction(
//				MenuItem.SHOW_AS_ACTION_ALWAYS
//						| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	
		return super.onCreateOptionsMenu(menu);
	}
	
	public void initActionBarControl(Menu menu) {

        ActionBar ab = getSupportActionBar();

        ab.setHomeButtonEnabled(true);
//			ab.setDisplayHomeAsUpEnabled(true); // Hien thi bieu tuong nut back
        getSupportActionBar().setIcon(R.drawable.emu_home);

        SubMenu sub = menu.addSubMenu("").setIcon(R.drawable.z_social_person);
        sub.clear();

        if (!UserInfo.getInstance().isLogin()) {

            sub.add(1, MNI_USER_LOGIN, 0, R.string.em_login).setIcon(R.drawable.emu_edit_user);
//
//            sub.add(1, MNI_USER_SIGNUP, 0, R.string.em_signup).setIcon(
//                    R.drawable.emu_add_user);

        } else {

            sub.add(1, MNI_USER_PROFILE, 0, R.string.em_update_profile).setIcon(R.drawable.emu_edit_user);

            sub.add(1, MNI_USER_PURCHASE_HISTORY, 0, R.string.em_purchase_history).setIcon(
                    R.drawable.emu_purchase_history);

            sub.add(1, MNI_USER_LOGOUT, 0, R.string.em_logout).setIcon(R.drawable.emu_disconnect);
            sub.add(2, MNI_USER_ABOUT, 0, "About");

        }

        sub.getItem().setShowAsAction(
                MenuItem.SHOW_AS_ACTION_ALWAYS
                        | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
//		switch (item.getItemId()) {
//		case android.R.id.home:
//			_intentManager
//			.OpenActivity(IntentManager.HOME_ACTIVITY);
//			break;
//
//		case MNI_USER_LOGIN:
//			_intentManager
//			.OpenActivity(IntentManager.LOGIN_ACTIVITY);
//			break;
//
//		case MNI_USER_LOGOUT:
//			
//			UserInfo.getInstance().reset();
//			// Logout system
//			UserInfo.getInstance().setLogout();
//
//			_intentManager
//					.OpenActivity(IntentManager.HOME_ACTIVITY);
//			
//			break;
//
//		case MNI_USER_SIGNUP:
//			_intentManager
//			.OpenActivity(IntentManager.SIGNUP_ACTIVITY);
//			break;
//
//		case MNI_USER_PROFILE:
//			_intentManager
//			.OpenActivity(IntentManager.CHANGE_PROFILE_ACTIVITY);
//			break;
//			
//		case MNI_USER_PURCHASE_HISTORY:
//			_intentManager
//			.OpenActivity(IntentManager.PURCHASE_HISTORY_ACTIVITY);
//			break;			
//			
//		default:
//			break;
//		}
//		
//		return true;
//	    return super.onOptionsItemSelected(item);
		
		
		if (item.getItemId() == android.R.id.home) {
			
			_intentManager
					.OpenActivity(IntentManager.HOME_ACTIVITY);
			return true;
			
		} else if (item.getItemId() == MNI_USER_LOGIN) {

            _intentManager
                    .OpenActivity(IntentManager.LOGIN_ACTIVITY);

            return true;

        }   else if (item.getItemId() == MNI_USER_LOGOUT) {

			UserInfo.getInstance().reset();
			// Logout system
			UserInfo.getInstance().setLogout();

			_intentManager
					.OpenActivity(IntentManager.HOME_ACTIVITY);
			return true;

		}  else if (item.getItemId() == MNI_USER_PROFILE) {
            if (!AppConfig.isEditProfile)
			_intentManager
					.OpenActivity(IntentManager.CHANGE_PROFILE_ACTIVITY);
			return true;

		} else if (item.getItemId() == MNI_USER_PURCHASE_HISTORY) {
            if (!AppConfig.isHistory)
			_intentManager
					.OpenActivity(IntentManager.PURCHASE_HISTORY_ACTIVITY);
			return true;

		} else if (item.getItemId() == MNI_USER_ABOUT)  {
            if (!AppConfig.isAbout)
                _intentManager.OpenActivity(IntentManager.ABOUT_ACTIVITY);
        }
	    
//		return true;
	    return super.onOptionsItemSelected(item);
		
	}	
	
	/**
	 * IGrabRequestUrlListener
	 */

	@Override
	public void onAsyncTaskBegin() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAsyncTaskDoBackground(String... url) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAsyncTaskComplete(String contentData, Integer messageID) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAsyncTaskError(String contentData, Integer messageID) {
		// TODO Auto-generated method stub
		if (messageID == GrabRequestUrl.DEFAULT_ERROR_ID) {

			boolean result = checkInternetConnection();
			if (result) {
				Utils.ShowNotification(BaseActivity.this,
						getString(R.string.error),
						getString(R.string.error_url_not_existed));
			} else {
				// Show notification connect internet
				AlertDialog.Builder builder = new AlertDialog.Builder(
						BaseActivity.this);
				builder.setTitle(R.string.error_internet_connection_title)
						.setMessage(R.string.error_internet_connection_content)
						.setPositiveButton(R.string.settings,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										startActivity(new Intent(
												Settings.ACTION_WIFI_SETTINGS));
									}
								})
						.setNegativeButton(R.string.no,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
										finish();
									}
								}).create().show();
			}

		} else if (messageID == GrabRequestUrl.URL_ERROR_ID) {
			Utils.ShowNotification(BaseActivity.this,
					getString(R.string.error), getString(R.string.error_url));
		} else if (messageID == GrabRequestUrl.PROTOCOL_ERROR_ID) {
			Utils.ShowNotification(BaseActivity.this,
					getString(R.string.error),
					getString(R.string.error_target_host));
		} else if (messageID == GrabRequestUrl.SESSION_ERROR_ID) {
			Utils.ShowNotification(BaseActivity.this,
					getString(R.string.error),
					getString(R.string.error_session_expired_),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							_intentManager
									.OpenActivity(IntentManager.LOGIN_ACTIVITY);
						}

					});
		}

	}

	protected boolean checkInternetConnection() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// test for connection
		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			// "Internet Connection Not Present"
			return false;
		}
	}

	/**
	 * MenuQuickAction private function
	 */

//	private MenuQuickAction createMenuQuickAction() {
//
//		// create QuickAction. Use QuickAction.VERTICAL or
//		// QuickAction.HORIZONTAL param to define layout
//		// orientation
//		final MenuQuickAction quickAction = new MenuQuickAction(this,
//				MenuQuickAction.VERTICAL);
//
//		if (!UserInfo.getInstance().isLogin()) {
//			MenuActionItem loginItem = new MenuActionItem(ID_LOGIN,
//					getString(R.string.em_login), getResources().getDrawable(
//							R.drawable.emu_connect));
//			MenuActionItem signupItem = new MenuActionItem(ID_SIGNUP,
//					getString(R.string.em_signup), getResources().getDrawable(
//							R.drawable.emu_add_user));
//
//			// add action items into QuickAction
//			quickAction.addActionItem(loginItem);
//			quickAction.addActionItem(signupItem);
//		} else {
//
//			// After login status
//			MenuActionItem titleItem = new MenuActionItem();
//			titleItem.setTitle(UserInfo.getInstance().name);
//			titleItem.setSticky(true);
//			MenuActionItem logoutItem = new MenuActionItem(ID_LOGOUT,
//					getString(R.string.em_logout), getResources().getDrawable(
//							R.drawable.emu_disconnect));
//			MenuActionItem editProfileItem = new MenuActionItem(
//					ID_CHANGE_PROFILE, getString(R.string.em_update_profile),
//					getResources().getDrawable(R.drawable.emu_edit_user));
//			MenuActionItem pruchaseHistoryItem = new MenuActionItem(
//					ID_PURCHASE_HISTORY,
//					getString(R.string.em_purchase_history), getResources()
//							.getDrawable(R.drawable.emu_purchase_history));
//
//			// add action items into QuickAction
//			quickAction.addActionItem(titleItem);
//			quickAction.addActionItem(editProfileItem);
//			quickAction.addActionItem(pruchaseHistoryItem);
//			quickAction.addActionItem(logoutItem);
//		}
//
//		// Set listener for action item clicked
//		quickAction
//				.setOnActionItemClickListener(new MenuQuickAction.OnActionItemClickListener() {
//					@Override
//					public void onItemClick(MenuQuickAction source, int pos,
//							int actionId) {
//						// here we can filter which action item was clicked with
//						// pos or actionId parameter
//						MenuActionItem actionItem = quickAction
//								.getActionItem(pos);
//
//						if (actionItem.getActionId() == ID_LOGIN) {
//
//							_intentManager
//									.OpenActivity(IntentManager.LOGIN_ACTIVITY);
//
//						} else if (actionItem.getActionId() == ID_LOGOUT) {
//
//							UserInfo.getInstance().reset();
//							// Logout system
//							UserInfo.getInstance().setLogout();
//
//							_intentManager
//									.OpenActivity(IntentManager.HOME_ACTIVITY);
//							// Toast.makeText(getApplicationContext(),
//							// actionItem.getTitle() + " selected",
//							// Toast.LENGTH_SHORT).show();
//
//						} else if (actionItem.getActionId() == ID_SIGNUP) {
//
//							_intentManager
//									.OpenActivity(IntentManager.SIGNUP_ACTIVITY);
//
//						} else if (actionItem.getActionId() == ID_CHANGE_PROFILE) {
//
//							_intentManager
//									.OpenActivity(IntentManager.CHANGE_PROFILE_ACTIVITY);
//
//						} else if (actionItem.getActionId() == ID_PURCHASE_HISTORY) {
//
//							_intentManager
//									.OpenActivity(IntentManager.PURCHASE_HISTORY_ACTIVITY);
//
//						}
//					}
//				});
//
//		// set listnener for on dismiss event, this listener will be called only
//		// if QuickAction dialog was dismissed
//		// by clicking the area outside the dialog.
//		// quickAction
//		// .setOnDismissListener(new MenuQuickAction.OnDismissListener() {
//		// @Override
//		// public void onDismiss() {
//		// Toast.makeText(getApplicationContext(), "Dismissed",
//		// Toast.LENGTH_SHORT).show();
//		// }
//		// });
//
//		return quickAction;
//
//	}

}
