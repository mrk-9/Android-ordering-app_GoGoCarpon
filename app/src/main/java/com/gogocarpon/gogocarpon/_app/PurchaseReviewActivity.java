package com.gogocarpon.gogocarpon._app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.AppConfig;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.gogocarpon.gogocarpon._app.baseclass.Utils;
import com.gogocarpon.gogocarpon._app.models.Deal;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PurchaseReviewActivity extends BaseActivity implements
		OnClickListener {

    private static final String TAG = PurchaseReviewActivity.class.getSimpleName();
    private PurchaseReviewActivity PurchaseReviewActivityRef = null;

	final static public int PAYPAL_REQUEST_CODE = 1;
	final static public int METHOD_PICKUP = 0;
	final static public int METHOD_DELIVERY = 1;
	private int _method = METHOD_PICKUP;

    ImageView paypal_Button;

	protected static final int INITIALIZE_SUCCESS = 0;
	protected static final int INITIALIZE_FAILURE = 1;

	private ProgressDialog _progressDialog;
	private boolean _launchedPayment = false;

	private Deal rowDeal;
	private int buyType;
	private int quantity;

	// private TextView lblPartialPayment;
	// private TextView lblOutstanding;
	private TextView lblName;
	private TextView lblPaymentValue;
	private TextView lblTotalItem;
	private TextView lblTotalPrice;

	private TextView lblReceiverAddress;
	private TextView lblReceiverPhone;

	private TextView lblReceiverName;
	private TextView lblReceiverEmail;
	private TextView lblReceiverMsg;

    private List<PayPalItem> productsInCart = new ArrayList<PayPalItem>();

    private static PayPalConfiguration paypalConfig = new PayPalConfiguration()
            .environment(AppConfig.PAYPAL_ENVIRONMENT).clientId(
                    AppConfig.PAYPAL_CLIENT_ID);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_purchase_review);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        startService(intent);

		setTitle(getString(R.string.em_purchase_review));

		rowDeal = (Deal) _intentParams.get("rowDeal");

		buyType = Integer.parseInt((String) _intentParams.get("buyType"));
		quantity = Integer.parseInt((String) _intentParams.get("quantity"));

        paypal_Button = (ImageView) findViewById(R.id.paypal_Button);
        paypal_Button.setOnClickListener(this);

		// lblPartialPayment = (TextView) findViewById(R.id.lblPartialPayment);
		// lblOutstanding = (TextView) findViewById(R.id.lblOutstanding);
		lblName = (TextView) findViewById(R.id.lblName);
		lblName.setText(rowDeal._name);
		
		lblPaymentValue = (TextView) findViewById(R.id.lblPaymentValue);
		lblTotalItem = (TextView) findViewById(R.id.lblTotalItem);
		lblTotalPrice = (TextView) findViewById(R.id.lblTotalPrice);

		lblReceiverAddress = (TextView) findViewById(R.id.lblReceiverAddress);
		lblReceiverPhone = (TextView) findViewById(R.id.lblReceiverPhone);

		lblReceiverName = (TextView) findViewById(R.id.lblReceiverName);
		lblReceiverEmail = (TextView) findViewById(R.id.lblReceiverEmail);
		lblReceiverMsg = (TextView) findViewById(R.id.lblReceiverMsg);

        Drawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.blue_light));
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        getSupportActionBar().setBackgroundDrawable(ld);

		if (rowDeal._prepay_percent < 100) {
			LinearLayout view = (LinearLayout) findViewById(R.id.viewReceiverInfo);
			view.setVisibility(LinearLayout.VISIBLE);
			// lblReceiverAddress.setText(extras.getString("receiverAddress"));
			// lblReceiverPhone.setText(extras.getString("receiverPhone"));

			lblReceiverAddress.setText((String) _intentParams
					.get("receiverAddress"));
			lblReceiverPhone.setText((String) _intentParams
					.get("receiverPhone"));
		}

		if (buyType == 1) {
			LinearLayout view = (LinearLayout) findViewById(R.id.viewFriendInfo);
			view.setVisibility(LinearLayout.VISIBLE);
			// lblReceiverName.setText(extras.getString("receiverName"));
			// lblReceiverEmail.setText(extras.getString("receiverEmail"));
			// lblReceiverMsg.setText(extras.getString("receiverMsg"));

			lblReceiverName.setText((String) _intentParams.get("receiverName"));
			lblReceiverEmail.setText((String) _intentParams
					.get("receiverEmail"));
			lblReceiverMsg.setText((String) _intentParams.get("receiverMsg"));
		}

		lblPaymentValue.setText(getString(R.string.em_price_signal)
				+ Utils.decimalFormat(AppConfig.DECIMAL_NUMBER_FORMAT,
                rowDeal._price));

		lblTotalItem.setText(quantity + "");
		lblTotalPrice.setText(getString(R.string.em_price_signal)
				+ Utils.decimalFormatPrice(rowDeal._price * quantity));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		super.initActionBarControl(menu);
		return super.onCreateOptionsMenu(menu);
	}

    private PayPalPayment getThingToBuy(String paymentIntent) {

        PayPalItem[] items =
                {
                        new PayPalItem(rowDeal._name, quantity, new BigDecimal(rowDeal._price), AppConfig.DEFAULT_CURRENCY, "sku-12345678")
                };

        BigDecimal subtotal = PayPalItem.getItemTotal(items);
        BigDecimal shipping = new BigDecimal("0.0");
        BigDecimal tax = new BigDecimal("0.0");
        PayPalPaymentDetails paymentDetails = new PayPalPaymentDetails(shipping, subtotal, tax);
        BigDecimal amount = subtotal.add(shipping).add(tax);
        PayPalPayment payment = new PayPalPayment(amount, "USD", rowDeal._name, paymentIntent);
        payment.items(items).paymentDetails(paymentDetails);

        //--- set other optional fields like invoice_number, custom field, and soft_descriptor
        payment.custom(rowDeal._desc);

        return payment;
    }

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.paypal_Button) {
			if (_launchedPayment == false) {
				_launchedPayment = true;

                PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);;

                Intent intent = new Intent(PurchaseReviewActivity.this, PaymentActivity.class);

                paypalConfig.defaultUserEmail(lblReceiverEmail.getText().toString());

                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);

                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

                startActivityForResult(intent, AppConfig.REQUEST_CODE_PAYMENT);

				// Create a basic PayPalPayment.
			}
		}

	}

	// This handler will allow us to properly update the UI. You cannot touch\

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConfig.REQUEST_CODE_PAYMENT) {

            _launchedPayment = false;

            if (resultCode == Activity.RESULT_OK) {

                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (confirm != null) {
                    try {
                        Log.i(TAG, confirm.toJSONObject().toString(4));
                        Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));

                        String paymentId = confirm.toJSONObject()
                                .getJSONObject("response").getString("id");
                        String payment_client = confirm.getPayment()
                                .toJSONObject().toString();
                        this.paymentSucceeded(paymentId,payment_client);
                        /**
                         *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                         * or consent completion.
                         */
                        Toast.makeText(
                                getApplicationContext(),
                                "PaymentConfirmation info received from PayPal", Toast.LENGTH_LONG)
                                .show();

                    } catch (JSONException e) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                this.paymentCanceled();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        TAG,
                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
                this.paymentFailed();
            }
        } else if (requestCode == AppConfig.REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth =
                        data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(
                                getApplicationContext(),
                                "Future Payment code received from PayPal", Toast.LENGTH_LONG)
                                .show();

                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        "FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        } else if (requestCode == AppConfig.REQUEST_CODE_PROFILE_SHARING) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth =
                        data.getParcelableExtra(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("ProfileSharingExample", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("ProfileSharingExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(
                                getApplicationContext(),
                                "Profile Sharing code received from PayPal", Toast.LENGTH_LONG)
                                .show();

                    } catch (JSONException e) {
                        Log.e("ProfileSharingExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("ProfileSharingExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        "ProfileSharingExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
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

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

        /**
         * TODO: Send the authorization response to your server, where it can
         * exchange the authorization code for OAuth access and refresh tokens.
         *
         * Your server must then store these tokens, so that your server code
         * can execute payments for this user in the future.
         *
         * A more complete example that includes the required app-server to
         * PayPal-server integration is available from
         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
         */
        /**
         *
         */
    }
	public void paymentSucceeded(String transactionID,String tracking_content) {
		
		_intentParams.put("payment_status", true);
		_intentParams.put("transaction_id", transactionID);
		_intentParams.put("tracking_content", tracking_content);

		_intentManager.OpenActivity(IntentManager.NOTIFY_BUY_DEAL_ACTIVITY,
				_intentParams);
        finish();
	}

	public void paymentCanceled() {
		// payment canceled
	}

	public void paymentFailed() {
		// payment failed
		
		_intentParams.put("payment_status", false);
		_intentParams.put("transaction_id", "");

		_intentManager.OpenActivity(IntentManager.NOTIFY_BUY_DEAL_ACTIVITY,
				_intentParams);
        finish();
		
	}

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

}
