package lalitjain.com;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import lalitjain.com.databinding.RecycleviewRowBinding;

public class Adapter extends RecyclerView.Adapter<Adapter.DataHolder> {
    private ArrayList<String> arrayList;

    public Adapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleviewRowBinding recycleviewRowBinding = RecycleviewRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);

        return new DataHolder(recycleviewRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {
        holder.binding.datatext.setText(arrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  class DataHolder extends RecyclerView.ViewHolder{
        private RecycleviewRowBinding binding;
        public DataHolder(RecycleviewRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}

