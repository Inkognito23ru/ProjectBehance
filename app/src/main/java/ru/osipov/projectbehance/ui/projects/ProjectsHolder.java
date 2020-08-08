package ru.osipov.projectbehance.ui.projects;

import androidx.recyclerview.widget.RecyclerView;
import ru.osipov.projectbehance.data.model.project.Project;
import ru.osipov.projectbehance.databinding.ProjectBinding;

public class ProjectsHolder extends RecyclerView.ViewHolder {

    private static final int FIRST_OWNER_INDEX = 0;

    private ProjectBinding mProjectBinding;

    public ProjectsHolder(ProjectBinding binding) {
        super(binding.getRoot());

        mProjectBinding = binding;
    }

    public void bind(Project item, ProjectsAdapter.OnItemClickListener onItemClickListener) {

        mProjectBinding.setProject(new ProjectListItemViewModel(item));
        mProjectBinding.setOnItemClickListener(onItemClickListener);


        mProjectBinding.executePendingBindings();

    }
}
