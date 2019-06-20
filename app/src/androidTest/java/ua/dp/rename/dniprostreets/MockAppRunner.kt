package ua.dp.rename.dniprostreets

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class MockAppRunner : AndroidJUnitRunner() {

   @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
   override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
      return super.newApplication(cl, MockApp::class.java.name, context)
   }
}
