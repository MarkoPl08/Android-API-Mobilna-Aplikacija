package hr.algebra.lol_projekt

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import mehdi.sakout.aboutpage.AboutPage


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        return AboutPage(context)
            .isRTL(false)
            .setDescription(getString(hr.algebra.lol_projekt.R.string.aboutDescription))
            .enableDarkMode(true)
            .setImage(hr.algebra.lol_projekt.R.drawable.teemo)
            .addGroup(getString(hr.algebra.lol_projekt.R.string.aboutContantGroup))
            .addEmail(getString(hr.algebra.lol_projekt.R.string.aboutEmail))
            .addFacebook(getString(hr.algebra.lol_projekt.R.string.aboutFacebook))
            .addTwitter(getString(hr.algebra.lol_projekt.R.string.aboutTwitter))
            .addInstagram(getString(hr.algebra.lol_projekt.R.string.aboutInstagram))
            .create();
    }

}