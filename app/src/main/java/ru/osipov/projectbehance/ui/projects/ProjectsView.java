package ru.osipov.projectbehance.ui.projects;

import java.util.List;

import ru.osipov.projectbehance.common.BaseView;
import ru.osipov.projectbehance.data.model.project.Project;

public interface ProjectsView extends BaseView {

    void showProjects(List<Project> projects);

    void openProfileFragment(String userName);
}
