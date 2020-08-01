package ru.osipov.projectbehance.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import ru.osipov.projectbehance.data.model.project.Cover;
import ru.osipov.projectbehance.data.model.project.Owner;
import ru.osipov.projectbehance.data.model.project.Project;
import ru.osipov.projectbehance.data.model.user.Image;
import ru.osipov.projectbehance.data.model.user.User;

@Database(entities = {Project.class, Cover.class, Owner.class, User.class, Image.class}, version = 1)
public abstract class BehanceDatabase extends RoomDatabase {

    public abstract BehanceDao getBehanceDao();
}
