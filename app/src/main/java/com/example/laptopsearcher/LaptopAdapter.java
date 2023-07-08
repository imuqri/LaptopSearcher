package com.example.laptopsearcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.List;

public class LaptopAdapter extends BaseAdapter {

    private Context context;
    private List<Laptop> laptopList;

    public LaptopAdapter(Context context, List<Laptop> laptopList) {
        this.context = context;
        this.laptopList = laptopList;
    }

    @Override
    public int getCount() {
        return laptopList.size();
    }

    @Override
    public Object getItem(int position) {
        return laptopList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_laptop, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.laptopImageView = view.findViewById(R.id.laptopImageView);
            viewHolder.laptopNameTextView = view.findViewById(R.id.laptopNameTextView);
            viewHolder.lowestPriceTextView = view.findViewById(R.id.lowestPriceTextView);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Laptop laptop = laptopList.get(position);

        // Set the laptop image using Glide (replace with your own image loading library or method)
        Glide.with(context)
                .load(laptop.getImageUrl())
                .into(viewHolder.laptopImageView);

        viewHolder.laptopNameTextView.setText(laptop.getName());
        viewHolder.lowestPriceTextView.setText(laptop.getLowestPrice());

        return view;
    }

    private static class ViewHolder {
        ImageView laptopImageView;
        TextView laptopNameTextView;
        TextView lowestPriceTextView;
    }
}
