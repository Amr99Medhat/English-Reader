package com.example.englishreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.Window
import android.widget.Toast
import kotlinx.android.synthetic.main.first.*
import java.util.*

class First : AppCompatActivity() {
    private var tToS: TextToSpeech? = null
    private var res: Int? = null
    private var v: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.first)

        checkStatus()
        btn_read.setOnClickListener {
            v = checkBeforeSpeak()
            speak(v)
        }
        btn_Stop.setOnClickListener { stop() }
    }

    private fun checkStatus() {
        tToS = TextToSpeech(this, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                res = tToS!!.setLanguage(Locale.ENGLISH)

                if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(this, "Language Not Supported", Toast.LENGTH_SHORT).show()
                } else {
                    btn_read.isEnabled = true
                    btn_Stop.isEnabled = true

                }
            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkBeforeSpeak(): Int {
        val text = ed_text.text.toString().toUpperCase(Locale.ROOT)

        for (i in text) {
            if (i != '0' && i != '1' && i != '2' && i != '3' && i != '4'
                && i != '5' && i != '6' && i != '7' && i != '8' && i != '9'
                && i != 'A' && i != 'B' && i != 'C' && i != 'D' && i != 'E'
                && i != 'F' && i != 'G' && i != 'H' && i != 'I' && i != 'J'
                && i != 'K' && i != 'L' && i != 'M' && i != 'N' && i != 'O'
                && i != 'P' && i != 'Q' && i != 'R' && i != 'S' && i != 'T'
                && i != 'U' && i != 'V' && i != 'W' && i != 'X' && i != 'Y'
                && i != 'Z' && i != '.' && i != '!' && i != '?' && i != ','
                && i != ':' && i != ';' && i != '‘' && i != '-' && i != '"'
                && i != '_' && i != ' ' && i != '\'' && i != '\n' && i != '\r'
                && i != '\t'
            ) {
                Toast.makeText(this, "Missing Data", Toast.LENGTH_SHORT).show()
                return 0
            }
        }
        return 1
    }

    private fun speak(s: Int) {
        if (s == 1) {
            val text = ed_text.text

            var pitch: Float = (seek_bar_pitch.progress / 50f)
            if (pitch < 0.1) pitch = 0.1f

            var speed: Float = (seek_bar_speed.progress / 50f)
            if (speed < 0.1) speed = 0.1f

            tToS!!.setPitch(pitch)
            tToS!!.setSpeechRate(speed)
            tToS!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    private fun stop() {
        tToS!!.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        tToS!!.stop()
        tToS!!.shutdown()
    }
}