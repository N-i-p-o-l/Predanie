package ru.predanie.predanie.view.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import com.annimon.stream.Exceptional;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import ru.predanie.predanie.R;
import ru.predanie.predanie.view.adapter.ExpandableRecycleAdapter;

/**
 * Created by NArtur on 13.04.2016.
 */
public class MusicPlayerActivity extends AppCompatActivity
    implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
    MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener,
    MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnInfoListener {

  private final static String TAG = "MusicPlayer";

  public final static String COMP_NAME = "compName";
  public final static String DATA_KEY = "dataKey";
  public final static String ID_KEY = "idKey";
  public final static String IMAGE_URL = "imageUrl";

  private ImageView musicImage;
  private TextView trackName;
  private ImageView playPause;
  private TextView toolbarTitle;
  private ImageView prev, next;
  private SeekBar seekBar;
  private Toolbar toolbar;
  private ProgressBar progressBar;
  private LinearLayout linearLayout;

  private MediaPlayer mediaPlayer;
  private boolean played;
  private int itemChoose;
  private String imageUrl;
  private String compName;
  private int currentTrack;

  private ArrayList<ExpandableRecycleAdapter.PartTrackItem> trackItems;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.musicplayer);

    musicImage = (ImageView) findViewById(R.id.music_image);
    trackName = (TextView) findViewById(R.id.trackName);
    playPause = (ImageView) findViewById(R.id.play_pause);
    prev = (ImageView) findViewById(R.id.prev);
    next = (ImageView) findViewById(R.id.next);
    seekBar = (SeekBar) findViewById(R.id.seekBar);
    toolbar = (Toolbar) findViewById(R.id.musicToolbar);
    toolbarTitle = (TextView) findViewById(R.id.musicToolbarTitle);
    progressBar = (ProgressBar) findViewById(R.id.musicLoadSpinner);
    linearLayout = (LinearLayout) findViewById(R.id.musicMainLayout);

    if (toolbar != null) {
      setSupportActionBar(toolbar);
    }

    getSupportActionBar().setDisplayShowTitleEnabled(false);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    trackItems = getIntent().getExtras().getParcelableArrayList(DATA_KEY);
    imageUrl = getIntent().getExtras().getString(IMAGE_URL);
    compName = getIntent().getExtras().getString(COMP_NAME);
    itemChoose = getIntent().getExtras().getInt(ID_KEY);

    toolbarTitle.setText(compName);
    currentTrack = itemChoose;

    Picasso.with(this)
        .load(imageUrl)
        .into(musicImage);

    mediaPlayer = new MediaPlayer();
    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    mediaPlayer.setOnPreparedListener(this);
    mediaPlayer.setOnCompletionListener(this);
    mediaPlayer.setOnBufferingUpdateListener(this);
    mediaPlayer.setOnErrorListener(this);
    mediaPlayer.setOnSeekCompleteListener(this);
    mediaPlayer.setOnInfoListener(this);

    trackName.setText(trackItems.get(itemChoose).trackName != null ?
        trackItems.get(itemChoose).trackName : trackItems.get(itemChoose).partName);

    Exceptional.of(() -> {
      mediaPlayer.setDataSource(trackItems.get(itemChoose).url);
      mediaPlayer.prepareAsync();
      return true;
    }).ifException(e -> Log.e(TAG, e.toString()));

    playPause.setOnClickListener((v) -> {
      if (played) {
        mediaPlayer.pause();
        playPause.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.ic_action_playback_play));
        played = false;
      } else {
        mediaPlayer.start();
        playPause.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_action_playback_pause));
        played = true;
      }
    });

    prev.setOnClickListener((v) -> {
       prevSong();
    });

    next.setOnClickListener((v) -> {
      nextSong();
    });

    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

      }

      @Override public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override public void onStopTrackingTouch(SeekBar seekBar) {
        progressBar.setVisibility(View.VISIBLE);
        mediaPlayer.seekTo(seekBar.getProgress() * 100);
        Log.d(TAG, "Seekbar seek");
      }
    });
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override public void onPrepared(MediaPlayer mp) {
    if (!mediaPlayer.isPlaying()) {
      mediaPlayer.start();
      seekBar.setProgress(0);
      seekBar.setMax(mp.getDuration() / 100);
      played = true;
      playPause.setImageDrawable(
          ContextCompat.getDrawable(this, R.drawable.ic_action_playback_pause));
    }

    ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
    service.scheduleWithFixedDelay(
        (Runnable) () -> seekBar.setProgress(mediaPlayer.getCurrentPosition() / 100), 1, 1, TimeUnit.SECONDS);

    progressBar.setVisibility(View.GONE);
    Log.d(TAG, "MediaPlayer prepared");
  }

  @Override public void onCompletion(MediaPlayer mp) {
    nextSong();
  }

  private void nextSong() {
    progressBar.setVisibility(View.VISIBLE);
    mediaPlayer.reset();
    currentTrack++;
    if (currentTrack > (trackItems.size() - 1)) {
      currentTrack = 0;
    }
    trackName.setText(trackItems.get(currentTrack).trackName != null ?
        trackItems.get(currentTrack).trackName : trackItems.get(currentTrack).partName);
    Exceptional.of(() -> {
      mediaPlayer.setDataSource(trackItems.get(currentTrack).url);
      mediaPlayer.prepareAsync();
      return true;
    }).ifException(e -> Log.e(TAG, e.toString()));
  }

  private void prevSong() {
    progressBar.setVisibility(View.VISIBLE);
    mediaPlayer.reset();
    currentTrack--;
    if (currentTrack < 0) {
      currentTrack = trackItems.size() - 1;
    }
    trackName.setText(trackItems.get(currentTrack).trackName != null ?
        trackItems.get(currentTrack).trackName : trackItems.get(currentTrack).partName);
    Exceptional.of(() -> {
      mediaPlayer.setDataSource(trackItems.get(currentTrack).url);
      mediaPlayer.prepareAsync();
      return true;
    }).ifException(e -> Log.e(TAG, e.toString()));
  }

  @Override public void onBufferingUpdate(MediaPlayer mp, int percent) {
  }

  @Override public boolean onError(MediaPlayer mp, int what, int extra) {
    progressBar.setVisibility(View.GONE);
    Snackbar.make(linearLayout, getString(R.string.error_network), Snackbar.LENGTH_LONG)
        .show();
    Log.d(TAG, "MediaPlayer Error");
    return false;
  }

  @Override public void onSeekComplete(MediaPlayer mp) {
    progressBar.setVisibility(View.GONE);
  }

  @Override public boolean onInfo(MediaPlayer mp, int what, int extra) {
    switch (what) {
      case MediaPlayer.MEDIA_INFO_BUFFERING_START:
        progressBar.setVisibility(View.VISIBLE);
      case MediaPlayer.MEDIA_INFO_BUFFERING_END:
        progressBar.setVisibility(View.GONE);
    }

    return false;
  }

  @Override
  public void onDestroy(){
    super.onDestroy();
    mediaPlayer.release();
  }
}
