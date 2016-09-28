package com.dylanonfb.android.bandhupdates;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niteen on 15-09-2016.
 */
public class BandhListAdapter extends ArrayAdapter<BandhList> {

    Context context;
    SimpleDateFormat sdf,sdfStart;
    ArrayList<BandhList> bandhLists;
    public BandhListAdapter(Context context, int resource, ArrayList<BandhList> objects) {
        super(context, resource, objects);
        this.context=context;
        this.bandhLists=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        sdf = new SimpleDateFormat("dd MMMM, yyyy hh:mm a");
        sdfStart = new SimpleDateFormat("EEE dd MMM, yyyy");
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvOrganizer=(TextView) convertView.findViewById(R.id.tvListOrganizer);
            viewHolder.tvDetail=(TextView) convertView.findViewById(R.id.tvListDetail);
            viewHolder.tvDate=(TextView) convertView.findViewById(R.id.tvListDate);
            viewHolder.tvDuration=(TextView) convertView.findViewById(R.id.tvListDuration);
            viewHolder.ppvFbProfilePic=(ProfilePictureView) convertView.findViewById(R.id.ppvFbProfilePic);
            viewHolder.ppvFbProfilePic.setPresetSize(ProfilePictureView.SMALL);
            viewHolder.tvUpdateTime=(TextView) convertView.findViewById(R.id.tvUpdateTime);
            viewHolder.tvFbName=(TextView) convertView.findViewById(R.id.tvFbName);
            convertView.setTag(viewHolder);

        }else{
            viewHolder=(ViewHolder) convertView.getTag();
            viewHolder.ppvFbProfilePic.setDrawingCacheEnabled(false);
        }
        String fbId=bandhLists.get(position).getProfileId();
        viewHolder.tvOrganizer.setText(bandhLists.get(position).getOrganizer());
        viewHolder.tvDetail.setText(bandhLists.get(position).getDetail());
        viewHolder.tvDate.setText("Starts from " +sdfStart.format(bandhLists.get(position).getDate()));
        viewHolder.tvDuration.setText("Duration: "+bandhLists.get(position).getDuration());
        viewHolder.ppvFbProfilePic.setProfileId(bandhLists.get(position).getProfileId());
        viewHolder.tvUpdateTime.setText(sdf.format(bandhLists.get(position).getUpdateTime()));
        viewHolder.tvFbName.setText(bandhLists.get(position).getProfileName());
        return convertView;
    }
    class ViewHolder {
        TextView tvOrganizer;
        TextView tvDetail;
        TextView tvDate;
        TextView tvDuration;
        ProfilePictureView ppvFbProfilePic;
        TextView tvUpdateTime;
        TextView tvFbName;
    }
}
