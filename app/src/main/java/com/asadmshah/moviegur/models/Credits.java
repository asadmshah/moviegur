package com.asadmshah.moviegur.models;

import android.os.Parcelable;

import com.asadmshah.moviegur.models.tmdb.CastMemberResponse;
import com.asadmshah.moviegur.models.tmdb.CreditsResponse;
import com.asadmshah.moviegur.models.tmdb.CrewMemberResponse;

import java.util.ArrayList;
import java.util.List;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class Credits implements Parcelable {

    public static Credits create(CreditsResponse response) {
        List<CastMember> castMembers = new ArrayList<>(response.cast.size());
        for (CastMemberResponse cmr : response.cast) {
            castMembers.add(CastMember.create(cmr));
        }

        List<CrewMember> crewMembers = new ArrayList<>(response.crew.size());
        for (CrewMemberResponse cmr : response.crew) {
            crewMembers.add(CrewMember.create(cmr));
        }

        return new AutoParcel_Credits(castMembers, crewMembers);
    }

    public abstract List<CastMember> castMembers();
    public abstract List<CrewMember> crewMembers();
}
