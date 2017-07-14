package com.zwonb.theapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.zwonb.theapp.base.BaseActivity;
import com.zwonb.theapp.view.WelcomeVideoView;

public class WelcomeActivity extends BaseActivity {

    private WelcomeVideoView videoView;


    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome);
        videoView = getViewById(R.id.welcome_video_view);
        videoView.setVideoURI(Uri.parse("android.resource://"
                + this.getPackageName() + "/" + R.raw.welcome));
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });
        getViewById(R.id.welcome_into).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.stopPlayback();
                    videoView = null;
                }
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.welcome_enter,R.anim.welcome_exit);
                finish();
            }
        });
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoView != null && !videoView.isPlaying()) {
            videoView.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null && videoView.isPlaying()) {
            videoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.suspend();
            videoView = null;
        }
    }
}
