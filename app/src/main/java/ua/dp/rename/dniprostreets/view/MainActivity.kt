package ua.dp.rename.dniprostreets.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ua.dp.rename.dniprostreets.R
import ua.dp.rename.dniprostreets.util.RotationUtil

class MainActivity : AppCompatActivity() {

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)
      RotationUtil.lockScreenOrientation(this)
   }
}
