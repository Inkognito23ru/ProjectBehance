package ru.osipov.projectbehance.di;

import javax.inject.Singleton;
import dagger.Component;
import ru.osipov.projectbehance.common.PresenterFragment;
import ru.osipov.projectbehance.ui.profile.ProfileFragment;
import ru.osipov.projectbehance.ui.projects.ProjectsFragment;
import ru.osipov.projectbehance.ui.projects.ProjectsPresenter;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    void injectProjectsFragment(ProjectsFragment injector);
    void injectProfileFragment(ProfileFragment injector);
}
