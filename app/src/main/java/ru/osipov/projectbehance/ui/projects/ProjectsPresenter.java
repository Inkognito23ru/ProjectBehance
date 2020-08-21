package ru.osipov.projectbehance.ui.projects;

import android.util.Log;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.osipov.projectbehance.BuildConfig;
import ru.osipov.projectbehance.common.BasePresenter;
import ru.osipov.projectbehance.data.Storage;
import ru.osipov.projectbehance.data.api.BehanceApi;
import ru.osipov.projectbehance.utils.ApiUtils;

public class ProjectsPresenter extends BasePresenter {

    private ProjectsView mProjectsView;
    @Inject
    Storage mStorage;
    @Inject
    BehanceApi mApi;

    @Inject
    public ProjectsPresenter() {
    }

    public void setView(ProjectsView view){
        mProjectsView = view;
    }

    public void getProjects(){

        Log.d("myLogs", "getProjects");
        mCompositeDisposable.add(mApi.getProjects(BuildConfig.API_QUERY)
                .doOnSuccess(response -> {
                    mStorage.insertProjects(response);
                })
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mProjectsView.showLoading())
                .doFinally(() -> {
                    mProjectsView.hideLoading();
                    Log.d("myLogs", "doFinally");
                } )
                .subscribe(
                        response -> mProjectsView.showProjects(response.getProjects()),
                        throwable -> mProjectsView.showError()));
    }

    public void openProfileFragment(String userName){
        mProjectsView.openProfileFragment(userName);
    }
}
