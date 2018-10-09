package com.lmd.thomas.gamechoice.framework;

import android.os.AsyncTask;

import com.lmd.thomas.gamechoice.wordSearchGenerator.generators.WordSearchGenerator;
import com.lmd.thomas.gamechoice.wordSearchGenerator.models.FillType;

import java.util.Random;

public class WordSearchManager {

    public final static int MIN_WORDLENGTH = 4;
    public final static int ADVANCED_MAX_WORDLENGTH = 11;
    public final static int ADVANCED_MAX_DIMENSION_OFFSET = 5;

    private final static int SIZE = 2;
    private static WordSearchManager mInstance;

    private String mWord;
    private Random mRandom;
    private WordSearchGenerator[] mWordSearchArray;

    private WordSearchManager() {
        mRandom = new Random();
        mWordSearchArray = new WordSearchGenerator[SIZE];
    }

    public static WordSearchManager getInstance() {
        if (mInstance == null)
            mInstance = new WordSearchManager();
        return mInstance;
    }

    public void buildWordSearches() {
        new BuildWordSearchArrayTask().execute();
    }

    public WordSearchGenerator getWordSearch(final int i) {
        if (i < 0)
            return buildWordSearch();
        WordSearchGenerator ret = mWordSearchArray[i % SIZE];
        new BuildWordSearchTask(i % SIZE).execute();
        return ret;
    }

    private WordSearchGenerator buildWordSearch() {
        int dimen = mWord.length();
        dimen += mRandom.nextInt(1);
        WordSearchGenerator gen = new WordSearchGenerator(dimen, dimen, mWord, FillType.RandomCharacters);
        gen.build();
        return gen;
    }

    public void Initialize(String word) {
        this.mWord = word;
    }

    private class BuildWordSearchArrayTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < SIZE; i++) {
                mWordSearchArray[i] = buildWordSearch();
            }
            return null;
        }
    }

    private class BuildWordSearchTask extends AsyncTask<Void, Void, Void> {

        private int i;

        public BuildWordSearchTask(int i) {
            this.i = i;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mWordSearchArray[i] = buildWordSearch();
            return null;
        }
    }
}
