package com.example.humberto.raites_uabc;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.humberto.raites_uabc.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecyclerViewHelper {

    public interface ItemRenderer {
        void render(View root, JSONObject object) throws JSONException;
    }

    public static void config(Activity activity, RecyclerView recycler, final int layout, final List<JSONObject> items, final ItemRenderer renderer) {
        recycler.setLayoutManager(new LinearLayoutManager(activity));
        recycler.setAdapter(new RecyclerView.Adapter() {
            private View root;

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                root = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
                return new RecyclerView.ViewHolder(root) {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                JSONObject object = items.get(i);
                try {
                    renderer.render(root, object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public int getItemCount() {
                return items.size();
            }
        });
    }

}
