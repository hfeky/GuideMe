package com.guideme.guideme.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.guideme.guideme.R;
import com.guideme.guideme.data.models.CommonPhrase;

import java.util.List;

public class CommonPhrasesAdapter extends RecyclerView.Adapter<CommonPhrasesAdapter.ViewHolder> {

    private CommonPhrasesActivity context;
    private List<CommonPhrase> commonPhrases;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public ImageView phraseIcon;
        public TextView englishPhrase, arabicPhrase, arabicPhraseEnglishLetters;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            phraseIcon = view.findViewById(R.id.phraseIcon);
            englishPhrase = view.findViewById(R.id.englishPhrase);
            arabicPhrase = view.findViewById(R.id.arabicPhrase);
            arabicPhraseEnglishLetters = view.findViewById(R.id.arabicPhraseEnglishLetters);
        }
    }

    public CommonPhrasesAdapter(CommonPhrasesActivity context, List<CommonPhrase> commonPhrases) {
        this.context = context;
        this.commonPhrases = commonPhrases;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_phrase, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CommonPhrase commonPhrase = commonPhrases.get(position);
        holder.englishPhrase.setText(commonPhrase.getEnglishPhrase());
        holder.arabicPhrase.setText(commonPhrase.getArabicPhrase());
        holder.arabicPhraseEnglishLetters.setText(commonPhrase.getArabicPhraseEnglishLetters());
        holder.phraseIcon.setImageResource(commonPhrase.getPhraseIcon());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Play sound.
            }
        });
    }

    @Override
    public int getItemCount() {
        return commonPhrases.size();
    }
}
