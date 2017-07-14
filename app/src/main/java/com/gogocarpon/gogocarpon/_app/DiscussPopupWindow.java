package com.gogocarpon.gogocarpon._app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.AppConfig;
import com.gogocarpon.gogocarpon._app.baseclass.GrabRequestUrl;
import com.gogocarpon.gogocarpon._app.baseclass.UserInfo;
import com.gogocarpon.gogocarpon._app.baseclass.Utils;
import com.gogocarpon.gogocarpon._app.models.Deal;
import com.gogocarpon.gogocarpon._app.ui.listeners.IGrabRequestUrlListener;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import java.io.UnsupportedEncodingException;


public class DiscussPopupWindow extends Dialog implements
		View.OnClickListener,
        IGrabRequestUrlListener<String, Integer> {

	// Properties
	private static DiscussPopupWindow DiscussPopupWindowRef = null;

	// Constant
	public static final int REQUEST_WRITE_COMMENT = 1;

	private Button btnCommit;
	private Button btnCancel;

	private EditText txtComment;
	private RatingBar ratingBar;
	private float ratingCount = 0;

	private Deal rowDeal;
	
	public static boolean isPostComment = true;

	public DiscussPopupWindow(Context context, Deal row) {
		super(context);
		rowDeal = row;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.wpopup_discuss_form);
		this.setTitle(null);
		this.setCancelable(true);

		DiscussPopupWindowRef = this;

		// set up button
		btnCommit = (Button) this.findViewById(R.id.btnCommit);
		btnCommit.setOnClickListener(this);
		btnCancel = (Button) this.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);

		txtComment = (EditText) this.findViewById(R.id.txtComment);
		ratingBar = (RatingBar) this.findViewById(R.id.ratingBar);
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				ratingCount = rating;

				Log.i("LOG APP", "ratingCount = " + ratingCount);
				
			}
		});

	}

	@Override
	public void onClick(View v) {

		if (v == btnCommit) {

			// Validate data
			if (checkValidateFields() == false) {
				return;
			}

			MultipartEntity reqEntity = new MultipartEntity();

			try {
				reqEntity.addPart("deal_id", new StringBody(rowDeal._id));
				reqEntity.addPart("token",
						new StringBody(UserInfo.getInstance().token));
				reqEntity.addPart("rating_count",
						new StringBody(String.valueOf(ratingCount)));
				reqEntity.addPart("comment_content", new StringBody(txtComment
						.getText().toString()));
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			GrabRequestUrl urlrequest = new GrabRequestUrl(
					DiscussPopupWindow.this.getContext(), this, reqEntity);

			Log.d("My Log", "Request server loadDealFromWebService :"
					+ AppConfig.URI_DEAL_COMMENT_ADD);

			urlrequest.executeRequest(AppConfig.URI_DEAL_COMMENT_ADD,
					REQUEST_WRITE_COMMENT);

		} else if (v == btnCancel) {
			isPostComment = false;
			dismiss();
		}

	}

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

		
		if (messageID == REQUEST_WRITE_COMMENT) {

			Utils.ShowNotification(DiscussPopupWindowRef.getContext(), this.getContext().getString(R.string.notify),
                    this.getContext().getString(R.string.notify_post_comment_success));
			
			isPostComment = true;
			this.dismiss();

		}

	}

	@Override
	public void onAsyncTaskError(String contentData, Integer messageID) {

		if (messageID == REQUEST_WRITE_COMMENT) {
			Utils.ShowNotification(DiscussPopupWindowRef.getContext(), this.getContext().getString(R.string.error),
					this.getContext().getString(R.string.error_post_comment));
			return;
		}

	}

	// Private Function
	private boolean checkValidateFields() {

		if (Utils.isEmpty(txtComment.getText().toString())) {
			Utils.ShowNotification(DiscussPopupWindowRef.getContext(), this.getContext().getString(R.string.error),
					this.getContext().getString(R.string.error_input_comment));
			return false;
		}
		return true;
	}



}
