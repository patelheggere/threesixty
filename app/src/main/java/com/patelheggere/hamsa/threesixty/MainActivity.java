package com.patelheggere.hamsa.threesixty;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class MainActivity extends AppCompatActivity {

    PlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    String videoURL = "http://dash.edgesuite.net/akamai/bbb_30fps/bbb_30fps.mpd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exoPlayerView =  findViewById(R.id.exo_player_view);
        try {
        /*    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            Uri videoURI = Uri.parse(videoURL);
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

           //Surface surface = new Surface(mSurface);
           // mExoPlayer.setVideoSurface(surface);

            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
*/

            Handler mainHandler = new Handler();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

// 2. Create the player
            SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            try {
                Uri dashVideoUri = Uri.parse(videoURL);
                DataSource.Factory mediaDataSourceFactory= new DefaultDataSourceFactory(this, Util.getUserAgent(this, "yourApplicationName"));
                DataSource.Factory dashDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "yourApplicationName"));
                // This is the MediaSource representing the media to be played.
                DashMediaSource dashMediaSource = new DashMediaSource.Factory(new DefaultDashChunkSource.Factory(mediaDataSourceFactory),
                        dashDataSourceFactory)
                        .createMediaSource(dashVideoUri,null,null);
                // Prepare the player with the source.
                player.prepare(dashMediaSource);
               exoPlayerView.setPlayer(player);
               player.setPlayWhenReady(true);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }catch (Exception e){
            Log.e("MainAcvtivity"," exoplayer error "+ e.toString());
        }
    }

}
