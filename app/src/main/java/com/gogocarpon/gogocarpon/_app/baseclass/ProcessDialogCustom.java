package com.gogocarpon.gogocarpon._app.baseclass;

import android.app.ProgressDialog;
import android.content.Context;

public class ProcessDialogCustom extends ProgressDialog {

	private ProgressDialog processDialog;
	boolean isMyDialogShowing;

	public ProcessDialogCustom(Context context) {
		super(context);
		if (processDialog == null) {
			this.processDialog = new ProgressDialog(context);
//			this.processDialog.setIndeterminateDrawable(context.getResources()
//					.getDrawable(R.drawable.my_progress_indeterminate));
		}
	}

	public void initDialog(CharSequence title, CharSequence message) {
		processDialog.setTitle(title);
		processDialog.setMessage(message);
		processDialog.setIndeterminate(true);
		processDialog.setCancelable(false);
	}

	public void showDialog() {
		this.isMyDialogShowing = true;
		if (processDialog != null && !processDialog.isShowing()) {
			processDialog.show();
		}
	}

	public void dismissMyDialog() {
		if (processDialog != null && processDialog.isShowing()) {
			processDialog.dismiss();
		}
	}

	public void killMyDialog() {
		this.isMyDialogShowing = false;
	}

	public boolean isShowing() {
		return isMyDialogShowing;
	}

}
