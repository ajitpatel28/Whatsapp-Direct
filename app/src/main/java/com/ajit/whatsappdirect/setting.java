package com.ajit.whatsappdirect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ajit.whatsappdirect.databinding.ActivitySettingsBinding;

public class setting extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        AppCompatButton settingSave = (AppCompatButton) findViewById(R.id.setting_save_btn);

        settingSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                EditText editText = (EditText) findViewById(R.id.settings_edittext);
                String message = editText.getText().toString();
                editor.putString("DEFAULT_MSG", message);
                editor.apply();

                Toast.makeText(setting.this, "Your default message has been successfully saved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });



    }
}