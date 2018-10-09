package com.lmd.thomas.gamechoice.ui.anagram

import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.amulyakhare.textdrawable.TextDrawable
import com.lmd.thomas.gamechoice.R
import com.lmd.thomas.gamechoice.extension.shuffleString
import com.lmd.thomas.gamechoice.ui.wordsearch.gameplay.WordSearchActivity
import kotlinx.android.synthetic.main.activity_anagram.*
import org.json.JSONObject

class AnagramActivity : AppCompatActivity() {

    private var book = "o_artista"
    private var level = 0
    private var wordId = 0
    private var width = 110
    private var height = 110
    private var countMatchs = 0
    private var countFullChild = 0
    private var totalContainers = 0
    private var totalHits = 0
    private val alreadyHit = false
    private val listLevels = mutableListOf<Array<String>>()
    private val backgroundsArtista = arrayOf(
            arrayOf(
                    intArrayOf(R.drawable.gato, R.drawable.gato_1, R.drawable.gato_2, R.drawable.gato_3, R.drawable.gato_4),
                    intArrayOf(R.drawable.vaca, R.drawable.vaca_1, R.drawable.vaca_2, R.drawable.vaca_3, R.drawable.vaca_4),
                    intArrayOf(R.drawable.bode, R.drawable.bode_1, R.drawable.bode_2, R.drawable.bode_3, R.drawable.bode_4),
                    intArrayOf(R.drawable.vagalume, R.drawable.vagalume_1, R.drawable.vagalume_2, R.drawable.vagalume_3, R.drawable.vagalume_4, R.drawable.vagalume_5, R.drawable.vagalume_6, R.drawable.vagalume_7, R.drawable.vagalume_8, R.drawable.vagalume_9)),
            arrayOf(
                    intArrayOf(R.drawable.lapis, R.drawable.lapis_1, R.drawable.lapis_2, R.drawable.lapis_3, R.drawable.lapis_4, R.drawable.lapis_5),
                    intArrayOf(R.drawable.besouro, R.drawable.besouro_1, R.drawable.besouro_2, R.drawable.besouro_3, R.drawable.besouro_4, R.drawable.besouro_5, R.drawable.besouro_6, R.drawable.besouro_7),
                    intArrayOf(R.drawable.caracol, R.drawable.caracol_1, R.drawable.caracol_2, R.drawable.caracol_3, R.drawable.caracol_4, R.drawable.caracol_5, R.drawable.caracol_6, R.drawable.caracol_7),
                    intArrayOf(R.drawable.galinha, R.drawable.galinha_1, R.drawable.galinha_2, R.drawable.galinha_3, R.drawable.galinha_4, R.drawable.galinha_5, R.drawable.galinha_6, R.drawable.galinha_7)),
            arrayOf(
                    intArrayOf(R.drawable.joaninha, R.drawable.joaninha_1, R.drawable.joaninha_2, R.drawable.joaninha_3, R.drawable.joaninha_4, R.drawable.joaninha_5, R.drawable.joaninha_6, R.drawable.joaninha_7, R.drawable.joaninha_8),
                    intArrayOf(R.drawable.zebra, R.drawable.zebra_1, R.drawable.zebra_2, R.drawable.zebra_3, R.drawable.zebra_4, R.drawable.zebra_5),
                    intArrayOf(R.drawable.lagarta, R.drawable.lagarta_1, R.drawable.lagarta_2, R.drawable.lagarta_3, R.drawable.lagarta_4, R.drawable.lagarta_5, R.drawable.lagarta_6, R.drawable.lagarta_7),
                    intArrayOf(R.drawable.pincel, R.drawable.pincel_1, R.drawable.pincel_2, R.drawable.pincel_3, R.drawable.pincel_4, R.drawable.pincel_5, R.drawable.pincel_6)))

