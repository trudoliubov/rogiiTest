package com.rogii.core.extentions.kotlin

fun CharSequence.unescapeUnicode(): String {
    val builder = StringBuilder()
    var temp: String = ""
    for (char in this) {
        if (char == '\\') {
            temp += char
        } else {
            if (temp.isBlank()) {
                builder.append(char)
            } else {
                temp += char
                if (temp.length == 2 && char != 'u') {
                    builder.append(temp)
                    temp = ""
                }
                if (temp.length < 6) {
                    continue
                } else {
                    builder.append(temp.substring(2).toInt(16).toChar())
                    temp = ""
                }
            }
        }
    }
    return builder.toString()
}