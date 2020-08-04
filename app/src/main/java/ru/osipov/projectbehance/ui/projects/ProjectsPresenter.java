package ru.osipov.projectbehance.ui.projects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import ru.osipov.projectbehance.BuildConfig;
import ru.osipov.projectbehance.common.BasePresenter;
import ru.osipov.projectbehance.data.Storage;
import ru.osipov.projectbehance.utils.ApiUtils;

@InjectViewState
public class ProjectsPresenter extends BasePresenter<ProjectsView> {

    private Storage mStorage;

    public ProjectsPresenter(Storage storage) {
        mStorage = storage;
    }

    public void getProjects(){

        mCompositeDisposable.add(ApiUtils.getApiService().getProjects(BuildConfig.API_QUERY)
                .doOnSuccess(response -> mStorage.insertProjects(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showLoading())
                .doFinally(() -> getViewState().hideLoading())
                .subscribe(
                        response -> getViewState().showProjects(response.getProjects()),
                        throwable -> getViewState().showError()));
    }

    public void openProfileFragment(String userName){
        getViewState().openProfileFragment(userName);
    }

}
