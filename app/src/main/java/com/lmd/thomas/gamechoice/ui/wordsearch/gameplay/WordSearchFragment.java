package com.lmd.thomas.gamechoice.ui.wordsearch.gameplay;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lmd.thomas.gamechoice.R;

public class WordSearchFragment extends Fragment {

    private WordSearchGridView mGrid;

    public WordSearchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wordsearch_fragment, container, false);
        TextView tv = (TextView) rootView.findViewById(R.id.section_label);
        mGrid = (WordSearchGridView) rootView.findViewById(R.id.gridView);
        mGrid.setWordFoundListener((WordSearchGridView.WordFoundListener) getActivity());
        tv.setText(mGrid.getWord());
        return rootView;
    }

}
