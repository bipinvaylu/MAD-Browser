package com.mad.browser;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends Activity
{

	protected WebView mWebView;
	protected EditText mUrlAddress;
	protected ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		mUrlAddress = (EditText) findViewById(R.id.urlAddress);
		mWebView = (WebView) findViewById(R.id.webView);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new MyWebViewClient());
		mUrlAddress.setText("http://www.google.com");
		mWebView.loadUrl("http://www.google.com");

		findViewById(R.id.go).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String url = mUrlAddress.getText().toString();
				if(url == null || url.length() == 0) {
					mUrlAddress.setError("please enter valid url");
					return;

				}
				// Append http:// if not
				if(!(url.startsWith("http://") || url.startsWith("https://"))) {
					url = "http://" + url;
				}

				mWebView.loadUrl(url);
				mWebView.requestFocus();
			}
		});
	}

	public class MyWebViewClient extends WebViewClient
	{
		@Override
		public void onPageStarted(WebView webView, String url, Bitmap bmp) {
			super.onPageStarted(webView, url, bmp);
			mProgressBar.setVisibility(View.VISIBLE);
			if(!url.equals(mUrlAddress.getText().toString())) {
				mUrlAddress.setText(url);
				webView.requestFocus();
			}
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView webView, String url) {
			webView.loadUrl(url);
			return true;
		}

		@Override
		public void onPageFinished(WebView webView, String url) {
			super.onPageFinished(webView, url);
			mProgressBar.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
