package ru.osipov.projectbehance.ui.profile;

import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.osipov.projectbehance.common.BasePresenter;
import ru.osipov.projectbehance.data.Storage;
import ru.osipov.projectbehance.data.api.BehanceApi;
import ru.osipov.projectbehance.utils.ApiUtils;

public class ProfilePresenter extends BasePresenter {

    private ProfileView mProfileView;
    @Inject
    Storage mStorage;
    @Inject
    BehanceApi mApi;

    @Inject
    public ProfilePresenter() {
    }

    public void setProfileView(ProfileView profileView) {
        mProfileView = profileView;
    }

    public void getProfile(String userName, ImageView mProfileImage){
        mCompositeDisposable.add(mApi.getUserInfo(userName)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(userName) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mProfileView.showLoading())
                .doFinally(() -> mProfileView.hideLoading())
                .subscribe(
                        response -> {
                            Picasso.get()
                                    .load(response.getUser().getImage().getPhotoUrl())
                                    .fit()
                                    .into(mProfileImage);
                            mProfileView.showProfile(response.getUser());
                        },
                        throwable -> {
                            mProfileView.showError();
                        }));
    }
}
