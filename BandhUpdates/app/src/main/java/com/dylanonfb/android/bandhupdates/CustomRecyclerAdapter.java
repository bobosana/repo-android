package com.dylanonfb.android.bandhupdates;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Niteen on 21-09-2016.
 */

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolder> {
    private ArrayList<BandhList> recyclerBandhList;
    private SimpleDateFormat sdf,sdfStart;

class CustomViewHolder extends RecyclerView.ViewHolder{
    TextView tvOrganizer;
    TextView tvDetail;
    TextView tvDate;
    TextView tvDuration;
    ProfilePictureView ppvFbProfilePic;
    TextView tvUpdateTime;
    TextView tvFbName;
    public CustomViewHolder(View view){
        super(view);
        tvOrganizer=(TextView) view.findViewById(R.id.tvListOrganizer);
        tvDetail=(TextView) view.findViewById(R.id.tvListDetail);
        tvDate=(TextView) view.findViewById(R.id.tvListDate);
        tvDuration=(TextView) view.findViewById(R.id.tvListDuration);
        ppvFbProfilePic=(ProfilePictureView) view.findViewById(R.id.ppvFbProfilePic);
        ppvFbProfilePic.setPresetSize(ProfilePictureView.SMALL);
        tvUpdateTime=(TextView) view.findViewById(R.id.tvUpdateTime);
        tvFbName=(TextView) view.findViewById(R.id.tvFbName);
    }


}

public CustomRecyclerAdapter(ArrayList<BandhList> list){
    this.recyclerBandhList=list;
    this.sdf = new SimpleDateFormat("dd MMMM, yyyy hh:mm a");
    this.sdfStart = new SimpleDateFormat("EEE dd MMM, yyyy");
}

    @Override
    public CustomRecyclerAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new CustomViewHolder(item);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        if(recyclerBandhList!=null) {
            holder.tvOrganizer.setText(recyclerBandhList.get(position).getOrganizer());
            holder.tvDetail.setText(recyclerBandhList.get(position).getDetail());
            holder.tvDate.setText("Starts from " + sdfStart.format(recyclerBandhList.get(position).getDate()));
            holder.tvDuration.setText("Duration: " + recyclerBandhList.get(position).getDuration());
            holder.ppvFbProfilePic.setProfileId(recyclerBandhList.get(position).getProfileId());
            holder.tvUpdateTime.setText(sdf.format(recyclerBandhList.get(position).getUpdateTime()));
            holder.tvFbName.setText(recyclerBandhList.get(position).getProfileName());
        }
    }

    @Override
    public int getItemCount() {
        if(recyclerBandhList!=null) {
            return recyclerBandhList.size();
        }
        else {
            return 0;
        }
    }
}
