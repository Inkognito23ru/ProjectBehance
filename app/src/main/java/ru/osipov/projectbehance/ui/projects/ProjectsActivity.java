package ru.osipov.projectbehance.ui.projects;

import androidx.fragment.app.Fragment;
import ru.osipov.projectbehance.common.SingleFragmentActivity;

public class ProjectsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return ProjectsFragment.newInstance();
    }
}
