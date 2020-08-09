package ru.osipov.projectbehance.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.osipov.projectbehance.data.Storage;
import ru.osipov.projectbehance.databinding.ProfileBinding;
public class ProfileFragment extends Fragment {

    private ProfileViewModel mProfileViewModel;
    public static final String PROFILE_KEY = "PROFILE_KEY";

    private String mUsername;

    public static ProfileFragment newInstance(Bundle args) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Storage storage = context instanceof Storage.StorageOwner ? ((Storage.StorageOwner) context).obtainStorage() : null;
        mProfileViewModel = new ProfileViewModel(storage);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ProfileBinding binding = ProfileBinding.inflate(inflater, container, false);
        binding.setVm(mProfileViewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            mUsername = getArguments().getString(PROFILE_KEY);
            mProfileViewModel.setUsername(mUsername);
        }

        if (getActivity() != null) {
            getActivity().setTitle(mUsername);
        }

        mProfileViewModel.loadProfile();

    }

    @Override
    public void onDetach() {
        mProfileViewModel.dispathDetach();
        super.onDetach();
    }
}
