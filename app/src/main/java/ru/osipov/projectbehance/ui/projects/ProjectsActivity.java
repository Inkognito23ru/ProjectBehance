package ru.osipov.projectbehance.ui.projects;

import androidx.fragment.app.Fragment;
import ru.osipov.projectbehance.AppDelegate;
import ru.osipov.projectbehance.common.SingleFragmentActivity;
import ru.osipov.projectbehance.data.Storage;

public class ProjectsActivity extends SingleFragmentActivity implements Storage.StorageOwner {

    @Override
    protected Fragment getFragment() {
        return ProjectsFragment.newInstance();
    }

    @Override
    public Storage obtainStorage() {
        return ((AppDelegate) getApplicationContext()).getStorage();
    }
}
