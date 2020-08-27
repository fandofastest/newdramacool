package com.kiassasian.appasian.utils.ads;

import android.content.Context;


import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.kiassasian.appasian.database.DatabaseHelper;
import com.kiassasian.appasian.network.model.AdsConfig;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.StartAppSDK;


public class BannerAds {

    public static void ShowAdmobBannerAds(Context context, RelativeLayout mAdViewLayout) {
        AdsConfig adsConfig = new DatabaseHelper(context).getConfigurationData().getAdsConfig();

        AdView mAdView = new AdView(context);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(adsConfig.getAdmobBannerAdsId());
        AdRequest.Builder builder = new AdRequest.Builder();

 // else do nothing , it will load PERSONALIZED ads
        mAdView.loadAd(builder.build());
        mAdViewLayout.addView(mAdView);

    }

    public static void showStartAppBanner(Context context, final RelativeLayout mainLayout) {
        //startapp
        StartAppSDK.init(context, new DatabaseHelper(context).getConfigurationData().getAdsConfig().getStartappAppId(), true);

        // Create new StartApp banner
        Banner startAppBanner = new Banner(context);
        RelativeLayout.LayoutParams bannerParameters =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
        bannerParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        // Add the banner to the main layout
        mainLayout.addView(startAppBanner, bannerParameters);
    }

    public static void showFANBanner(Context context, RelativeLayout mAdViewLayout) {
        AdsConfig adsConfig = new DatabaseHelper(context).getConfigurationData().getAdsConfig();

        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, adsConfig.getFanBannerAdsPlacementId(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        mAdViewLayout.addView(adView);
        // Request an ad
        adView.loadAd();
    }

}
