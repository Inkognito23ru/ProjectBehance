package ru.osipov.projectbehance.ui.projects;

import ru.osipov.projectbehance.data.model.project.Project;
import ru.osipov.projectbehance.data.model.project.RichProject;
import ru.osipov.projectbehance.utils.DateUtils;

public class ProjectListItemViewModel {

    private static final int FIRST_OWNER_INDEX = 0;

    private String mImageUrl;
    private String mName;
    private String mUserName;
    private String mPublisheOn;

    public ProjectListItemViewModel(RichProject item){
        mImageUrl = item.mProject.getCover().getPhotoUrl();
        mName = item.mProject.getName();
        if (item.mOwners != null && item.mOwners.size() != 0){
            mUserName = item.mOwners.get(FIRST_OWNER_INDEX).getUsername();
            mPublisheOn = DateUtils.format(item.mProject.getPublishedOn());
        }
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
