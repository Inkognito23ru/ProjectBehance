package ru.osipov.projectbehance.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ru.osipov.projectbehance.data.Storage;
import ru.osipov.projectbehance.ui.profile.ProfileViewModel;

public class CustomProfileFactory extends ViewModelProvider.NewInstanceFactory {

    private Storage mStorage;

    public CustomProfileFactory(Storage storage) {
        mStorage = storage;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProfileViewModel(mStorage);
    }
}
