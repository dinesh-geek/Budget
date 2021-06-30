package com.arkapp.expensemanager.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.arkapp.expensemanager.R;
import com.arkapp.expensemanager.data.repository.PrefRepository;
import com.arkapp.expensemanager.utils.ViewUtilsKt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static androidx.navigation.fragment.NavHostFragment.findNavController;


/**
 * First screen fragment of the app. This is opened every time app is launched
 */
public final class SplashFragment extends Fragment {
    private PrefRepository prefRepository;

    public SplashFragment() {
        super(R.layout.fragment_splash);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefRepository = new PrefRepository(requireContext());
        initSignUpBtn();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.loadSplash();
    }

    private void initSignUpBtn() {
        if (!prefRepository.setLoggedIn()) {

            getView().findViewById(R.id.signUpBtn).setOnClickListener(it -> {
                if (!ViewUtilsKt.isDoubleClicked(1000L)) {
                    findNavController(this).navigate(R.id.action_splashFragment_to_signupFragment);
                }
            });
        } else
            ViewUtilsKt.hide(getView().findViewById(R.id.signUpBtn));

    }

    private void loadSplash() {
        Runnable runnable = () -> {
            if (prefRepository.setLoggedIn())
                findNavController(this).navigate(R.id.action_splashFragment_to_homeFragment);
        };

        new Handler().postDelayed(runnable, 2000);
    }

}
