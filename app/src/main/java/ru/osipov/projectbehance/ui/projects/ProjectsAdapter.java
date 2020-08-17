package ru.osipov.projectbehance.ui.projects;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ru.osipov.projectbehance.R;
import ru.osipov.projectbehance.data.model.project.Project;
import ru.osipov.projectbehance.data.model.project.RichProject;
import ru.osipov.projectbehance.databinding.ProjectBinding;

public class ProjectsAdapter extends PagedListAdapter<RichProject, ProjectsHolder> {

    private final OnItemClickListener mOnItemClickListener;

    public ProjectsAdapter(OnItemClickListener onItemClickListener) {
        super(CALLBACK);
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ProjectsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.li_projects, parent, false);
        ProjectBinding binding = ProjectBinding.inflate(inflater, parent, false);
        return new ProjectsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectsHolder holder, int position) {
        RichProject project = getItem(position);
        if (project != null){
            holder.bind(project, mOnItemClickListener);
        }
    }


    private static final DiffUtil.ItemCallback<RichProject> CALLBACK = new DiffUtil.ItemCallback<RichProject>() {
        @Override
        public boolean areItemsTheSame(@NonNull RichProject oldItem, @NonNull RichProject newItem) {
            return oldItem.mProject.getId() == newItem.mProject.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull RichProject oldItem, @NonNull RichProject newItem) {
            return oldItem.equals(newItem);
        }
    };

    public interface OnItemClickListener {
        void onItemClick(String username);
    }
}
