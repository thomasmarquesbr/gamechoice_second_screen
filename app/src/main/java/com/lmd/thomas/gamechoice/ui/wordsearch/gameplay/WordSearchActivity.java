package com.lmd.thomas.gamechoice.ui.wordsearch.gameplay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;

import com.lmd.thomas.gamechoice.R;
import com.lmd.thomas.gamechoice.adapters.WordSearchPagerAdapter;
import com.lmd.thomas.gamechoice.base.BaseActivity;
import com.lmd.thomas.gamechoice.framework.WordSearchManager;
import com.lmd.thomas.gamechoice.models.GameDifficulty;
import com.lmd.thomas.gamechoice.models.Words;
import com.lmd.thomas.gamechoice.ui.anagram.AnagramActivity;
import com.lmd.thomas.gamechoice.ui.puzzle.PuzzleActivity;
import com.scalified.fab.ActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class WordSearchActivity
        extends BaseActivity
        implements WordSearchGridView.WordFoundListener {

    private String book = "o_artista";
    private int wordId = 0;
    private int gameDificulty = GameDifficulty.EASY;
    public static int currentItem;
    private ViewPager mViewPager;
    private WordSearchPagerAdapter mWordSearchPagerAdapter;
    private ActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();
        configViews();
    }

    private void parseIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            try {
                JSONObject jsonObject = new JSONObject(bundle.getString("params"));
                book = jsonObject.getString("book");
                gameDificulty = jsonObject.getInt("level");
                wordId = jsonObject.getInt("word");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() { }

    private void configViews() {
        WordSearchManager wsm = WordSearchManager.getInstance();
        wsm.Initialize(Words.get(book)[gameDificulty][wordId]);
        wsm.buildWordSearches();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.wordsearch_activity);
        actionButton = (ActionButton) findViewById(R.id.action_button);
//            actionButton = (ActionButton) findViewById(R.id.action_button);
        currentItem = 0;
        mWordSearchPagerAdapter = new WordSearchPagerAdapter(getFragmentManager(), getApplicationContext());
        mViewPager = (WordSearchViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mWordSearchPagerAdapter);
    }

    private void callNextGame() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("book", book);
            jsonObject.put("level", gameDificulty);
            jsonObject.put("word", 0);
            if (book.equals("o_artista"))
                callAnagram(jsonObject);
//                openAnotherApp("com.lmd.thomas.anagram", jsonObject);
            else
                callPuzzle(jsonObject);
//                openAnotherApp("com.lmd.thomas.puzzle", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void callAnagram(JSONObject jsonObject) {
        Intent intent = new Intent(this, AnagramActivity.class);
        intent.putExtra("params", jsonObject.toString());
        startActivity(intent);
    }

    private void callPuzzle(JSONObject jsonObject) {
        Intent intent = new Intent(this, PuzzleActivity.class);
        intent.putExtra("params", jsonObject.toString());
        startActivity(intent);
    }

    @Override
    public void notifyWordFound() {
        actionButton.show();
    }

    public void onClickNextButton(View view) {
        wordId++;
        if (wordId == Words.get(book)[gameDificulty].length) {
            callNextGame();
        } else {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("book", book);
                jsonObject.put("level", gameDificulty);
                jsonObject.put("word", wordId);
                Intent intent = new Intent(this, WordSearchActivity.class);
                intent.putExtra("params", jsonObject.toString());
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
