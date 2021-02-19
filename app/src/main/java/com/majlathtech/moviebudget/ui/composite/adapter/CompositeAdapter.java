package com.majlathtech.moviebudget.ui.composite.adapter;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = "CompositeAdapter";
    private List<AdapterDelegate> delegates = new ArrayList<>();

    protected void addAdapterDelegate(AdapterDelegate adapterDelegate) {
        delegates.add(adapterDelegate);
    }

    protected void removeAdapterDelegate(AdapterDelegate adapterDelegate) {
        delegates.remove(adapterDelegate);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        for (AdapterDelegate delegate : delegates) {
            if (delegate.getViewType() == viewType) {
                return delegate.onCreateViewHolder(parent);
            }
        }
        throw new RuntimeException("Unimplemented viewHolderType used");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        for (AdapterDelegate delegate : delegates) {
            if (delegate.getViewType() == getItemViewType(position)) {
                delegate.onBindViewHolder(holder, position);
                return;
            }
        }
        throw new RuntimeException("No adapter found for position:" + position + " with view type:" + getItemViewType(position));
    }

    public abstract T getItem(int position);
}
