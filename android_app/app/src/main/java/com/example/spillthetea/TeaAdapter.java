package com.example.spillthetea;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TeaAdapter extends RecyclerView.Adapter<TeaAdapter.TeaViewHolder>{

    private ArrayList<TeaItem> teaItemArrayList;
    private OnTeaItemClickedListener onTeaItemClickedListener;

    public TeaAdapter(){
        this.teaItemArrayList = new ArrayList<TeaItem>();
        notifyDataSetChanged();
    }

    public interface OnTeaItemClickedListener {
        void onTeaItemClicked(TeaItem teaItem);
    }

    public TeaAdapter(OnTeaItemClickedListener onTeaItemClickedListener){
        this.onTeaItemClickedListener = onTeaItemClickedListener;
        this.teaItemArrayList = new ArrayList<TeaItem>();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TeaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.tea_list_item, parent, false);
        TeaViewHolder teaViewHolder = new TeaViewHolder(view);
        return teaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeaViewHolder holder, int position) {
        holder.bind(this.teaItemArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.teaItemArrayList.size();
    }

    public void addTea(TeaItem teaItem){
        this.teaItemArrayList.add(teaItem);
        notifyDataSetChanged();
    }

    class TeaViewHolder extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView time;

        public TeaViewHolder(@NonNull View itemView) {
            super(itemView);

            this.author = (TextView) itemView.findViewById(R.id.username_tv);
            this.time = (TextView) itemView.findViewById(R.id.timestamp_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTeaItemClickedListener.onTeaItemClicked(teaItemArrayList.get(getAdapterPosition()));
                }
            });

        }

        public void bind(TeaItem teaItem){
            this.author.setText(teaItem.username);

            //Format timestamp into real time for textview
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(teaItem.time);
            String timeForTV = formatter.format(calendar.getTime());

            this.time.setText(timeForTV);
        }

    }
}


