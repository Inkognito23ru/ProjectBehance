package ru.osipov.projectbehance.data.model.project;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

@Entity
public class Cover implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "cover_id")
    private int mId;

    @ColumnInfo(name = "photo_url")
    @SerializedName("202")
    private String mPhotoUrl;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(@NonNull String photoUrl) {
        mPhotoUrl = photoUrl;
    }
}
