package com.github.icarohs7.unoxandroidarch.extensions

import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.github.icarohs7.unoxandroidarch.TestApplication
import com.github.icarohs7.unoxandroidarch.mockActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldEqual

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class ViewExtensionsKtTest {
    @Test
    fun `should add listeners to edit text`() {
        val (_, act) = mockActivity<AppCompatActivity>()
        val editText = EditText(act)

        var a = ""
        editText.onTextChange(onChange = { s, _, _, _ -> a = "$s" })
        editText.setText("Omai wa mou shindeiru!")
        a shouldEqual "Omai wa mou shindeiru!"

        var b = ""
        editText.onTextChange(beforeChange = { s, _, _, _ -> b = "$s" })
        editText.setText("Hello!")
        a shouldEqual "Hello!"
        b shouldEqual "Omai wa mou shindeiru!"

        var c = ""
        editText.onTextChange(afterChange = { s -> c = "$s" })
        editText.setText("NANI!?")
        a shouldEqual "NANI!?"
        b shouldEqual "Hello!"
        c shouldEqual "NANI!?"
    }
}