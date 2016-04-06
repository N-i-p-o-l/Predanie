package ru.predanie.predanie.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NArtur on 06.04.2016.
 */
public class MainViewPageAdapter extends FragmentStatePagerAdapter {

  private final List<Fragment> fragments = new ArrayList<>();
  private final List<String> fragmentTitles = new ArrayList<>();

  public MainViewPageAdapter(FragmentManager fm) {
    super(fm);
  }

  public void addFragment(Fragment fragment, String title) {
    fragments.add(fragment);
    fragmentTitles.add(title);
  }

  @Override public Fragment getItem(int position) {
    return fragments.get(position);
  }

  @Override public int getCount() {
    return fragments.size();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return fragmentTitles.get(position);
  }
}
