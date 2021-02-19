package com.majlathtech.moviebudget.ui.composite.list;


import android.content.Context;

import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.ui.composite.adapter.CompositeAdapter;
import com.majlathtech.moviebudget.ui.composite.model.Header;
import com.majlathtech.moviebudget.ui.composite.model.Listable;
import com.majlathtech.moviebudget.ui.composite.model.Types;

import java.util.ArrayList;
import java.util.List;

public class AllAdapter extends CompositeAdapter<Listable> {
    private List<Listable> listables;

    public AllAdapter(Context context, List<Listable> listables) {
        this.listables = listables;
        addAdapterDelegate(new MovieDetailAdapterDelegate(context, this));
        addAdapterDelegate(new HeaderAdapterDelegate(context, this));
    }

    public void setItems(List<Listable> newList){
        this.listables = newList;
        notifyDataSetChanged();
    }

    public void clearItems(){
        this.listables = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listables.size();
    }

    @Override
    public Listable getItem(int position) {
        return listables.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        Listable listable = getItem(position);
        if (listable instanceof MovieDetail) {
            return Types.MOVIE;//Todo add type int into MovieDetail class?
        } else if (listable instanceof Header) {
            return Types.HEADER;
        } else {
            throw new RuntimeException("Item type not supported;");
        }
    }
}