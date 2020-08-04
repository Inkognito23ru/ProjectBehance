package ru.osipov.projectbehance.ui.projects;

import java.util.List;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.osipov.projectbehance.common.BaseView;
import ru.osipov.projectbehance.data.model.project.Project;

@StateStrategyType(SkipStrategy.class)
public interface ProjectsView extends BaseView {

    void showProjects(List<Project> projects);

    void openProfileFragment(String userName);
}
