package com.blagro.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.blagro.R;
import com.blagro.model.MyPojo;

import java.util.List;

public class SpinnerProductAdapter extends ArrayAdapter<MyPojo> {

    LayoutInflater flater;

    public SpinnerProductAdapter(Activity context, int resouceId, int textviewId, List<MyPojo> list){
        super(context,resouceId,textviewId, list);
//        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    private View rowview(View convertView , int position){

        MyPojo myPojo = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.listitems_layout, null, false);

            holder.item_txt =  rowview.findViewById(R.id.item_txt);
            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }
        holder.item_txt.setText(myPojo.getName());

        return rowview;
    }

    private class viewHolder{
        TextView item_txt;
    }
}


