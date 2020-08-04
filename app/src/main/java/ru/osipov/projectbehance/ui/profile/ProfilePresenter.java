package ru.osipov.projectbehance.ui.profile;

import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import ru.osipov.projectbehance.common.BasePresenter;
import ru.osipov.projectbehance.data.Storage;
import ru.osipov.projectbehance.utils.ApiUtils;

@InjectViewState
public class ProfilePresenter extends BasePresenter<ProfileView> {

    private Storage mStorage;

    public ProfilePresenter(Storage storage) {
        mStorage = storage;
    }

    public void getProfile(String userName, ImageView mProfileImage){
        mCompositeDisposable.add(ApiUtils.getApiService().getUserInfo(userName)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(userName) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showLoading())
                .doFinally(() -> getViewState().hideLoading())
                .subscribe(
                        response -> {
                            Picasso.get()
                                    .load(response.getUser().getImage().getPhotoUrl())
                                    .fit()
                                    .into(mProfileImage);
                            getViewState().showProfile(response.getUser());
                        },
                        throwable -> {
                            getViewState().showError();
                        }));
    }
}
