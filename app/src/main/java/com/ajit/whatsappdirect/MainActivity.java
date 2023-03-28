package com.ajit.whatsappdirect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    public String DEFAULT_MSG ="Hello";

    public LinearLayout card1;
    public LinearLayout card2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        AppCompatEditText appCompatEditText = (AppCompatEditText) findViewById(R.id.et1);
        AppCompatEditText appCompatEditText2 = (AppCompatEditText) findViewById(R.id.et2);
        AppCompatEditText appCompatEditText3 = (AppCompatEditText) findViewById(R.id.et3);
        this.card1 = (LinearLayout) findViewById(R.id.send_card1);
        this.card2 = (LinearLayout) findViewById(R.id.send_card2);
        this.card1.setVisibility(View.VISIBLE);
        this.card2.setVisibility(View.GONE);
        ((RadioGroup) findViewById(R.id.radiogroup)).setOnCheckedChangeListener(new radiobtn());
        appCompatEditText3.setHint("\nSkip +91 Code and Paste all numbers\nMessage will be send to each number one by one individually by coming back to this app after every click on Whatsapp.");




        ((AppCompatButton) findViewById(R.id.senddefaultbutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] numbers = appCompatEditText3.getText().toString().split("[,\\s]+");
                String s1 = appCompatEditText.getText().toString();
                if (s1 != null && !s1.isEmpty()) {
                    sendmsg(s1, DEFAULT_MSG);
                } else if (numbers != null) {
                    for (String number : numbers) {
                        sendmsg(number, DEFAULT_MSG);
                    }
                }

            }
        });


        ((AppCompatButton) findViewById(R.id.custombutton1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numbers[] = appCompatEditText3.getText().toString().split("[,\\s]+");

                String s1 = appCompatEditText.getText().toString();
                String s2 = appCompatEditText2.getText().toString();
                if (s1 != null && !s1.isEmpty()) {
                    sendmsg(s1, s2);
                } else if (numbers != null) {
                    for (String number : numbers) {
                        sendmsg(number, s2);
                    }
                }
            }
        });

    }








        public final boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem menuItem) {


        switch (menuItem.getItemId()) {

            case R.id.menu_setting:
                Intent intent = new Intent(this, setting.class);
                startActivity(intent);
                break;
            case R.id.menu_upload:

                startActivity( new Intent(this, SendCsvMessagesActivity.class));
                break;
            case R.id.menu_share:

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");

                i.putExtra(Intent.EXTRA_TEXT, "Sharing");
                startActivity(Intent.createChooser(i, "Suggest"));
                startActivity(i);
                break;
        }
            return super.onOptionsItemSelected(menuItem);
        }






    public class radiobtn implements RadioGroup.OnCheckedChangeListener {
        public radiobtn() {
        }

        @Override
        public final void onCheckedChanged(RadioGroup radioGroup, int i2) {
            if (i2 == R.id.custommsg) {
                MainActivity.this.card1.setVisibility(View.GONE);
                MainActivity.this.card2.setVisibility(View.VISIBLE);
            } else if (i2 != R.id.defaultmsg) {
            } else {
                MainActivity.this.card1.setVisibility(View.VISIBLE);
                MainActivity.this.card2.setVisibility(View.GONE);
            }
        }
    }




        public final void sendmsg(String str, String str2) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + ("91" + str) + "&text=" + str2));
            startActivity(intent);
        } catch (Exception e6) {
            e6.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        DEFAULT_MSG = sharedPreferences.getString("DEFAULT_MSG", "Hello");
    }


    }

