package ru.osipov.projectbehance.common;

import io.reactivex.disposables.CompositeDisposable;
import moxy.MvpPresenter;


public abstract class BasePresenter<V extends BaseView> extends MvpPresenter<V> {

    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void disposeAll(){
        mCompositeDisposable.clear();
    }
}
