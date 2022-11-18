package com.example.gymmanagementapp.service;


import static android.os.Build.VERSION.SDK_INT;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.gymmanagementapp.AdminDashBoard;
import com.example.gymmanagementapp.FirstActivity;
import com.example.gymmanagementapp.MainActivity;
import com.example.gymmanagementapp.R;
import com.example.gymmanagementapp.UserDashBoard;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Random;


public class NotificationService extends FirebaseMessagingService {

    private static final String TAG = NotificationService.class.getSimpleName();

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        //update Token.
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Gson gson = new Gson();
        Log.e("message", gson.toJson(message).toString());
        Log.e(TAG, "From: " + message.getFrom());

        // Check if message contains a data payload.
        if (message.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + message.getData());
            SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
            ;
            if (preferences.contains("num")) {
                if (preferences.getString("num", "").equals("9999999999")) {
                    //show admin notifications
                    Log.e(TAG, "User id admin so ignoring message..");

                } else {
                    //show user notifications ignore notifications
                    if (message.getData().get("id") != null) {
                        if (message.getData().get("id").equals(preferences.getString("id", ""))) {
                            sendNotification(message.getData().get("title"), message.getData().get("data"));
                        }
                    } else
                        Log.e(TAG, "No id");
                }
            } else {
                //user not logged in
            }
        }

        // Check if message contains a notification payload.
        if (message.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + message.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void sendNotification(String title, String message) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int flags;
        if (SDK_INT >= Build.VERSION_CODES.S) {
            flags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE;
        } else {
            flags = PendingIntent.FLAG_UPDATE_CURRENT;
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, flags);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setStyle(
                        new NotificationCompat.BigTextStyle()
                                .bigText(message)
                )
                .setContentText(message)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Gym Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }
}

