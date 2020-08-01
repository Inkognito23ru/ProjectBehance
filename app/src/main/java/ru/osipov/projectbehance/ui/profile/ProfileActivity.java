package ru.osipov.projectbehance.ui.profile;

import androidx.fragment.app.Fragment;
import ru.osipov.projectbehance.common.SingleFragmentActivity;

public class ProfileActivity extends SingleFragmentActivity {

    public static final String USERNAME_KEY = "USERNAME_KEY";

    @Override
    protected Fragment getFragment() {
        if (getIntent() != null) {
            return ProfileFragment.newInstance(getIntent().getBundleExtra(USERNAME_KEY));
        }
        throw new IllegalStateException("getIntent cannot be null");
    }
}
