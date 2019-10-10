package com.medialink.stacknotif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etSender, etMessage;
    private int idNotification = 0;
    private final List<NotificationItem> stackNotif = new ArrayList<>();

    private static final CharSequence CHANNEL_NAME = "dicoding channel";
    private final static String GROUP_KEY_EMAILS = "group_key_emails";
    private final static int NOTIFICATION_REQUEST_CODE = 200;
    private static final int MAX_NOTIFICATION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSender = findViewById(R.id.et_sender);
        etMessage = findViewById(R.id.et_message);
        Button btnSend = findViewById(R.id.btn_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sender = etSender.getText().toString();
                String message = etMessage.getText().toString();

                if (sender.isEmpty() || message.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Data harus diisi", Toast.LENGTH_SHORT).show();
                } else {
                    stackNotif.add(new NotificationItem(idNotification, sender, message));
                    sendNotif();
                    idNotification++;

                    etSender.setText("");
                    etMessage.setText("");
                    InputMethodManager methodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    methodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
    }

    private void sendNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_white_24dp);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder;

        // TODO: lanjutkan lagi besok

    }
}
