package com.gogocarpon.gogocarpon._app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.ActParameter;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.gogocarpon.gogocarpon._app.baseclass.Utils;


public class SignupActivity extends BaseActivity {
	
	// Properties
	private static SignupActivity SignupActivityRef = null;

	// Constant
	public static final int REQUEST_SIGNUP = 1;

	private EditText txtUsername;
	private EditText txtPassword;
	private EditText txtRePassword;

	private EditText txtName;
	private EditText txtEmail;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_signup);
        
        SignupActivityRef = this;
        
        setTitle(getString(R.string.em_signup));
        
		txtUsername = (EditText) findViewById(R.id.txtUsername);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		txtRePassword = (EditText) findViewById(R.id.txtRePassword);

		txtName = (EditText) findViewById(R.id.txtName);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		
		Button btnSignup = (Button) findViewById(R.id.btnSignup);
		btnSignup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Validate data
				if (checkValidateFields() == false) {
					return;
				}
				
				
				ActParameter params = new ActParameter();
				params.put("txtName", txtName.getText().toString());
				params.put("txtEmail", txtEmail.getText().toString());
				params.put("txtUsername", txtUsername.getText().toString());
				params.put("txtPassword", txtPassword.getText().toString());
				
				// Open Agree View
				_intentManager.OpenActivity(IntentManager.SIGNUP_AGREE_ACTIVITY, params);

			}
		});
        
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

    // Private Function
	private boolean checkValidateFields() {

		if (Utils.isEmpty(txtName.getText().toString())) {
			Utils.ShowNotification(SignupActivityRef, getString(R.string.error),
					getString(R.string.error_input_display_name_empty));
			return false;
		}
		
		if (Utils.isEmpty(txtUsername.getText().toString())) {
			Utils.ShowNotification(SignupActivityRef, getString(R.string.error),
					getString(R.string.error_input_username_empty));
			return false;
		}

		if (!Utils.isEmail(txtEmail.getText().toString())) {
			Utils.ShowNotification(SignupActivityRef, getString(R.string.error),
					getString(R.string.error_input_email_invalied));
			return false;
		}

		if (Utils.isEmpty(txtPassword.getText().toString())) {
			Utils.ShowNotification(SignupActivityRef, getString(R.string.error),
					getString(R.string.error_input_pw_empty));
			return false;
		}
		
		if (!txtRePassword.getText().toString().equals(txtPassword.getText().toString())) {
			Utils.ShowNotification(SignupActivityRef, getString(R.string.error),
					getString(R.string.error_input_verify_pw));
			return false;
			
		}
		
		return true;
	}
    
    
}
