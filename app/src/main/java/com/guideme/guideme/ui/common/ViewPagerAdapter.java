package com.guideme.guideme.ui.common;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentsList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    public List<Fragment> getFragments() {
        return fragmentsList;
    }

    public void addFragment(Fragment fragment) {
        fragmentsList.add(fragment);
    }

    public void addFragment(int index, Fragment fragment) {
        fragmentsList.add(index, fragment);
    }

    public void removeFragment(int index) {
        fragmentsList.remove(index);
    }
}
