package com.molaapp.adapter;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.molaapp.iptv.ChannelDetailsActivity;
import com.molaapp.iptv.R;
import com.molaapp.item.ItemChannel;
import com.molaapp.util.Constant;
import com.github.ornolfr.ratingview.RatingView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Activity context;
    private ArrayList<ItemChannel> mList;

    public SliderAdapter(Activity context, ArrayList<ItemChannel> itemChannels) {
        this.context = context;
        this.mList = itemChannels;
        inflater = context.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View imageLayout = inflater.inflate(R.layout.row_slider_item, container, false);
        assert imageLayout != null;
        ImageView imageView = imageLayout.findViewById(R.id.image);
        TextView channelName = imageLayout.findViewById(R.id.text);
        TextView channelCategory = imageLayout.findViewById(R.id.textCategory);
        RatingView ratingView = imageLayout.findViewById(R.id.ratingView);
        RelativeLayout rootLayout = imageLayout.findViewById(R.id.rootLayout);

        final ItemChannel itemChannel = mList.get(position);
        Picasso.get().load(itemChannel.getImage()).placeholder(R.drawable.placeholder).into(imageView);
        channelName.setText(itemChannel.getChannelName());
        channelCategory.setText(itemChannel.getChannelCategory());
        ratingView.setRating(Float.parseFloat(itemChannel.getChannelAvgRate()));
        ratingView.setVisibility(View.GONE);

        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChannelDetailsActivity.class);
                intent.putExtra(Constant.CHANNEL_ID, itemChannel.getId());
                intent.putExtra(Constant.CHANNEL_TITLE, itemChannel.getChannelName());
                intent.putExtra(Constant.CATEGORY_NAME, itemChannel.getChannelCategory());
                intent.putExtra(Constant.CHANNEL_IMAGE, itemChannel.getImage());
                intent.putExtra(Constant.CHANNEL_AVG_RATE, itemChannel.getChannelAvgRate());
                intent.putExtra(Constant.CHANNEL_URL, itemChannel.getChannelUrl());
                context.startActivity(intent);
            }
        });

        container.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        (container).removeView((View) object);
    }
}
