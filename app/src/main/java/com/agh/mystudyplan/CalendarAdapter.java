package com.agh.mystudyplan;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CalendarAdapter extends ArrayAdapter<MySubject> {

    private ArrayList<MySubject> calendarList;
    private LayoutInflater mInflater;

    public CalendarAdapter(@NonNull Context context, @LayoutRes int resource,
                              @NonNull ArrayList<MySubject> objects) {
        super(context, resource, objects);
        calendarList = objects;
        mInflater = LayoutInflater.from(context);
    }

    private static class ViewHolder {
        TextView startTime;
        TextView endTime;
        TextView subject;
        TextView subjectType;
        RelativeLayout layout;
        int position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.calendar_row, null);

            holder = new ViewHolder();
            holder.layout = (RelativeLayout) convertView.findViewById(R.id.calendar_layout);
            holder.startTime = (TextView) convertView.findViewById(R.id.start_time);
            holder.endTime = (TextView) convertView.findViewById(R.id.end_time);
            holder.subject = (TextView) convertView.findViewById(R.id.subject);
            holder.subjectType = (TextView) convertView.findViewById(R.id.subject_type);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.startTime.setText(calendarList.get(position).getStartHour());
        holder.endTime.setText(calendarList.get(position).getEndHour());
        holder.subject.setText(calendarList.get(position).getSubject());
        holder.subjectType.setText(calendarList.get(position).getSubjectType());
        holder.position = position;

        return convertView;
    }
}
