package com.example.texttospeechdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech mTTS;
    private EditText editText;
    private SeekBar seekBarPich;
    private SeekBar seekBarSpeed;
    private Button btnSpeak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        seekBarPich = findViewById(R.id.seekBar_pitch);
        seekBarSpeed = findViewById(R.id.seekBar_speed);
        btnSpeak = findViewById(R.id.btn_speek);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int resutl = mTTS.setLanguage(Locale.CANADA);
                    if (resutl == TextToSpeech.LANG_MISSING_DATA || resutl == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS", "Language not supported");
                    }else {
                        btnSpeak.setEnabled(true);
                    }
                }else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speek();
            }
        });
    }

    private void speek(){
        String text = editText.getText().toString();
        float pitch = (float) seekBarPich.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) seekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null){
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }
}