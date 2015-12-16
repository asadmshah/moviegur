package com.asadmshah.moviegur.models;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.asadmshah.moviegur.models.tmdb.CastMemberResponse;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class CastMember implements Parcelable {

    public static CastMember create(CastMemberResponse response) {
        Builder builder = new AutoParcel_CastMember.Builder();
        builder.id(response.id);
        builder.character(response.character);
        builder.name(response.name);
        if (response.profilePath != null) {
            builder.profilePath(ProfilePath.of(response.profilePath));
        }
        return builder.build();
    }

    public abstract long id();
    public abstract String character();
    public abstract String name();
    @Nullable public abstract ProfilePath profilePath();

    @AutoParcel.Builder
    public abstract static class Builder {
        public abstract Builder id(long l);
        public abstract Builder character(String s);
        public abstract Builder name(String s);
        public abstract Builder profilePath(ProfilePath pp);
        public abstract CastMember build();
    }
}
