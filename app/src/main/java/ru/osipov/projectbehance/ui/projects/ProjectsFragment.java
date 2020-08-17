package ru.osipov.projectbehance.ui.projects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import ru.osipov.projectbehance.R;
import ru.osipov.projectbehance.data.Storage;
import ru.osipov.projectbehance.databinding.ProjectsBinding;
import ru.osipov.projectbehance.ui.profile.ProfileActivity;
import ru.osipov.projectbehance.ui.profile.ProfileFragment;
import ru.osipov.projectbehance.utils.CustomProjectFactory;

public class ProjectsFragment extends Fragment {

    private ProjectsViewModel mProjectsViewModel;
    private ProjectsAdapter.OnItemClickListener mOnItemClickListener = new ProjectsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String username) {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            Bundle args = new Bundle();
            args.putString(ProfileFragment.PROFILE_KEY, username);
            intent.putExtra(ProfileActivity.USERNAME_KEY, args);
            startActivity(intent);
        }
    };

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Storage.StorageOwner) {
            Storage storage = ((Storage.StorageOwner) context).obtainStorage();
            CustomProjectFactory customProjectFactory = new CustomProjectFactory(storage, mOnItemClickListener);

            mProjectsViewModel = ViewModelProviders
                    .of(this, customProjectFactory)
                    .get(ProjectsViewModel.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ProjectsBinding binding = ProjectsBinding.inflate(inflater, container, false);
        binding.setVm(mProjectsViewModel);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().setTitle(R.string.projects);
        }
    }


}
