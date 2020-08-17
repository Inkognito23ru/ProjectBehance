package ru.osipov.projectbehance.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.osipov.projectbehance.data.Storage;
import ru.osipov.projectbehance.ui.projects.ProjectsAdapter;
import ru.osipov.projectbehance.ui.projects.ProjectsViewModel;

public class CustomProjectFactory extends ViewModelProvider.NewInstanceFactory {

    private Storage mStorage;
    private ProjectsAdapter.OnItemClickListener mOnItemClickListener;

    public CustomProjectFactory(Storage storage, ProjectsAdapter.OnItemClickListener onItemClickListener){
        mStorage = storage;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProjectsViewModel(mStorage, mOnItemClickListener);
    }
}
