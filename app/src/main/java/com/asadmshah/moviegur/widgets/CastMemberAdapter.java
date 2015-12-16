package com.asadmshah.moviegur.widgets;

import com.asadmshah.moviegur.models.CastMember;
import com.asadmshah.moviegur.models.ProfilePath;

import java.util.List;

public class CastMemberAdapter extends AbstractProductionMemberAdapter {

    private final List<CastMember> castMembers;

    public CastMemberAdapter(List<CastMember> castMembers) {
        this.castMembers = castMembers;
    }

    @Override
    String getTopText(int position) {
        return castMembers.get(position).character();
    }

    @Override
    ProfilePath getProfilePath(int position) {
        return castMembers.get(position).profilePath();
    }

    @Override
    String getBottomText(int position) {
        return castMembers.get(position).name();
    }

    @Override
    public int getItemCount() {
        return castMembers.size();
    }
}
