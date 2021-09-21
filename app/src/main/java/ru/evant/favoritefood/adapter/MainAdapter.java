package ru.evant.favoritefood.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.evant.favoritefood.EditActivity;
import ru.evant.favoritefood.R;
import ru.evant.favoritefood.db.MyConstDB;
import ru.evant.favoritefood.db.MyDBManager;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context context;
    private List<ListItem> list;

    public MainAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    // нарисовать новый элемент в RecyclerView
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent, false);
        return new MyViewHolder(view, context, list);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private Context context;
        private List<ListItem> list;

        MyViewHolder(View itemView, Context context, List<ListItem> list) {
            super(itemView);
            this.context = context;
            this.list = list;
            tvTitle = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(this);
        }

        void setData(String title) {
            tvTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra(MyConstDB.LIST_ITEM_INTENT, list.get(getAdapterPosition()));
            intent.putExtra(MyConstDB.EDIT_STATE, false);
            context.startActivity(intent);
        }
    }

    // обновить адаптер
    public void updateAdapter(List<ListItem> newList) {
        list.clear();
        list.addAll(newList);

        // обновить адаптер
        notifyDataSetChanged();
    }

    // удалить элемент из адаптера и из БД
    public void removeItem(int position, MyDBManager myDbManager){
        myDbManager.delete(list.get(position).getId());   // удалить этот элемент из БД
        list.remove(position);                          // удалить из списка Адаптера для RecyclerView
        notifyItemRangeChanged(0, list.size()); // передать адаптеру, что список стал меньше(передать диапазон списка)
        notifyItemRemoved(position); // передать адаптеру позицию с которой удалили элемент
    }
}
