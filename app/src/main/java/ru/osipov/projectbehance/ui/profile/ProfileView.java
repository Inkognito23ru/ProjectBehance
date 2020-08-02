package ru.osipov.projectbehance.ui.profile;

import ru.osipov.projectbehance.common.BaseView;
import ru.osipov.projectbehance.data.model.user.User;

public interface ProfileView extends BaseView {

    void showProfile(User user);

}
