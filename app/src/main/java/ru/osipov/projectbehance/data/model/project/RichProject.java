package ru.osipov.projectbehance.data.model.project;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RichProject {

    @Embedded
    public Project mProject;

    @Relation(entity = Owner.class, entityColumn = "project_id", parentColumn = "id")
    public List<Owner> mOwners;
}
