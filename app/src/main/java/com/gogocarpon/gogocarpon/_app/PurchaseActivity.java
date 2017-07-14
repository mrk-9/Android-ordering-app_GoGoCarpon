package com.gogocarpon.gogocarpon._app;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.gogocarpon.gogocarpon._app.baseclass.Utils;
import com.gogocarpon.gogocarpon._app.models.Deal;


public class PurchaseActivity extends BaseActivity {

	private PurchaseActivity PurchaseActivityRef;

	private Deal rowDeal;
	private int buyType;
	private int quantity;

	private TextView lblName;
//	private TextView lblPartialPayment;
//	private TextView lblOutstanding;
	private TextView lblPaymentValue;
	private TextView lblTotalItem;
	private TextView lblTotalPrice;

	private Spinner selQuantity;

	private Button btnPurchase;

	private EditText txtReceiverAddress;
	private EditText txtReceiverPhone;

	private EditText txtReceiverName;
	private EditText txtReceiverEmail;
	private EditText txtReceiverMsg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_purchase);
		
		PurchaseActivityRef = this;
		
		setTitle(getString(R.string.em_purchase));

		rowDeal = (Deal) _intentParams.get("rowDeal");

		buyType = Integer.parseInt((String)_intentParams.get("buyType"));
		
		lblName = (TextView) findViewById(R.id.lblName);
		lblName.setText(rowDeal._name);
		
//		lblPartialPayment = (TextView) findViewById(R.id.lblPartialPayment);
//		lblOutstanding = (TextView) findViewById(R.id.lblOutstanding);
		lblPaymentValue = (TextView) findViewById(R.id.lblPaymentValue);
		lblTotalItem = (TextView) findViewById(R.id.lblTotalItem);
		lblTotalPrice = (TextView) findViewById(R.id.lblTotalPrice);

		txtReceiverAddress = (EditText) findViewById(R.id.txtReceiverAddress);
		txtReceiverPhone = (EditText) findViewById(R.id.txtReceiverPhone);
		
		txtReceiverName = (EditText) findViewById(R.id.txtReceiverName);
		txtReceiverEmail = (EditText) findViewById(R.id.txtReceiverEmail);
		txtReceiverMsg = (EditText) findViewById(R.id.txtReceiverMsg);
		
		// Demo data
//		txtReceiverAddress.setText("123 le loi"); 
//		txtReceiverPhone.setText("123456789");
//		
//		txtReceiverName.setText("Hoang Le");
//		txtReceiverEmail.setText("hoangle@yahoo.com");
//		txtReceiverMsg.setText("Chuyen phat nhanh");
		// --------------------------------------------

        Drawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.blue_light));
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        getSupportActionBar().setBackgroundDrawable(ld);

		if (rowDeal._prepay_percent < 100) {
			LinearLayout view = (LinearLayout) findViewById(R.id.viewReceiverInfo);
			view.setVisibility(LinearLayout.VISIBLE);
		}

		if (buyType == 1) {
			LinearLayout view = (LinearLayout) findViewById(R.id.viewFriendInfo);
			view.setVisibility(LinearLayout.VISIBLE);
		}

		selQuantity = (Spinner) findViewById(R.id.selQuantity);
		selQuantity.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// items[0] = "One";
				// selectedItem = items[position];
				quantity = position + 1;
				updatePaymentSchedule();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		// Update payment schedule
		quantity = 1;
		updatePaymentSchedule();

		// Button action
		btnPurchase = (Button) findViewById(R.id.btnPurchase);
		btnPurchase.setOnClickListener(btnPurchaseListener);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		super.initActionBarControl(menu);
		return super.onCreateOptionsMenu(menu);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Overwrite button home function
            //menu.toggle();
//	    	Toast.makeText(getApplicationContext(), "Overwrite button home function",Toast.LENGTH_LONG).show();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	/*
	 * 
	 * */

	// Create an anonymous implementation of OnClickListener
	private OnClickListener btnPurchaseListener = new OnClickListener() {
		public void onClick(View v) {

			if (checkValidateFields() == false) {
				return;
			}

			_intentParams.put("receiverAddress", txtReceiverAddress.getText()
					.toString());
			_intentParams.put("receiverPhone", txtReceiverPhone.getText().toString());

			_intentParams.put("receiverName", txtReceiverName.getText().toString());
			_intentParams.put("receiverEmail", txtReceiverEmail.getText().toString());
			_intentParams.put("receiverMsg", txtReceiverMsg.getText().toString());

			_intentParams.put("quantity", quantity + "");
			
			_intentManager.OpenActivity(IntentManager.REVIEW_BUY_DEAL_ACTIVITY, _intentParams);
            finish();
		}
	};

	private void updatePaymentSchedule() {
		float total_price = rowDeal._price * quantity;
//		float partial_pay = 0, outstanding_pay = 0;

//		if (rowDeal._prepay_percent < 100) {
//			// $this->cart->getTotalItem() * $item_price * $item->prepay_percent
//			// / 100
//			partial_pay = total_price * rowDeal._prepay_percent / 100;
//
//			// $this->cart->getTotalItem() * $item_price * (100 -
//			// $item->prepay_percent) / 100
//			outstanding_pay = total_price * (100 - rowDeal._prepay_percent)
//					/ 100;
//		}
		
//		lblPartialPayment.setText(Utils.decimalFormatPrice(partial_pay));
//		lblOutstanding.setText(Utils.decimalFormatPrice(outstanding_pay));
		lblPaymentValue.setText(Utils.decimalFormatPrice(rowDeal._price));
		
//		lblPartialPayment.setText(Utils.decimalFormat(
//				AppConfig.DECIMAL_NUMBER_FORMAT, partial_pay));
//		lblOutstanding.setText(Utils.decimalFormat(
//				AppConfig.DECIMAL_NUMBER_FORMAT, outstanding_pay));
//		lblPaymentValue.setText(Utils.decimalFormat(
//				AppConfig.DECIMAL_NUMBER_FORMAT, rowDeal._price));

		lblTotalItem.setText(quantity + "");
		lblTotalPrice.setText(Utils.decimalFormatPrice(total_price));		
//		lblTotalPrice.setText(Utils.decimalFormat(
//				AppConfig.DECIMAL_NUMBER_FORMAT, total_price));

	}

	private boolean checkValidateFields() {

		if (rowDeal._prepay_percent < 100) {
			if (Utils.isEmpty(txtReceiverAddress.getText().toString())) {
				Utils.ShowNotification(PurchaseActivityRef, getString(R.string.error),
						getString(R.string.error_input_receiver_address_empty));
				return false;
			}
			if (Utils.isEmpty(txtReceiverPhone.getText().toString())) {
				Utils.ShowNotification(PurchaseActivityRef, getString(R.string.error),
						getString(R.string.error_input_receiver_phone_empty));
				return false;
			}
		}

		if (buyType == 1) {
			if (Utils.isEmpty(txtReceiverName.getText().toString())) {
				Utils.ShowNotification(PurchaseActivityRef, getString(R.string.error),
						getString(R.string.error_input_receiver_name_empty));
				return false;
			}
			if (!Utils.isEmail(txtReceiverEmail.getText().toString())) {
				Utils.ShowNotification(PurchaseActivityRef, getString(R.string.error),
						getString(R.string.error_input_email_invalied));
				return false;
			}

		}
		return true;
	}

}
