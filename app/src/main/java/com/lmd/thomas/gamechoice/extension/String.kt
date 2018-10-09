package com.lmd.thomas.gamechoice.extension

import java.util.*

fun String.shuffleString() : String {
    val letters = Arrays.asList<String>(*this.split("".toRegex()).dropLastWhile {
        it.isEmpty()
    }.toTypedArray())
    letters.shuffle()
    var shuffled = ""
    for (letter in letters) {
        shuffled += letter
    }
    return shuffled
}
