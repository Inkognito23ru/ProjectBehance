package ru.osipov.projectbehance.ui.profile;

import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.osipov.projectbehance.common.BaseView;
import ru.osipov.projectbehance.data.model.user.User;

@StateStrategyType(SkipStrategy.class)
public interface ProfileView extends BaseView {
    void showProfile(User user);

}
