package com.pedo.animecatalog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_popup.*

class PopupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup)

        findNavController(R.id.nav_host_popup)
            .setGraph(R.navigation.nav_popup,intent.extras)

        btn_close_popup.setOnClickListener {
            this.finish()
        }
    }

    override fun onBackPressed() {
        //do nothing
    }
}
