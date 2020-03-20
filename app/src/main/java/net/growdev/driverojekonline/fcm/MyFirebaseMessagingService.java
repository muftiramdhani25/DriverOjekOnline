package net.growdev.driverojekonline.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonElement;

import net.growdev.driverojekonline.R;
import net.growdev.driverojekonline.helper.MyContants;
import net.growdev.driverojekonline.view.detail.DetailOrderActivity;
import net.growdev.driverojekonline.view.history.HistoryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private String data;
    private String pesandarifirebase;
    private Map<String, String> datafirebase;
    private String datafirebasee;
    private JsonElement datadata;
    private JSONObject array;
    private String username;
    private int nilai;
    private String catatan;
    private String idbook;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        nilai = remoteMessage.getData().size();
        Log.d("testnilai","info : " + nilai );
        if (remoteMessage.getData().size()>0){
            datafirebasee = remoteMessage.getData().get("datax");
            try {
                JSONObject object = new JSONObject(datafirebasee);

                array = object.getJSONObject("datax").getJSONObject("data");
                username = array.getString("user_nama");
                String hp = array.getString("user_hp");
                idbook = array.getString("id_booking");
                catatan = array.getString("booking_catatan");
                Log.d("testnih", username);
            } catch (JSONException e) {

            }
        }

//  notif(pesandarifirebase, bodydarifirebase,a,b);

        showNotification(getApplicationContext(), username,catatan,idbook);
//            Log.d("dataku", pesandarifirebase);
    }

    public void showNotification(Context context, String username, String data, String idboking) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        // Uri soundUri = Uri.parse("android.resource://" +
        // getApplicationContext().getPackageName() + "/" + R.raw.consequence);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(username)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentText(data);
        Intent intent = new Intent(context, DetailOrderActivity.class);
        intent.putExtra(MyContants.IDBOOKING,idboking );
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }

    private void notif(String pesandarifirebase) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent ii = new Intent(getApplicationContext(), HistoryActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(pesandarifirebase);
        bigText.setBigContentTitle("Title");
        bigText.setSummaryText("Text in detail");

        //Uri soundUri = Uri.parse("android.resource://" +
        //  getApplicationContext().getPackageName() + "/" + R.raw.consequence);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Orderan Masuk");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        mBuilder.setVibrate(new long[]{500, 500, 500, 500});
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

//            if(soundUri != null){
//                // Changing Default mode of notification
//                mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
//                // Creating an Audio Attribute
//                AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                        .setUsage(AudioAttributes.USAGE_ALARM)
//                        .build();
//
//                // Creating Channel
//                NotificationChannel notificationChannel = new NotificationChannel("CH_ID","Testing_Audio",NotificationManager.IMPORTANCE_HIGH);
//                notificationChannel.setSound(soundUri,audioAttributes);
//                mNotificationManager.createNotificationChannel(notificationChannel);
//            }
        }
        mNotificationManager.notify(0, mBuilder.build());
    }
}



