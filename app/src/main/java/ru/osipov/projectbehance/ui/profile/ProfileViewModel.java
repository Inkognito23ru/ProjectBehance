package ru.osipov.projectbehance.ui.profile;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.osipov.projectbehance.data.Storage;
import ru.osipov.projectbehance.data.model.user.User;
import ru.osipov.projectbehance.utils.ApiUtils;
import ru.osipov.projectbehance.utils.DateUtils;

public class ProfileViewModel {

    private Disposable mDisposable;
    private Storage mStorage;

    private ObservableBoolean mIsLoading = new ObservableBoolean(false);

    private String mUsername;

    public void setUsername(String username) {
        mUsername = username;
    }

    private ObservableField<String> mProfileImage = new ObservableField<>();
    private ObservableField<String> mProfileName = new ObservableField<>();
    private ObservableField<String> mProfileCreatedOn = new ObservableField<>();
    private ObservableField<String> mProfileLocation = new ObservableField<>();

    public ObservableBoolean mIsErrorVisible = new ObservableBoolean(false);
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadProfile();
        }
    };

    public ProfileViewModel(Storage storage) {
        mStorage = storage;
    }

    public void loadProfile() {
        mDisposable = ApiUtils.getApiService().getUserInfo(mUsername)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(mUsername) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mIsLoading.set(true))
                .doFinally(() -> mIsLoading.set(false))
                .subscribe(
                        response -> {
                            mIsErrorVisible.set(false);
                            bind(response.getUser());
                        },
                        throwable -> {
                            mIsErrorVisible.set(true);
                        });
    }

    private void bind(User user) {
        mProfileImage.set(user.getImage().getPhotoUrl());
        mProfileName.set(user.getDisplayName());
        mProfileCreatedOn.set(DateUtils.format(user.getCreatedOn()));
        mProfileLocation.set(user.getLocation());
    }

    public void dispathDetach(){
        mStorage = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public ObservableField<String> getProfileImage() {
        return mProfileImage;
    }

    public ObservableField<String> getProfileName() {
        return mProfileName;
    }

    public ObservableField<String> getProfileCreatedOn() {
        return mProfileCreatedOn;
    }

    public ObservableField<String> getProfileLocation() {
        return mProfileLocation;
    }

    public ObservableBoolean getIsErrorVisible() {
        return mIsErrorVisible;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }
}
