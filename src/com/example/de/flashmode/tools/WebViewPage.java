package com.example.de.flashmode.tools;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewPage extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.webviewpage);
		WebView webView = (WebView) findViewById(R.id.WebView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://www.google.de");
	}
}
