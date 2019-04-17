package ua.dp.rename.dniprostreets.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.include_default_toolbar.*
import ua.dp.rename.dniprostreets.R
import ua.dp.rename.dniprostreets.util.RotationUtil

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_about)
        RotationUtil.lockScreenOrientation(this)
        toolbar.setTitle(R.string.screen_title_about)
        toolbar.setNavigationIcon(R.drawable.back_icon)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}
