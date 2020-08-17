package ru.osipov.projectbehance.utils;

import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.squareup.picasso.Picasso;
import java.util.List;
import ru.osipov.projectbehance.data.model.project.Project;
import ru.osipov.projectbehance.data.model.project.RichProject;
import ru.osipov.projectbehance.ui.projects.ProjectsAdapter;

public class CustomBindingAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String urlImage){
        Picasso.get().load(urlImage).into(imageView);
    }

    @BindingAdapter({"bind:data", "bind:clickHandler"})
    public static void configureRecyclerView(RecyclerView recyclerView,
                                             PagedList<RichProject> projects,
                                             ProjectsAdapter.OnItemClickListener listener){
        ProjectsAdapter adapter = new ProjectsAdapter(listener);
        adapter.submitList(projects);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);

    }

    @BindingAdapter({"bind:refreshState", "bind:onRefresh"})
    public static void configureSwipeRefreshLayout(SwipeRefreshLayout layout,
                                                   boolean isLoading,
                                                   SwipeRefreshLayout.OnRefreshListener listener){
        layout.setOnRefreshListener(listener);
        layout.post(()->layout.setRefreshing(isLoading));
    }
}
