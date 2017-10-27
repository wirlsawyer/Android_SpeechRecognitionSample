package com.example.android_speechrecognitionsample;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

public class SYVoiceRecognizer {
	private Activity mActivity;
	private Intent mIntent;
	private SpeechRecognizer mRecognizer = null;
	private long mErrorTick = 0;
	
	public SYVoiceRecognizer(Activity activity) {
		mActivity = activity;
		mRecognizer = SpeechRecognizer.createSpeechRecognizer(activity);
		mRecognizer.setRecognitionListener(onRecognition);
		
		mIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		// Specify language
		// intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
		// Locale.TRADITIONAL_CHINESE);
		// intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE,
		// Locale.TRADITIONAL_CHINESE);
		// Specify language model
		mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		mIntent.putExtra(
				RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,
				500);
	}

	public void Start() {
		MuteAudio(mActivity); 
		mRecognizer.startListening(mIntent);
		UnMuteAudio(mActivity);
	}

	public void Stop() {
		mRecognizer.stopListening();
	}

	public void Destory() {
		mRecognizer.destroy();
	}

	private RecognitionListener onRecognition = new RecognitionListener() {

		@Override
		public void onBeginningOfSpeech() {
			// TODO Auto-generated method stub
			Log.d("TTTTT", "RecognitionListener onBeginningOfSpeech");
		}

		@Override
		public void onBufferReceived(byte[] buffer) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onEndOfSpeech() {
			// TODO Auto-generated method stub
			Log.d("TTTTT", "RecognitionListener onEndOfSpeech");
		}

		@Override
		public void onError(int error) {
			// TODO Auto-generated method stub
			if (mErrorTick + 1000 > System.currentTimeMillis()) return;
			mErrorTick = System.currentTimeMillis();
			
			String mError = "";
			String mStatus = "Error detected";
	        switch (error) {
	        case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:                
	            mError = " network timeout"; 
	            Start();
	            break;
	        case SpeechRecognizer.ERROR_NETWORK: 
	            mError = " network" ;
	            //toast("Please check data bundle or network settings");
	            return;
	        case SpeechRecognizer.ERROR_AUDIO: 
	            mError = " audio"; 
	            break;
	        case SpeechRecognizer.ERROR_SERVER: 
	            mError = " server"; 
	            Start();
	            break;
	        case SpeechRecognizer.ERROR_CLIENT: 
	            mError = " client"; 
	            break;
	        case SpeechRecognizer.ERROR_SPEECH_TIMEOUT: 
	            mError = " speech time out" ; 
	            Start();
	            break;
	        case SpeechRecognizer.ERROR_NO_MATCH: 
	            mError = " no match" ; 
	            Start(); 
	            break;
	        case SpeechRecognizer.ERROR_RECOGNIZER_BUSY: 
	            mError = " recogniser busy" ; 
	            break;
	        case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS: 
	            mError = " insufficient permissions" ; 
	            break;

	        } 
			Log.e("TTTTT", "RecognitionListener Error Code: " + error + " " + mError);
		}

		@Override
		public void onEvent(int eventType, Bundle params) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPartialResults(Bundle partialResults) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onReadyForSpeech(Bundle params) {
			// TODO Auto-generated method stub
			//final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            //tg.startTone(ToneGenerator.TONE_PROP_BEEP);
		}

		@Override
		public void onResults(Bundle results) {
			// TODO Auto-generated method stub
			List<String> resList = results
					.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
			StringBuffer sb = new StringBuffer();
			for (String res : resList) {
				sb.append(res + "\n");
			}

			Log.d("TTTTT", "RecognitionListener onResults: " + sb.toString());
			Start();
		}

		@Override
		public void onRmsChanged(float rmsdB) {
			// TODO Auto-generated method stub

		}

	};

	public void MuteAudio(Activity activity) {
		AudioManager mAlramMAnager = (AudioManager) activity
				.getSystemService(Context.AUDIO_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION,
					AudioManager.ADJUST_MUTE, 0);
			mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_ALARM,
					AudioManager.ADJUST_MUTE, 0);
			mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_MUTE, 0);
			mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_RING,
					AudioManager.ADJUST_MUTE, 0);
			mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_SYSTEM,
					AudioManager.ADJUST_MUTE, 0);
		} else {
			mAlramMAnager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
			mAlramMAnager.setStreamMute(AudioManager.STREAM_ALARM, true);
			mAlramMAnager.setStreamMute(AudioManager.STREAM_MUSIC, true);
			mAlramMAnager.setStreamMute(AudioManager.STREAM_RING, true);
			mAlramMAnager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
		}
	}

	public void UnMuteAudio(Activity activity) {
		AudioManager mAlramMAnager = (AudioManager) activity
				.getSystemService(Context.AUDIO_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION,
					AudioManager.ADJUST_UNMUTE, 0);
			mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_ALARM,
					AudioManager.ADJUST_UNMUTE, 0);
			mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_UNMUTE, 0);
			mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_RING,
					AudioManager.ADJUST_UNMUTE, 0);
			mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_SYSTEM,
					AudioManager.ADJUST_UNMUTE, 0);
		} else {
			mAlramMAnager
					.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
			mAlramMAnager.setStreamMute(AudioManager.STREAM_ALARM, false);
			mAlramMAnager.setStreamMute(AudioManager.STREAM_MUSIC, false);
			mAlramMAnager.setStreamMute(AudioManager.STREAM_RING, false);
			mAlramMAnager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
		}
	}
}
