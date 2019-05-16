package ceti.co.copyproj1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> items = new ArrayList<Fragment>();

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // i 위치의 프래그먼트
    @Override
    public Fragment getItem(int i) {
        return items.get(i);
    }

    // 아이템의 갯수
    @Override
    public int getCount() {
        return items.size();
    }

    // 아이템 갱신
    public void updateFragments(List<Fragment> items){
        this.items.addAll(items);
    }
}
