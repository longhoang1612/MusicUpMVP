package music_38.framgia.com.musicup.utils;

import android.support.annotation.AnimRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.facebook.common.internal.Preconditions.checkNotNull;

public class FragmentTransactionUtils {

    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, int res, String tag) {
        addFragment(fragmentManager, fragment, res, tag, false, -1, -1);
    }

    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, int res, String tag,
                                   boolean isAddToBackStack, @AnimRes int animIn, @AnimRes int animOut) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);

        Fragment currentFragment = fragmentManager.findFragmentByTag(tag);
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (currentFragment != null) {
            showFragment(fragmentManager, transaction, fragment);
            return;
        }
        transaction.add(res, fragment, tag);
        if (isAddToBackStack) {
            transaction.addToBackStack(null);
        }
        if (animIn != -1 && animOut != -1) {
            transaction.setCustomAnimations(animIn, animOut);
        }
        transaction.commit();
    }

    private static void showFragment(FragmentManager fragmentManager, FragmentTransaction transaction, Fragment fragment) {
        for (int i = 0; i < fragmentManager.getFragments().size(); i++) {
            transaction.hide(fragmentManager.getFragments().get(i));
        }
        transaction.show(fragment).commit();
    }
}
