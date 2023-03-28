package com.ajit.whatsappdirect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SendCsvMessagesActivity extends AppCompatActivity {


        private static final int REQUEST_CODE_CSV_FILE = 1;

        private EditText customMessageEditText;
        private AppCompatButton sendButton;
        private AppCompatButton csvUploadButton;

        private List<String> numberList = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_send_csv_messages);

            customMessageEditText = findViewById(R.id.custom_message_edittext);
            sendButton = findViewById(R.id.send_button);
            csvUploadButton = findViewById(R.id.csv_upload_button);

            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String customMessage = customMessageEditText.getText().toString().trim();
                    if (!TextUtils.isEmpty(customMessage) && !numberList.isEmpty()) {
                        sendMessages(customMessage, numberList);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter a message and select at least one number", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            csvUploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Open file picker dialog
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("text/csv");
                    startActivityForResult(intent, REQUEST_CODE_CSV_FILE);
                }
            });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE_CSV_FILE && resultCode == RESULT_OK) {
                if (data != null && data.getData() != null) {
                    Uri csvFileUri = data.getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(csvFileUri);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] fields = line.split(",");
                            String countryCode = fields[0].trim();
                            String phoneNumber = fields[1].trim();
                            String fullNumber = countryCode + phoneNumber;
                            numberList.add(fullNumber);
                        }
                        reader.close();
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void sendMessages(String customMessage, List<String> numberList) {
            for (String number : numberList) {
                try {
                    String message = URLEncoder.encode(customMessage, "UTF-8");
                    String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + message;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
