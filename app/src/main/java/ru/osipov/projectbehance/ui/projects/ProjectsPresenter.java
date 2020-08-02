package ru.osipov.projectbehance.ui.projects;

import android.view.View;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.osipov.projectbehance.BuildConfig;
import ru.osipov.projectbehance.common.BasePresenter;
import ru.osipov.projectbehance.data.Storage;
import ru.osipov.projectbehance.utils.ApiUtils;

public class ProjectsPresenter extends BasePresenter {

    private ProjectsView mProjectsView;
    private Storage mStorage;

    public ProjectsPresenter(ProjectsView projectsView, Storage storage) {
        mProjectsView = projectsView;
        mStorage = storage;
    }

    public void getProjects(){

        mCompositeDisposable.add(ApiUtils.getApiService().getProjects(BuildConfig.API_QUERY)
                .doOnSuccess(response -> mStorage.insertProjects(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mProjectsView.showLoading())
                .doFinally(() -> mProjectsView.hideLoading())
                .subscribe(
                        response -> mProjectsView.showProjects(response.getProjects()),
                        throwable -> mProjectsView.showError()));
    }

    public void openProfileFragment(String userName){
        mProjectsView.openProfileFragment(userName);
    }

}
