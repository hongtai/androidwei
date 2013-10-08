package com.example.de.flashmode.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FindOrderedArticleActivity extends Activity implements
		OnClickListener {
	private Button submitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_ordered_article);

		submitButton = (Button) findViewById(R.id.submit_button);
		submitButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		EditText emailField = (EditText) findViewById(R.id.email_field);
		String email = emailField.getText().toString();

		if (email == null || email.trim().isEmpty()) {
			new AlertDialog.Builder(this)
					.setMessage(R.string.error_email_missing)
					.setNeutralButton(R.string.error_ok, null).show();
			return;
		}
		
		if(view == submitButton) {
			int resourceId = R.string.submit_greeting;
			String greeting = getResources().getString(resourceId, email);
			//Toast.makeText(this, greeting,  Toast.LENGTH_LONG).show();
			Intent intent = new Intent();
			intent.setClass(FindOrderedArticleActivity.this, ResultOrderedArticleActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("customerEmail", email);
			intent.putExtras(bundle);
			
			startActivity(intent);
			FindOrderedArticleActivity.this.finish();
		}
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.find_ordered_article, menu);
	// return true;
	// }

}
