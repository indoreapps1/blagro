package com.blagro.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.blagro.R;
import com.blagro.activity.ViewDistributorOrderActivity;
import com.blagro.model.MyPojo;

import java.util.List;

public class AreaAdapter extends ArrayAdapter<MyPojo> {

    LayoutInflater flater;

    public AreaAdapter(Activity context, int resouceId, int textviewId, List<MyPojo> list) {
        super(context, resouceId, textviewId, list);
//        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView, position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView, position);
    }

    private View rowview(View convertView, int position) {

        MyPojo myPojo = getItem(position);

        AreaAdapter.viewHolder holder;
        View rowview = convertView;
        if (rowview == null) {

            holder = new AreaAdapter.viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.items_area_layout, null, false);

            holder.item_txt = rowview.findViewById(R.id.item_txt);
            rowview.setTag(holder);
        } else {
            holder = (AreaAdapter.viewHolder) rowview.getTag();
        }
        holder.item_txt.setText(""+myPojo.getArea());

        return rowview;
    }

    private class viewHolder {
        TextView item_txt;
    }
}


