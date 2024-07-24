package com.orlovdanylo.fromonetoninegame.presentation.about_game

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.presentation.core.BaseFragment

class AboutGameFragment : BaseFragment<AboutGameViewModel>() {

    override val layoutId: Int = R.layout.fragment_about_game
    override val viewModel: AboutGameViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.ivSendMail).setOnClickListener {
            sendMail()
        }

        view.findViewById<ImageView>(R.id.btnRateApp).setOnClickListener {
            openGooglePlayAppPage()
        }
    }

    private fun sendMail() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(RECIPIENT_EMAIL))
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
            selector = Intent(Intent.ACTION_SENDTO).also { it.setData(Uri.parse("mailto:")) }
        }
        startActivity(Intent.createChooser(intent, ""))
    }

    private fun openGooglePlayAppPage() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setData(Uri.parse(APP_URL))
        }
        startActivity(intent)
    }

    companion object {
        private const val RECIPIENT_EMAIL = "danil.orlov.3022013@gmail.com"
        private const val APP_URL = "https://play.google.com/store/apps/details?id=com.orlovdanylo.fromonetoninegame"
    }

    override fun clear() = Unit
}