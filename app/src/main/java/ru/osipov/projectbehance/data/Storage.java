package ru.osipov.projectbehance.data;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.ArrayList;
import java.util.List;
import ru.osipov.projectbehance.data.database.BehanceDao;
import ru.osipov.projectbehance.data.model.project.Cover;
import ru.osipov.projectbehance.data.model.project.Owner;
import ru.osipov.projectbehance.data.model.project.Project;
import ru.osipov.projectbehance.data.model.project.ProjectResponse;
import ru.osipov.projectbehance.data.model.project.RichProject;
import ru.osipov.projectbehance.data.model.user.Image;
import ru.osipov.projectbehance.data.model.user.User;
import ru.osipov.projectbehance.data.model.user.UserResponse;

public class Storage {

    public static final int PAGE_SIZE = 30;
    private BehanceDao mBehanceDao;

    public Storage(BehanceDao behanceDao) {
        mBehanceDao = behanceDao;
    }

    public void insertProjects(ProjectResponse response) {
        insertProjects(response.getProjects());

    }

    public void insertProjects(List<Project> projects){
        mBehanceDao.insertProjects(projects);
        List<Owner> owners = getOwners(projects);
        mBehanceDao.clearOwnerTable();
        mBehanceDao.insertOwners(owners);
    }

    private List<Owner> getOwners(List<Project> projects) {

        List<Cover> covers = new ArrayList<>();
        List<Owner> owners = new ArrayList<>();
        for (int i = 0; i < projects.size(); i++) {

            Owner owner = projects.get(i).getOwners().get(0);
            owner.setId(i);
            owner.setProjectId(projects.get(i).getId());
            owners.add(owner);
        }
        return owners;
    }

    public LiveData<List<RichProject>> getProjectsLive(){
        return mBehanceDao.getProjectsLive();
    }

    public LiveData<PagedList<RichProject>> getProjectsPaged(){
        return new LivePagedListBuilder<>(mBehanceDao.getProjectsPaged(), PAGE_SIZE).build();
    }

    public ProjectResponse getProjects() {
        List<Project> projects = mBehanceDao.getProjects();
        for (Project project : projects) {
            project.setOwners(mBehanceDao.getOwnersFromProject(project.getId()));
        }

        ProjectResponse response = new ProjectResponse();
        response.setProjects(projects);

        return response;
    }

    public void insertUser(UserResponse response) {
        User user = response.getUser();
        Image image = user.getImage();
        image.setId(user.getId());
        image.setUserId(user.getId());

        mBehanceDao.insertUser(user);
        mBehanceDao.insertImage(image);
    }

    public UserResponse getUser(String username) {
        User user = mBehanceDao.getUserByName(username);
        Image image = mBehanceDao.getImageFromUser(user.getId());
        user.setImage(image);

        UserResponse response = new UserResponse();
        response.setUser(user);

        return response;
    }

    public interface StorageOwner {
        Storage obtainStorage();
    }

}
