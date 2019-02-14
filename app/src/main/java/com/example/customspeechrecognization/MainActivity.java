package com.example.customspeechrecognization;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;



import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ImageButton button;
    String message;
    TextView tv_listen;
    MyCountDownTimer myCountDownTimer;
     EditText editText;
    SpeechRecognizer mSpeechRecognizer;
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (ImageButton) findViewById(R.id.button);
        tv_listen = (TextView) findViewById(R.id.tv_listen);

        checkPermission();
        editText = findViewById(R.id.editText);
          mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int errorCode) {
                switch (errorCode) {
                    case SpeechRecognizer.ERROR_AUDIO:
                        message = String.valueOf(R.string.error_audio_error);
                        break;
                    case SpeechRecognizer.ERROR_CLIENT:
                        message = String.valueOf(R.string.error_client);
                        break;
                    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                        message = String.valueOf(R.string.error_permission);
                        break;
                    case SpeechRecognizer.ERROR_NETWORK:
                        message = String.valueOf(R.string.error_network);
                        break;
                    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                        message = String.valueOf(R.string.error_timeout);
                        break;
                    case SpeechRecognizer.ERROR_NO_MATCH:
                        message = String.valueOf(R.string.error_no_match);
                        break;
                    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                        message = String.valueOf(R.string.error_busy);
                        break;
                    case SpeechRecognizer.ERROR_SERVER:
                        message = String.valueOf(R.string.error_server);
                        break;
                    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                        message = String.valueOf(R.string.error_timeout);
                        break;
                    default:
                        message = String.valueOf(R.string.error_understand);
                        break;
                }
            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                //displaying the first match
                if (matches != null) {
                    editText.setText(matches.get(0));
                    editText.setSelection(editText.getText().length());
                    button.setVisibility(View.VISIBLE);
                    tv_listen.setVisibility(View.GONE);

                } else {
                    button.setVisibility(View.GONE);
                    tv_listen.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
        //Toast.makeText(this,message,Toast.LENGTH_LONG).show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCountDownTimer = new MyCountDownTimer(5000, 1000);
                myCountDownTimer.start();
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                    editText.setText("");
                    editText.setSelection(editText.getText().length());
                    editText.setHint("Listening...");
                    button.setEnabled(false);
                    button.setVisibility(View.GONE);
                    tv_listen.setVisibility(View.VISIBLE);

            }
        });

//    button1.setEnabled(false);
//    new CountDownTimer(5000, 10) { //Set Timer for 5 seconds
//      public void onTick(long millisUntilFinished) {
//      }
//
//      @Override
//      public void onFinish() {
//        button1.setEnabled(true);
//      }
//    }.start()


        //*****On press record****//
//    button.setOnTouchListener(new View.OnTouchListener() {
//      @Override
//      public boolean onTouch(View view, MotionEvent motionEvent) {
//        switch (motionEvent.getAction()) {
//          case MotionEvent.ACTION_UP:
//            mSpeechRecognizer.stopListening();
//            editText.setHint("You will see input here");
//            break;
//
//          case MotionEvent.ACTION_DOWN:
//            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
//            editText.setText("");
//            editText.setHint("Listening...");
//            break;
//        }
//        return false;
//      }
//    });

    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }
    public  class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

        }

        @Override
        public void onTick(long millisUntilFinished) {
            int progress = (int) (millisUntilFinished/1000);
            button.setEnabled(false);
//            button.setVisibility(View.GONE);
//            tv_listen.setVisibility(View.VISIBLE);
            editText.setHint("Listening...");
            editText.setSelection(editText.getText().length()); // End point Cursor
        }
        @Override
        public void onFinish() {
            mSpeechRecognizer.stopListening();
            button.setEnabled(true);
            button.setVisibility(View.VISIBLE);
            tv_listen.setVisibility(View.GONE);
            editText.setSelection(editText.getText().length());
            editText.setHint("You will see input here");
            editText.setSelection(editText.getText().length()); // End point Cursor

        }
    }
}