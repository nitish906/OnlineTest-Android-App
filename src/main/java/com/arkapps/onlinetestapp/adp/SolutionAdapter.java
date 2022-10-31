package com.arkapps.onlinetestapp.adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.arkapps.onlinetestapp.R;
import com.arkapps.onlinetestapp.cls.QuestionData;
import com.arkapps.onlinetestapp.databinding.SolutionListBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class SolutionAdapter extends RecyclerView.Adapter<SolutionAdapter.SolutionViewHolder> {

    ArrayList<QuestionData> arrayList;
    Context context;

    public SolutionAdapter(ArrayList<QuestionData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public SolutionViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        return new SolutionViewHolder(SolutionListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SolutionViewHolder holder, int position) {

        QuestionData data = arrayList.get(position);
        if (data == null) {
            return;
        }
        int qno = position+1;
        String question = "[Q."+qno+"]  " + data.getQuestion() + " ?";
        holder.binding.question.setText(question);

        holder.binding.option1.setText(data.getOptions().get(0));
        holder.binding.option2.setText(data.getOptions().get(1));
        holder.binding.option3.setText(data.getOptions().get(2));
        holder.binding.option4.setText(data.getOptions().get(3));

        int index = data.getOptions().indexOf(data.getAnswer());
         switch (index){
             case 0:
                 holder.binding.option1.setBackgroundColor(ContextCompat.getColor(context,R.color.green_light));
                 break;
             case 1:
                 holder.binding.option2.setBackgroundColor(ContextCompat.getColor(context,R.color.green_light));
                 break;
             case 2:
                 holder.binding.option3.setBackgroundColor(ContextCompat.getColor(context,R.color.green_light));
                 break;
             case 3:
                 holder.binding.option4.setBackgroundColor(ContextCompat.getColor(context,R.color.green_light));
                 break;
         }

         if (data.getUserAnswer() !=null && !data.getUserAnswer().equals(data.getAnswer()) ){
             //  User ans is wrong
             int index2 = data.getOptions().indexOf(data.getUserAnswer());
             switch (index2){
                 case 0:
                     holder.binding.option1.setBackgroundColor(ContextCompat.getColor(context,R.color.red));
                     break;
                 case 1:
                     holder.binding.option2.setBackgroundColor(ContextCompat.getColor(context,R.color.red));
                     break;
                 case 2:
                     holder.binding.option3.setBackgroundColor(ContextCompat.getColor(context,R.color.red));
                     break;
                 case 3:
                     holder.binding.option4.setBackgroundColor(ContextCompat.getColor(context,R.color.red));
                     break;
             }
         }







    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class SolutionViewHolder extends RecyclerView.ViewHolder {

        SolutionListBinding binding;

        public SolutionViewHolder(@NonNull @NotNull SolutionListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
