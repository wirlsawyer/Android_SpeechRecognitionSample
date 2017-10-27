package com.example.android_speechrecognitionsample;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView mTextView = null;
	private Button mButton = null;
	private static final int RQS_VOICE_RECOGNITION = 1;
	private SYVoiceRecognizer mRecognizer = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mRecognizer = new SYVoiceRecognizer(MainActivity.this);

		mTextView = (TextView) findViewById(R.id.textView1);
		mButton = (Button) findViewById(R.id.button1);
		mButton.setOnClickListener(onClicked);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private OnClickListener onClicked = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			int method = 2;
			if (method == 1) {
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				// Specify language
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
						Locale.TRADITIONAL_CHINESE);
				intent.putExtra(
						RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE,
						Locale.TRADITIONAL_CHINESE);
				// Specify language model
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
						RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
				// Specify how many results to receive
				intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
				// Title
				intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speech");
				// Start listening
				startActivityForResult(intent, RQS_VOICE_RECOGNITION);
			} else if (method == 2) {
				mRecognizer.Start();
			}

		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == RQS_VOICE_RECOGNITION) {
			if (resultCode == RESULT_OK) {

				ArrayList<String> result = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				String firstMatch = (String) result.get(0);
				mTextView.setText(firstMatch);
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mRecognizer.Destory();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// TODO Auto-generated method stub
		mRecognizer.Destory(); 
	}

	@Override
	protected void onResume() {
		super.onResume();
		// TODO Auto-generated method stub 
		mRecognizer.Start(); 
	}
}
