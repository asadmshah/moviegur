package com.asadmshah.moviegur.widgets;

import com.asadmshah.moviegur.models.CrewMember;
import com.asadmshah.moviegur.models.ProfilePath;

import java.util.List;

public class CrewMemberAdapter extends AbstractProductionMemberAdapter {

    private final List<CrewMember> crewMembers;

    public CrewMemberAdapter(List<CrewMember> crewMembers) {
        this.crewMembers = crewMembers;
    }

    @Override
    String getTopText(int position) {
        return crewMembers.get(position).job();
    }

    @Override
    ProfilePath getProfilePath(int position) {
        return crewMembers.get(position).profilePath();
    }

    @Override
    String getBottomText(int position) {
        return crewMembers.get(position).name();
    }

    @Override
    public int getItemCount() {
        return crewMembers.size();
    }
}
