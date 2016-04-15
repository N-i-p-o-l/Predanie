package ru.predanie.predanie.view.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import ru.predanie.predanie.R;
import ru.predanie.predanie.view.adapter.MainViewPageAdapter;
import ru.predanie.predanie.view.fragment.NewFragment;
import ru.predanie.predanie.view.fragment.PopularFragment;
import ru.predanie.predanie.view.fragment.RecommendFragment;

/**
 * Created by NArtur on 05.04.2016.
 */
public class MainActivity extends AppCompatActivity {

  private DrawerLayout drawerLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    final ActionBar ab = getSupportActionBar();
    ab.setHomeAsUpIndicator(R.drawable.ic_menu);
    ab.setDisplayHomeAsUpEnabled(true);

    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    if (navigationView != null) {
      setupDrawerContent(navigationView);
    }

    ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
    if (viewPager != null) {
      setupViewPager(viewPager);
    }

    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(viewPager);
  }

  private void setupDrawerContent(NavigationView navigationView) {
    navigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {
          @Override public boolean onNavigationItemSelected(MenuItem menuItem) {
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
            return true;
          }
        });
  }

  private void setupViewPager(ViewPager viewPager) {
    MainViewPageAdapter adapter = new MainViewPageAdapter(getSupportFragmentManager());
    adapter.addFragment(new PopularFragment(), "Популярное");
    adapter.addFragment(new NewFragment(), "Новое");
    adapter.addFragment(new RecommendFragment(), "Рекомендуем");
    viewPager.setAdapter(adapter);
    viewPager.setOffscreenPageLimit(3);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        drawerLayout.openDrawer(GravityCompat.START);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }

}
