package com.penner.architecture.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.penner.architecture.R;
import com.penner.architecture.view.databinding.DatabindingActivity;

import java.util.List;

/**
 * Created by penneryu on 16/8/5.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.CellViewHodler> {

    private Context context;
    private List<String> datas;

    public MainRecyclerAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public CellViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_recycler_item, parent, false);
        return new CellViewHodler(view);
    }

    @Override
    public void onBindViewHolder(final CellViewHodler holder, int position) {
        String data = datas.get(position);
        holder.title.setText(data);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                switch (position) {
                    case 0:
                        context.startActivity(new Intent(context, MvpActivity.class));
                        break;
                    case 1:
                        context.startActivity(new Intent(context, DaggerActivity.class));
                        break;
                    case 2:
                        context.startActivity(new Intent(context, DatabindingActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class CellViewHodler extends RecyclerView.ViewHolder {

        TextView title;

        public CellViewHodler(View view) {
            super(view);
            title = (TextView)itemView.findViewById(R.id.penner_recycler_txt_tile);
        }
    }
}
