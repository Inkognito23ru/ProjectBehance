package ru.osipov.projectbehance.ui.profile;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.osipov.projectbehance.data.Storage;
import ru.osipov.projectbehance.data.model.user.User;
import ru.osipov.projectbehance.utils.ApiUtils;
import ru.osipov.projectbehance.utils.DateUtils;

public class ProfileViewModel extends ViewModel {

    private Disposable mDisposable;
    private Storage mStorage;

    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();

    private String mUsername;

    public void setUsername(String username) {
        mUsername = username;
    }

    private MutableLiveData<String> mProfileImage = new MutableLiveData<>();
    private MutableLiveData<String> mProfileName = new MutableLiveData<>();
    private MutableLiveData<String> mProfileCreatedOn = new MutableLiveData<>();
    private MutableLiveData<String> mProfileLocation = new MutableLiveData<>();

    private MutableLiveData<Boolean> mIsErrorVisible = new MutableLiveData<>();
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
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doFinally(() -> mIsLoading.postValue(false))
                .subscribe(
                        response -> {
                            mIsErrorVisible.postValue(false);
                            bind(response.getUser());
                        },
                        throwable -> {
                            mIsErrorVisible.postValue(true);
                        });
    }

    private void bind(User user) {
        mProfileImage.postValue(user.getImage().getPhotoUrl());
        mProfileName.postValue(user.getDisplayName());
        mProfileCreatedOn.postValue(DateUtils.format(user.getCreatedOn()));
        mProfileLocation.postValue(user.getLocation());
    }

    @Override
    public void onCleared(){
        mStorage = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }


    public MutableLiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }

    public MutableLiveData<String> getProfileImage() {
        return mProfileImage;
    }

    public MutableLiveData<String> getProfileName() {
        return mProfileName;
    }

    public MutableLiveData<String> getProfileCreatedOn() {
        return mProfileCreatedOn;
    }

    public MutableLiveData<String> getProfileLocation() {
        return mProfileLocation;
    }

    public MutableLiveData<Boolean> getIsErrorVisible() {
        return mIsErrorVisible;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }
}
