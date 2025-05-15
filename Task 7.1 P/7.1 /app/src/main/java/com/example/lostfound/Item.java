package com.example.lostfound;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Item extends RecyclerView.Adapter<Item.ViewBox> {

    Context c;
    List<String[]> d;

    public Item(Context c, List<String[]> d) {
        this.c = c;
        this.d = d;
    }

    @NonNull
    @Override
    public ViewBox onCreateViewHolder(@NonNull ViewGroup group, int viewType) {
        View v = LayoutInflater.from(group.getContext()).inflate(R.layout.item, group, false);
        return new ViewBox(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBox box, int pos) {
        String[] one = d.get(pos);
        box.t.setText(one[6].toUpperCase() + ": " + one[1]);
    }

    @Override
    public int getItemCount() {
        return d.size();
    }

    public class ViewBox extends RecyclerView.ViewHolder {
        TextView t;

        public ViewBox(View v) {
            super(v);
            t = v.findViewById(R.id.tv_item_name);

            v.setOnClickListener(view -> {
                Intent go = new Intent(c, Viewlist.class);
                go.putExtra("data", d.get(getAdapterPosition()));
                c.startActivity(go);
            });
        }
    }
}
