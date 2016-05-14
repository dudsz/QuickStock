package erlach.quickstock;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Mackan on 2016-05-14.
 */
public class Tabs extends FragmentStatePagerAdapter {

    public Tabs(FragmentManager fm) { super (fm); }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                TabOne tab1 = new TabOne();
                return tab1;
            case 1:
                TabTwo tab2 = new TabTwo();
                return tab2;
            /*
            case 2:
                Three tab3 = new Three();
                return tab3;
            */
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}

