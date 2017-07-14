package com.gogocarpon.gogocarpon._app.ui.listeners;

import android.view.View;
import android.view.View.OnClickListener;

public class CallbackOnListViewClickListener implements OnClickListener {
	private int position;
	private int iAction;
	private OnCustomListViewClickListener callback;

	// Pass in the callback (this'll be the activity) and the row position
	public CallbackOnListViewClickListener(
			OnCustomListViewClickListener callback, int pos, int action) {
		position = pos;
		iAction = action;
		this.callback = callback;
	}

	// The onClick method which has NO position information
	@Override
	public void onClick(View v) {

		// Let's call our custom callback with the position we added in the
		// constructor
		callback.OnCustomListViewClick(v, position, iAction);
	}
}