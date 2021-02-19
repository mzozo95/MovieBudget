package com.majlathtech.moviebudget.ui.movielist.adapter;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.ui.composite.adapter.AdapterDelegate;
import com.majlathtech.moviebudget.ui.composite.adapter.CompositeAdapter;
import com.majlathtech.moviebudget.ui.composite.model.Header;
import com.majlathtech.moviebudget.ui.composite.model.Types;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderAdapterDelegate extends AdapterDelegate<HeaderAdapterDelegate.ViewHolder> {

    private final CompositeAdapter adapter;

    public HeaderAdapterDelegate(Context context, CompositeAdapter adapter) {
        super(context);
        this.adapter = adapter;
    }

    @Override
    public int getViewType() {
        return Types.HEADER;
    }

    @Override
    public HeaderAdapterDelegate.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View contentView = getInflater().inflate(R.layout.li_header, parent, false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Header delivery = (Header) adapter.getItem(position);
        holder.tvTitle.setText(delivery.getName());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
        }
    }
}