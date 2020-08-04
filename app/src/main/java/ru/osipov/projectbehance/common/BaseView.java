package ru.osipov.projectbehance.common;

import moxy.MvpView;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(SkipStrategy.class)
public interface BaseView extends MvpView {

    void showLoading();

    void hideLoading();

    void showError();
}
