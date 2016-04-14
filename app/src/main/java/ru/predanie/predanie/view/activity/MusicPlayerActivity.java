package ru.predanie.predanie.view.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.annimon.stream.Exceptional;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import ru.predanie.predanie.R;
import ru.predanie.predanie.view.adapter.ExpandableRecycleAdapter;

/**
 * Created by NArtur on 13.04.2016.
 */
public class MusicPlayerActivity extends AppCompatActivity {

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

  private MediaPlayer mediaPlayer;
  private int itemChoose;
  private String imageUrl;
  private String compName;

  private ExpandableRecycleAdapter.PartTrackItem trackItem;

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

    if (toolbar != null) {
      setSupportActionBar(toolbar);
    }

    getSupportActionBar().setDisplayShowTitleEnabled(false);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    trackItem = getIntent().getExtras().getParcelable(DATA_KEY);
    imageUrl = getIntent().getExtras().getString(IMAGE_URL);
    compName = getIntent().getExtras().getString(COMP_NAME);

    toolbarTitle.setText(compName);
    trackName.setText(TextUtils.isEmpty(trackItem.trackName) ?
        trackItem.partName : trackItem.trackName);

    Picasso.with(this)
        .load(imageUrl)
        .into(musicImage);

    mediaPlayer = new MediaPlayer();
    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

    /*Exceptional.of(() -> {
      mediaPlayer.setDataSource(trackItem.url);
      mediaPlayer.prepare();
      return true;
    }).ifException(e -> Log.e(TAG, e.toString()));

    playPause.setOnClickListener((v) -> {
      mediaPlayer.start();
    });*/

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
}
