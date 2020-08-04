package ru.osipov.projectbehance.common;

import moxy.MvpAppCompatFragment;

public abstract class PresenterFragment extends MvpAppCompatFragment {

    protected abstract BasePresenter getPresenter();

    @Override
    public void onDetach() {
        if (getPresenter() != null){
            getPresenter().disposeAll();
        }
        super.onDetach();
    }
}
