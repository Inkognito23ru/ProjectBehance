package ru.osipov.projectbehance.ui.projects;

import ru.osipov.projectbehance.data.model.project.Project;
import ru.osipov.projectbehance.utils.DateUtils;

public class ProjectListItemViewModel {

    private static final int FIRST_OWNER_INDEX = 0;

    private String mImageUrl;
    private String mName;
    private String mUserName;
    private String mPublisheOn;

    public ProjectListItemViewModel(Project project){
        mImageUrl = project.getCover().getPhotoUrl();
        mName = project.getName();
        mUserName = project.getOwners().get(FIRST_OWNER_INDEX).getUsername();
        mPublisheOn = DateUtils.format(project.getPublishedOn());
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getName() {
        return mName;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getPublisheOn() {
        return mPublisheOn;
    }
}
