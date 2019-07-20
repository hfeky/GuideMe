package com.guideme.guideme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhraseAdapter extends RecyclerView.Adapter<PhraseAdapter.ViewHolder> {

    private CommonPhrasesActivity context;
    private List<PhraseItem> phraseItems;

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

    public PhraseAdapter(CommonPhrasesActivity context, List<PhraseItem> phraseItems) {
        this.context = context;
        this.phraseItems = phraseItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phrase, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PhraseItem phraseItem = phraseItems.get(position);
        holder.englishPhrase.setText(phraseItem.getEnglishPhrase());
        holder.arabicPhrase.setText(phraseItem.getArabicPhrase());
        holder.arabicPhraseEnglishLetters.setText(phraseItem.getArabicPhraseEnglishLetters());
        holder.phraseIcon.setImageResource(phraseItem.getPhraseIcon());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Play sound
            }
        });
    }

    @Override
    public int getItemCount() {
        return phraseItems.size();
    }
}
