package com.github.fgoncalves.canvas.data

import android.content.SharedPreferences
import javax.inject.Inject

class Settings @Inject constructor(val sharedPreferences: SharedPreferences) {
    var didWeBotherUserWithDrawer: Boolean
        get() {
            return sharedPreferences.getBoolean("showed.drawer", false)
        }
        set(value) {
            sharedPreferences.edit().putBoolean("showed.drawer", value).apply()
        }

}
