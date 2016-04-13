package ru.predanie.predanie.view.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.predanie.predanie.R;
import ru.predanie.predanie.api.CustomGson;
import ru.predanie.predanie.api.IApi;
import ru.predanie.predanie.api.ServiceGenerator;
import ru.predanie.predanie.model.Composition;
import ru.predanie.predanie.view.fragment.CompDescFragment;
import ru.predanie.predanie.view.fragment.CompPartsFragment;

/**
 * Created by NArtur on 11.04.2016.
 */
public class CompositionDetailActivity extends AppCompatActivity {

  public static final String COMPOSITION_ID = "composition_id";
  private static final String TAG = "CompDetailActivity";

  private long comp_id;

  private ImageView compImage;
  private TextView compName;
  private TextView compAuthor;
  private TextView toolbarTitle;
  private Button content, description;
  private ProgressBar compAuthorProgress;
  private ProgressBar compPartsProgress;

  private Toolbar toolbar;

  private FragmentManager fragmentManager;

  private IApi compositionApi;

  private Composition composition;
  public Composition getComposition() {
    return composition;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.composition_detail_view);

    toolbar = (Toolbar) findViewById(R.id.compDetailToolbar);
    compImage = (ImageView) findViewById(R.id.composition_detail_image);
    compName = (TextView)findViewById(R.id.composition_detail_name);
    compAuthor = (TextView) findViewById(R.id.composition_detail_author);
    toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
    content = (Button) findViewById(R.id.composition_books);
    description = (Button) findViewById(R.id.composition_desc);
    compAuthorProgress = (ProgressBar) findViewById(R.id.comp_detail_author_progress);
    compPartsProgress = (ProgressBar) findViewById(R.id.comp_detail_parts_progress);

    if (toolbar != null) {
      setSupportActionBar(toolbar);
    }

    getSupportActionBar().setDisplayShowTitleEnabled(false);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    comp_id = intent.getLongExtra(COMPOSITION_ID, 0);

    getCompDetail();

    content.setOnClickListener((v) -> {
      fragmentManager = getSupportFragmentManager();
      fragmentManager.beginTransaction()
          .replace(R.id.composition_content, CompPartsFragment.newInstance())
          .commit();
    });

    description.setOnClickListener((v) -> {
      fragmentManager = getSupportFragmentManager();
      fragmentManager.beginTransaction()
          .replace(R.id.composition_content, CompDescFragment.newInstance())
          .commit();
    });
  }

  private void getCompDetail() {
    compositionApi = ServiceGenerator.createService(IApi.class, CustomGson.getGsonCompDetail());
    Call<Composition> call = compositionApi.getCompositionDetail(comp_id);
    call.enqueue(new Callback<Composition>() {
      @Override public void onResponse(Call<Composition> call, Response<Composition> response) {

        composition = new Composition();
        composition.setId(response.body().getId());
        composition.setName(response.body().getName());
        composition.setAuthor(response.body().getAuthor());
        composition.setImageMediumUrl(response.body().getImageMediumUrl());
        composition.setImageBigUrl(response.body().getImageBigUrl());
        composition.setDesc(response.body().getDesc());
        composition.setParts(response.body().getParts());
        composition.setTracks(response.body().getTracks());

        compName.setText(composition.getName());
        compAuthor.setText(composition.getAuthor());

        Glide.with(CompositionDetailActivity.this)
            .load(response.body().getImageMediumUrl())
            .into(compImage);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
            .replace(R.id.composition_content, CompPartsFragment.newInstance())
            .commit();

        compAuthorProgress.setVisibility(View.GONE);
        compPartsProgress.setVisibility(View.GONE);
        enableButtons();
      }

      @Override public void onFailure(Call<Composition> call, Throwable t) {
        compAuthorProgress.setVisibility(View.GONE);
        compPartsProgress.setVisibility(View.GONE);
        Log.d(TAG, "Error on load comp details");
      }
    });

  }

  private void enableButtons() {
    content.setEnabled(true);
    content.setAlpha(1f);
    content.setClickable(true);
    description.setEnabled(true);
    description.setAlpha(1f);
    description.setClickable(true);
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
