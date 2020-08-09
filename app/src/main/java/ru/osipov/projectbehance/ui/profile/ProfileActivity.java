package ru.osipov.projectbehance.ui.profile;

import androidx.fragment.app.Fragment;

import ru.osipov.projectbehance.AppDelegate;
import ru.osipov.projectbehance.common.SingleFragmentActivity;
import ru.osipov.projectbehance.data.Storage;

public class ProfileActivity extends SingleFragmentActivity implements Storage.StorageOwner {

    public static final String USERNAME_KEY = "USERNAME_KEY";

    @Override
    protected Fragment getFragment() {
        if (getIntent() != null) {
            return ProfileFragment.newInstance(getIntent().getBundleExtra(USERNAME_KEY));
        }
        throw new IllegalStateException("getIntent cannot be null");
    }

        @Override
    public Storage obtainStorage() {
        return ((AppDelegate) getApplicationContext()).getStorage();
    }
}
