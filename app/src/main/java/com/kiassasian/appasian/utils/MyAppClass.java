package com.kiassasian.appasian.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.facebook.ads.AudienceNetworkAds;
import com.onesignal.OneSignal;
import com.kiassasian.appasian.Config;
import com.kiassasian.appasian.NotificationClickHandler;
import com.kiassasian.appasian.database.DatabaseHelper;
import com.kiassasian.appasian.network.RetrofitClient;
import com.kiassasian.appasian.network.model.ActiveStatus;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyAppClass extends Application {

    public static final String NOTIFICATION_CHANNEL_ID = "download_channel_id";
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        if (AudienceNetworkAds.isInAdsProcess(this)) {
            return;
        }
        Picasso.setSingletonInstance(getCustomPicasso());

        mContext = this;
        createNotificationChannel();


        // OneSignal Initialization
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new NotificationClickHandler(mContext))
                //.inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        SharedPreferences preferences = getSharedPreferences("push", MODE_PRIVATE);
        if (preferences.getBoolean("status", true)) {
            OneSignal.setSubscription(true);
        } else {
            OneSignal.setSubscription(false);
        }

        //

        if (!getFirstTimeOpenStatus()) {
            if (Config.DEFAULT_DARK_THEME_ENABLE) {
                changeSystemDarkMode(true);
            } else {
                changeSystemDarkMode(false);
            }
            saveFirstTimeOpenStatus(true);
        }

        // fetched and save the user active status if user is logged in
        String userId = PreferenceUtils.getUserId(this);
        if (userId != null && !userId.equals("")) {

        }

        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(this);
     }

    private Picasso getCustomPicasso() {
        Picasso.Builder builder = new Picasso.Builder(this);
        //set 12% of available app memory for image cachecc
        builder.memoryCache(new LruCache(getBytesForMemCache(12)));
        //set request transformer
        Picasso.RequestTransformer requestTransformer =  new Picasso.RequestTransformer() {
            @Override
            public Request transformRequest(Request request) {
                Log.d("image request", request.toString());
                return request;
            }
        };
        builder.requestTransformer(requestTransformer);

        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri,
                                          Exception exception) {
                Log.d("image load error", uri.getPath());
            }
        });

        return builder.build();
    }

    private int getBytesForMemCache(int percent) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)
                getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);

        double availableMemory= mi.availMem;

        return (int)(percent*availableMemory/100);
    }


    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NotificationName",
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }


    public void changeSystemDarkMode(boolean dark) {
        SharedPreferences.Editor editor = getSharedPreferences("push", MODE_PRIVATE).edit();
        editor.putBoolean("dark", dark);
        editor.apply();
    }

    public void saveFirstTimeOpenStatus(boolean dark) {
        SharedPreferences.Editor editor = getSharedPreferences("push", MODE_PRIVATE).edit();
        editor.putBoolean("firstTimeOpen", true);
        editor.apply();

    }

    public boolean getFirstTimeOpenStatus() {
        SharedPreferences preferences = getSharedPreferences("push", MODE_PRIVATE);
        return preferences.getBoolean("firstTimeOpen", false);
    }

    public static Context getContext() {
        return mContext;
    }




}
