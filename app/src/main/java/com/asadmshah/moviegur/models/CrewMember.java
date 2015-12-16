package com.asadmshah.moviegur.models;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.asadmshah.moviegur.models.tmdb.CrewMemberResponse;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class CrewMember implements Parcelable {

    public static CrewMember create(CrewMemberResponse response) {
        Builder builder = new AutoParcel_CrewMember.Builder();
        builder.id(response.id);
        builder.department(response.department);
        builder.job(response.job);
        builder.name(response.name);
        if (response.profilePath != null) {
            builder.profilePath(ProfilePath.of(response.profilePath));
        }
        return builder.build();
    }

    public abstract long id();
    public abstract String department();
    public abstract String job();
    public abstract String name();
    @Nullable public abstract ProfilePath profilePath();

    @AutoParcel.Builder
    public abstract static class Builder {
        public abstract Builder id(long l);
        public abstract Builder department(String s);
        public abstract Builder job(String s);
        public abstract Builder name(String s);
        public abstract Builder profilePath(ProfilePath pp);
        public abstract CrewMember build();
    }

}
