package com.ashhasib.architask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.ViewHolder> {

    //private List<Note> noteList= new ArrayList<>();

    private OnItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK= new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.getId()==t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note note, @NonNull Note t1) {
            return (note.getName().equals(t1.getName()) && note.getDesc().equals(t1.getDesc())
                    && note.getPriority()== t1.getPriority());
        }
    };






    public Note getNoteAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_note, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Note note= getItem(i);

        viewHolder.txtTitle.setText(note.getName());
        viewHolder.txtDesc.setText(note.getDesc());
        viewHolder.txtPriority.setText(String.valueOf(note.getPriority()));
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle, txtDesc, txtPriority;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle= itemView.findViewById(R.id.txtTitle);
            txtDesc= itemView.findViewById(R.id.txtDesc);
            txtPriority= itemView.findViewById(R.id.txtPriority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos= getAdapterPosition();
                    if(listener!=null && pos!=RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(pos));
                }
            });

        }
    }


    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener= listener;
    }
}
