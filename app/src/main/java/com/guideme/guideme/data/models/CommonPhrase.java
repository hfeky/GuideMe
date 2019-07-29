package com.guideme.guideme.data.models;

public class CommonPhrase {

    private String englishPhrase, arabicPhrase, arabicPhraseEnglishLetters;
    private int phraseIcon;

    public CommonPhrase(String englishPhrase, String arabicPhrase, String arabicPhraseEnglishLetters, int phraseIcon) {
        this.englishPhrase = englishPhrase;
        this.arabicPhrase = arabicPhrase;
        this.arabicPhraseEnglishLetters = arabicPhraseEnglishLetters;
        this.phraseIcon = phraseIcon;
    }

    public String getEnglishPhrase() {
        return englishPhrase;
    }

    public void setEnglishPhrase(String englishPhrase) {
        this.englishPhrase = englishPhrase;
    }

    public String getArabicPhrase() {
        return arabicPhrase;
    }

    public void setArabicPhrase(String arabicPhrase) {
        this.arabicPhrase = arabicPhrase;
    }

    public String getArabicPhraseEnglishLetters() {
        return arabicPhraseEnglishLetters;
    }

    public void setArabicPhraseEnglishLetters(String arabicPhraseEnglishLetters) {
        this.arabicPhraseEnglishLetters = arabicPhraseEnglishLetters;
    }

    public int getPhraseIcon() {
        return phraseIcon;
    }

    public void setPhraseIcon(int phraseIcon) {
        this.phraseIcon = phraseIcon;
    }
}
