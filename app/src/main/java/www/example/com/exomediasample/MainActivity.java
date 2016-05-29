package www.example.com.exomediasample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.devbrackets.android.exomedia.listener.OnCompletionListener;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.listener.VideoControlsButtonListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.devbrackets.android.exomedia.ui.widget.VideoControls;

public class MainActivity extends AppCompatActivity {

    EMVideoView emVideoView;
    String mVideoUrl;
    String mVideoTitle;

    private boolean pausedInOnStop = false;
    private boolean mIsFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = getIntent();
        mVideoUrl = "http://www.sample-videos.com/video/mp4/480/big_buck_bunny_480p_10mb.mp4";
        mVideoTitle = "타이틀 입니다.";

        setupVideoView();
    }

    private void setupVideoView() {
        emVideoView = (EMVideoView)findViewById(R.id.video_view_video_play);
        emVideoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                emVideoView.start();
            }
        });

        emVideoView.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion() {
                mIsFinished = true;
            }
        });


        final VideoControls videoControls = emVideoView.getVideoControls();
        videoControls.show();
        videoControls.setTitle(mVideoTitle);
        videoControls.setButtonListener(new VideoControlsButtonListener() {
            @Override
            public boolean onPlayPauseClicked() {
                if (mIsFinished) {
                    emVideoView.setVideoURI(Uri.parse(mVideoUrl));
                    mIsFinished = false;
                }
                return false;
            }

            @Override
            public boolean onPreviousClicked() {
                return false;
            }

            @Override
            public boolean onNextClicked() {
                return false;
            }

            @Override
            public boolean onRewindClicked() {
                return false;
            }

            @Override
            public boolean onFastForwardClicked() {
                return false;
            }
        });

        emVideoView.setVideoURI(Uri.parse(mVideoUrl));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (emVideoView.isPlaying()) {
            pausedInOnStop = true;
            emVideoView.pause();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (pausedInOnStop) {
            emVideoView.start();
            pausedInOnStop = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        emVideoView.release();
    }
}