    private val backgroundsBarco = arrayOf(
            arrayOf(
                    intArrayOf(R.drawable.indio, R.drawable.indio_1, R.drawable.indio_2, R.drawable.indio_3, R.drawable.indio_4, R.drawable.indio_5),
                    intArrayOf(R.drawable.jacare, R.drawable.jacare_1, R.drawable.jacare_2, R.drawable.jacare_3, R.drawable.jacare_4, R.drawable.jacare_5, R.drawable.jacare_6),
                    intArrayOf(R.drawable.mata, R.drawable.mata_1, R.drawable.mata_2, R.drawable.mata_3, R.drawable.mata_4),
                    intArrayOf(R.drawable.rio, R.drawable.rio_1, R.drawable.rio_2, R.drawable.rio_3)),
            arrayOf(
                    intArrayOf(R.drawable.barco, R.drawable.barco_1, R.drawable.barco_2, R.drawable.barco_3, R.drawable.barco_4, R.drawable.barco_5),
                    intArrayOf(R.drawable.mar, R.drawable.mar_1, R.drawable.mar_2, R.drawable.mar_3),
                    intArrayOf(R.drawable.onaa, R.drawable.onaa_1, R.drawable.onaa_2, R.drawable.onaa_3, R.drawable.onaa_4),
                    intArrayOf(R.drawable.peixes, R.drawable.peixes_1, R.drawable.peixes_2, R.drawable.peixes_3, R.drawable.peixes_4, R.drawable.peixes_5, R.drawable.peixes_6)),
            arrayOf(
                    intArrayOf(R.drawable.montanhas, R.drawable.montanhas_1, R.drawable.montanhas_2, R.drawable.montanhas_3, R.drawable.montanhas_4, R.drawable.montanhas_5, R.drawable.montanhas_6, R.drawable.montanhas_7, R.drawable.montanhas_8, R.drawable.montanhas_9),
                    intArrayOf(R.drawable.passaro, R.drawable.passaro_1, R.drawable.passaro_2, R.drawable.passaro_3, R.drawable.passaro_4, R.drawable.passaro_5, R.drawable.passaro_6, R.drawable.passaro_7),
                    intArrayOf(R.drawable.tartaruga, R.drawable.tartaruga_1, R.drawable.tartaruga_2, R.drawable.tartaruga_3, R.drawable.tartaruga_4, R.drawable.tartaruga_5, R.drawable.tartaruga_6, R.drawable.tartaruga_7, R.drawable.tartaruga_8, R.drawable.tartaruga_9)))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar
        actionBar?.hide()
        setContentView(R.layout.activity_anagram)
        intent.extras?.let {
            val jsonObject = JSONObject(it.getString("params"))
            book = jsonObject.getString("book")
            level = jsonObject.getInt("level")
            wordId = jsonObject.getInt("word")
        }
        initWords()
        with(root_linear_layout) {
            background = if (book == "o_barco")
                getDrawable(backgroundsBarco[level][wordId][0])
            else
                getDrawable(backgroundsArtista[level][wordId][0])
        }
        initContainers()
    }

    override fun onBackPressed() { }

    private fun initWords() {
        val level1: Array<String>
        val level2: Array<String>
        val level3: Array<String>
        if (book == "o_barco") {
            level1 = resources.getStringArray(R.array.level1_barco)
            level2 = resources.getStringArray(R.array.level2_barco)
            level3 = resources.getStringArray(R.array.level3_barco)
        } else {
            level1 = resources.getStringArray(R.array.level1_artista)
            level2 = resources.getStringArray(R.array.level2_artista)
            level3 = resources.getStringArray(R.array.level3_artista)
        }
        listLevels.add(level1)
        listLevels.add(level2)
        listLevels.add(level3)
    }

    private fun initContainers() {
        val word = listLevels[level][wordId]
        if (word.length > 5) {
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val heightScreen = size.y
            height = heightScreen * width / 1920
            width = height
        }

        val randomWord = word.shuffleString()
        for (i in 0 until word.length) {
            val relativeLayoutWord = RelativeLayout(this)
            val llp = LinearLayout.LayoutParams(width, height)
            llp.leftMargin = 4
            llp.rightMargin = 4
            relativeLayoutWord.layoutParams = llp
            relativeLayoutWord.tag = word[i]
            relativeLayoutWord.setOnDragListener(MyDragListener(i))
            relativeLayoutWord.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.alphaBlack))
            relativeLayoutWord.background = getDrawable(R.drawable.layout_rounded)
            panel_word.addView(relativeLayoutWord)

            val relativeLayoutLetter = RelativeLayout(this)
            val ll = LinearLayout.LayoutParams(width, height)
            ll.leftMargin = 4
            ll.rightMargin = 4
            relativeLayoutLetter.layoutParams = ll
            relativeLayoutLetter.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.alphaBlack))
            relativeLayoutLetter.background = getDrawable(R.drawable.layout_rounded)
            relativeLayoutLetter.tag = "null"
            val textDrawable = TextDrawable.builder()
                    .beginConfig()
                    .width(width)
                    .height(height)
                    .textColor(Color.WHITE)
                    .endConfig()
                    .buildRect(randomWord[i].toString(), Color.TRANSPARENT)
            val imageView = ImageView(this)
            imageView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            imageView.setImageDrawable(textDrawable)
            imageView.tag = randomWord[i]
            imageView.setOnTouchListener(MyTouchListener())
            relativeLayoutLetter.addView(imageView)
            panel_letters.addView(relativeLayoutLetter)
        }
        totalContainers = word.length
    }

    fun onClickNextButton(view: View) {
        wordId++
        val backgrounds = if (book == "o_barco")
            backgroundsBarco
        else
            backgroundsArtista
        if (wordId == backgrounds[level].size) {
            wordId = 0
            val jsonObject = JSONObject()
            jsonObject.put("book", book)
            jsonObject.put("level", level)
            jsonObject.put("word", wordId)
            if (book == "o_barco") {
//                openAnotherApp("com.lmd.thomas.puzzle")
                callPuzzleActivity(jsonObject)
            } else
                callWordSearchActivity(jsonObject)
//                openAnotherApp("com.lapic.thomas.wordsearch")
        } else {
            val intent = Intent(this, AnagramActivity::class.java)
            val jsonObject = JSONObject()
            jsonObject.put("book", book)
            jsonObject.put("level", level)
            jsonObject.put("word", wordId)
            intent.putExtra("params", jsonObject.toString())
            startActivity(intent)
        }
    }

    private fun callPuzzleActivity(jsonObject: JSONObject) {
//        val intent = Intent(this, Puzzl)
    }

    private fun callWordSearchActivity(jsonObject: JSONObject) {
        val intent = Intent(this, WordSearchActivity::class.java)
        intent.putExtra("params", jsonObject.toString())
        startActivity(intent)
    }

    internal inner class MyTouchListener : View.OnTouchListener {

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            return if (event.action == MotionEvent.ACTION_DOWN) {
                val shadowBuilder = View.DragShadowBuilder(v)
                v.startDrag(null, shadowBuilder, v, 0)
                true
            } else
                false
        }

    }

    internal inner class MyDragListener(private val id: Int) : View.OnDragListener {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        override fun onDrag(v: View, event: DragEvent): Boolean {
            val action = event.action
            when (action) {
                DragEvent.ACTION_DRAG_STARTED -> return true
                DragEvent.ACTION_DRAG_ENTERED -> { }
                DragEvent.ACTION_DRAG_LOCATION -> { }
                DragEvent.ACTION_DRAG_EXITED -> { }
                DragEvent.ACTION_DROP -> {
                    val view = event.localState as View
                    val from = view.parent as ViewGroup
                    val to = v as RelativeLayout

                    if (to.childCount > 0) {
                        //existe view no container destino, então é feito o swap das imagens
                        val viewSwap = to.getChildAt(0)
                        from.removeView(view)
                        to.addView(view)
                        view.visibility = View.VISIBLE

                        to.removeView(viewSwap)
                        from.addView(viewSwap)
                        viewSwap.visibility = View.VISIBLE
                    } else {
                        //não existe img no container destino, então é adicionado a img normalmente
                        from.removeView(view)
                        to.addView(view)
                        view.visibility = View.VISIBLE
                    }

                    //Faz a contagem de acertos
                    countMatchs = 0
                    countFullChild = 0
                    for (i in 0 until totalContainers) {
                        val containerTemp = panel_word.getChildAt(i) as RelativeLayout
                        //verifica se existe img no container
                        if (containerTemp.childCount > 0) {
                            countFullChild++
                            val imgTemp = containerTemp.getChildAt(0) as ImageView
                            //verifica se houve acerto
                            if (imgTemp.tag.toString() == containerTemp.tag.toString())
                                countMatchs++
                        }
                    }
                    val imageDrawable = if (book == "o_barco")
                        getDrawable(backgroundsBarco[level][wordId][countMatchs])
                    else
                        getDrawable(backgroundsArtista[level][wordId][countMatchs])
                    root_linear_layout.background = imageDrawable

                    //verifica se acertou todas as silabas
                    if (countMatchs == totalContainers) {
                        if (!alreadyHit) //ainda não acertou o game
                            totalHits++
                        action_button.show()
                    } else {
                        if (alreadyHit && totalHits > 0)
                            totalHits--
                        action_button.hide()
                    }
                    return true
                }
                DragEvent.ACTION_DRAG_ENDED -> { }
            }
            return false
        }
    }
}
