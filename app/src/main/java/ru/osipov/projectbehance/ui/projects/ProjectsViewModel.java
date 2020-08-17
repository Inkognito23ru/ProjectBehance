package ru.osipov.projectbehance.ui.projects;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.osipov.projectbehance.BuildConfig;
import ru.osipov.projectbehance.data.Storage;
import ru.osipov.projectbehance.data.model.project.ProjectResponse;
import ru.osipov.projectbehance.data.model.project.RichProject;
import ru.osipov.projectbehance.utils.ApiUtils;

public class ProjectsViewModel extends ViewModel {

    private Disposable mDisposable;
    private Storage mStorage;

    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            updateProjects();
        }
    };

    private MutableLiveData<Boolean> mIsErrorVisible = new MutableLiveData<>();

    private LiveData<PagedList<RichProject>> mProjects;
    private ProjectsAdapter.OnItemClickListener mOnItemClickListener;

    public ProjectsViewModel(Storage storage, ProjectsAdapter.OnItemClickListener onItemClickListener) {
        mStorage = storage;
        mOnItemClickListener = onItemClickListener;
        mProjects = mStorage.getProjectsPaged();
        updateProjects();
    }


    private void updateProjects() {

        mDisposable = ApiUtils.getApiService().getProjects(BuildConfig.API_QUERY)
                .map(ProjectResponse::getProjects)
                .doOnSuccess(response -> mIsErrorVisible.postValue(false))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doFinally(() -> mIsLoading.postValue(false))
                .subscribe(response -> {
                            mStorage.insertProjects(response);
                        },
                        throwable -> {
                            mIsErrorVisible.postValue(true);
                            mIsErrorVisible.postValue(mProjects == null || mProjects.getValue().size() == 0);
                        });

    }

    @Override
    public void onCleared() {
        mStorage = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    public ProjectsAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }

    public MutableLiveData<Boolean> getIsErrorVisible() {
        return mIsErrorVisible;
    }

    public LiveData<PagedList<RichProject>> getProjects() {
        return mProjects;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }
}
