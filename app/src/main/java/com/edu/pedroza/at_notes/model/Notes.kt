package com.edu.pedroza.at_notes.model

import android.graphics.Bitmap

class Notes(
    var title: String? = null,
    var description: String? = null,
    val date: String? = null,
    val img: Bitmap? = null
)