package com.lmd.thomas.gamechoice.ui.main

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.lmd.thomas.gamechoice.R
import com.lmd.thomas.gamechoice.multicast.MulticastGroup
import com.lmd.thomas.gamechoice.ui.anagram.AnagramActivity
import com.lmd.thomas.gamechoice.ui.wordsearch.gameplay.WordSearchActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

internal enum class Game {
    ANAGRAM, WORDSEARCH, PUZZLE
}

class MainActivity :
        AppCompatActivity() {

    private var book = "o_artista"
    private var word = 0
    private var level = -1
    private lateinit var multicastGroup: MulticastGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        startMulticastGroup()
        parseIntent()
        if (level < 0) {
            setContentView(R.layout.empty_activity)
            showDialogLevel()
//            level = 0
//            setContentView(R.layout.activity_main)
//            configViews()
        } else {
            setContentView(R.layout.activity_main)
            configViews()
        }
    }

    private fun parseIntent() {
        intent.extras?.let {bundle ->
            bundle.getString("params")?.let {
                val jsonObject = JSONObject(it)
                book = jsonObject.getString("book")
                level = jsonObject.getInt("level")
                word = jsonObject.getInt("word")
            } ?: run {
                level = 0
            }
        } ?: run {
            level = 0
        }
    }

    fun onClickButton(view: View) {
        val jsonObject = JSONObject()
        jsonObject.put("book", book)
        jsonObject.put("level", level)
        jsonObject.put("word", 0)
        var intent: Intent
        when (view.tag as Game) {
            Game.ANAGRAM -> {
                intent = Intent(this, AnagramActivity::class.java)
                intent.putExtra("params", jsonObject.toString())
                startActivity(intent)
            }
            Game.WORDSEARCH -> {
                intent = Intent(this, WordSearchActivity::class.java)
                intent.putExtra("params", jsonObject.toString())
                startActivity(intent)
            }
            Game.PUZZLE -> openAnotherApp("com.lmd.thomas.puzzle")
        }
    }

    private fun openAnotherApp(packageName: String) {
        val jsonObject = JSONObject()
        jsonObject.put("book", book)
        jsonObject.put("level", level)
        jsonObject.put("word", 0)
        var intent = this.packageManager.getLaunchIntentForPackage(packageName)
        if (intent == null) {
            intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("market://details?id=$packageName")
        }
        intent.putExtra("params", jsonObject.toString())
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun showDialogLevel() {
        val builder = AlertDialog.Builder(this)
        val items = arrayOf("6 Anos", "7 Anos", "8 Anos")
        builder.setItems(items) { _, which ->
            val message = which.toString()
            Log.e("MESSAGE", message)
            multicastGroup.sendMessage(false, message)
            finishAndRemoveTask()
        }
        builder.setTitle("Qual é a sua idade?")
        builder.setCancelable(false)
        builder.create().show()
    }

    private fun configViews() {
        when (book) {
            "o_artista" -> {
                title_1.setText(R.string.puzzle)
                title_2.setText(R.string.word_search)
                with(image_button_1) {
                    setImageResource(R.mipmap.anagram)
                    tag = Game.ANAGRAM
                }
                with(image_button_2) {
                    setImageResource(R.mipmap.wordsearch)
                    tag = Game.WORDSEARCH
                }
            }
            "o_barco" -> {
                title_1.setText(R.string.puzzle)
                title_2.setText(R.string.puzzle)
                with(image_button_1) {
                    setImageResource(R.mipmap.anagram)
                    tag = Game.ANAGRAM
                }
                with(image_button_2) {
                    setImageResource(R.mipmap.puzzle)
                    tag = Game.PUZZLE
                }
            }
            "o_ovo" -> {
                title_1.setText(R.string.puzzle)
                title_2.setText(R.string.word_search)
                with(image_button_1) {
                    setImageResource(R.mipmap.puzzle)
                    tag = Game.PUZZLE
                }
                with(image_button_2) {
                    setImageResource(R.mipmap.wordsearch)
                    tag = Game.WORDSEARCH
                }
            }
            else -> { }
        }
    }

    private fun startMulticastGroup() {
        val tag = "ACTION"
        val multicastIp = "230.192.0.19"
        val port = 1050
        multicastGroup = MulticastGroup(this, tag, multicastIp, port)
    }


}
