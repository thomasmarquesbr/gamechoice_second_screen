package com.lmd.thomas.gamechoice.ui.puzzle

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.lmd.thomas.gamechoice.R
import com.lmd.thomas.gamechoice.ui.anagram.AnagramActivity
import com.lmd.thomas.gamechoice.ui.wordsearch.gameplay.WordSearchActivity
import kotlinx.android.synthetic.main.activity_puzzle.*
import org.json.JSONObject

class PuzzleActivity :
        AppCompatActivity(), Runnable, View.OnTouchListener {

    private var squareRootNum = 2
    private var level = 0
    private var word = 0
    private var book = "o_barco"
    private val imagesBarco = arrayOf(
            intArrayOf(R.mipmap.mar_colored, R.mipmap.rio_colored, R.mipmap.jacare_colored, R.mipmap.indio_colored),
            intArrayOf(R.mipmap.barco_colored, R.mipmap.peixes_colored, R.mipmap.onca_colored, R.mipmap.mar_colored),
            intArrayOf(R.mipmap.tartaruga_colored, R.mipmap.montanhas_colored, R.mipmap.passaro_colored))
    private val imagesOvo = arrayOf(
            intArrayOf(R.mipmap.ovo_colored, R.mipmap.gato_colored, R.mipmap.pato_colored, R.mipmap.tucano_colored),
            intArrayOf(R.mipmap.tucano_colored, R.mipmap.coruja_colored, R.mipmap.raposa_colored, R.mipmap.bichos_colored),
            intArrayOf(R.mipmap.filhote_colored, R.mipmap.familia_colored, R.mipmap.gamba_colored))
    private val listImages = mutableMapOf<String, Array<IntArray>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzzle)
        val actionBar = supportActionBar
        actionBar?.hide()
        initListImage()
        parseIntent()
        initViews()
    }

    private fun initListImage() {
        listImages["o_barco"] = imagesBarco
        listImages["o_ovo"] = imagesOvo
    }

    private fun parseIntent() {
        intent.extras?.let {
            val jsonObject = JSONObject(it.getString("params"))
            book = jsonObject.getString("book")
            level = jsonObject.getInt("level")
            word = jsonObject.getInt("word")
        }
    }

    private fun initViews() {
        val drawableId = listImages[book]!![level][word]
        image_view_tips.setImageResource(drawableId)
        text_view_tips.setOnTouchListener(this)
        puzzle_layout.setImage(drawableId, squareRootNum)
        puzzle_layout.setOnCompleteCallback {
            Toast.makeText(this@PuzzleActivity, R.string.next, Toast.LENGTH_LONG).show()
            puzzle_layout.postDelayed(this@PuzzleActivity, 800)
        }
    }

    private fun callNextGame() {
        val jsonObject = JSONObject()
        jsonObject.put("book", book)
        jsonObject.put("level", level)
        jsonObject.put("word", 0)
        if (book == "o_ovo")
            callWordSearch(jsonObject)
        else
            callAnagram(jsonObject)
    }

    private fun callWordSearch(jsonObject: JSONObject) {
        val intent = Intent(this, WordSearchActivity::class.java)
        intent.putExtra("params" ,jsonObject.toString())
        startActivity(intent)
    }

    private fun callAnagram(jsonObject: JSONObject) {
        val intent = Intent(this, AnagramActivity::class.java)
        intent.putExtra("params" ,jsonObject.toString())
        startActivity(intent)
    }

    override fun run() {
        word++
        if (word >= listImages[book]!![level].size) {
            Toast.makeText(this@PuzzleActivity, R.string.complete, Toast.LENGTH_SHORT).show()
            callNextGame()
        } else {
            val drawableId = listImages[book]!![level][word]
            image_view_tips.setImageResource(drawableId)
            puzzle_layout.setImage(drawableId, squareRootNum)
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> image_view_tips.visibility = View.VISIBLE
            else -> image_view_tips.visibility = View.GONE
        }
        return true
    }
}
