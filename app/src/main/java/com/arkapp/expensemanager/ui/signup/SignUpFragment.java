package com.arkapp.expensemanager.ui.signup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.arkapp.expensemanager.R;
import com.arkapp.expensemanager.data.models.UserLogin;
import com.arkapp.expensemanager.data.repository.PrefRepository;
import com.arkapp.expensemanager.databinding.FragmentSignupBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static androidx.navigation.fragment.NavHostFragment.findNavController;
import static com.arkapp.expensemanager.utils.Constants.ENTERED_USER_NAME;
import static com.arkapp.expensemanager.utils.ViewUtilsKt.disableTouch;
import static com.arkapp.expensemanager.utils.ViewUtilsKt.enableTouch;
import static com.arkapp.expensemanager.utils.ViewUtilsKt.hide;
import static com.arkapp.expensemanager.utils.ViewUtilsKt.isDoubleClicked;
import static com.arkapp.expensemanager.utils.ViewUtilsKt.show;
import static com.arkapp.expensemanager.utils.ViewUtilsKt.showSnack;
import static com.arkapp.expensemanager.utils.ViewUtilsKt.toastShort;


public class SignUpFragment extends Fragment {
    private FragmentSignupBinding binding;
    private PrefRepository prefRepository;


    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater);
        FragmentSignupBinding binding = this.binding;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefRepository = new PrefRepository(requireContext());

        //Initializing the button listeners for sign up and sign in
        binding.signUpBtn.setOnClickListener(v -> {
            if (isDoubleClicked(1000)) return;

            if (binding.signUpBtn.getText() == getString(R.string.sign_up)) {
                binding.signUpBtn.setText(getString(R.string.login));
                binding.signUpDesc.setText(getString(R.string.already_have_account));

                hide(binding.loginCard);
                show(binding.signUpCard);

                binding.userNameEt.setText("");
                binding.passwordEt.setText("");

                binding.userName.setError(null);
                binding.password.setError(null);

            } else {
                binding.signUpBtn.setText(getString(R.string.sign_up));
                binding.signUpDesc.setText(getString(R.string.not_have_an_account));

                hide(binding.signUpCard);
                show(binding.loginCard);

                binding.signUpUserNameEt.setText("");
                binding.signUpPasswordEt.setText("");
                binding.signUpConfirmPasswordEt.setText("");

                binding.signUpUserName.setError(null);
                binding.signUpPassword.setError(null);
                binding.signUpConfirmPassword.setError(null);
            }
        });

        binding.loginBtn.setOnClickListener(v -> {
            if (isDoubleClicked(1000))
                return;
            show(binding.loginProgress);
            disableTouch(requireActivity().getWindow());
            onLoginClicked();
        });

        binding.insideSignUpBtn.setOnClickListener(v -> {
            if (isDoubleClicked(1000)) return;
            show(binding.signupProgress);
            disableTouch(requireActivity().getWindow());
            onSignUpClicked();
        });

        removeErrorOnChange();
    }

    //Validating all data before signup
    private void onSignUpClicked() {
        if (TextUtils.isEmpty(binding.signUpUserNameEt.getText())) {
            binding.signUpUserName.setError("Username required!");

            hide(binding.signupProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (binding.signUpUserNameEt.getText().length() < 3) {
            binding.signUpUserName.setError("Invalid Username!");

            hide(binding.signupProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (TextUtils.isEmpty(binding.signUpPasswordEt.getText())) {
            binding.signUpPassword.setError("Password required!");

            hide(binding.signupProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (binding.signUpPasswordEt.getText().length() < 3) {
            binding.signUpPassword.setError("Invalid Password! Length should be more than 4");

            hide(binding.signupProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (TextUtils.isEmpty(binding.signUpConfirmPasswordEt.getText())) {
            binding.signUpConfirmPassword.setError("Password required!");

            hide(binding.signupProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (!binding.signUpConfirmPasswordEt.getText().toString().equals(binding.signUpPasswordEt.getText().toString())) {
            binding.signUpConfirmPassword.setError("Password incorrect!");

            hide(binding.signupProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        checkIfUserNameExist();
    }

    //Validating all data before login
    private void onLoginClicked() {
        if (TextUtils.isEmpty(binding.userNameEt.getText())) {
            binding.userName.setError("Username required!");

            hide(binding.loginProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        if (TextUtils.isEmpty(binding.passwordEt.getText())) {
            binding.password.setError("Password required!");

            hide(binding.loginProgress);
            enableTouch(requireActivity().getWindow());
            return;
        }

        checkCredentials();
    }

    //Used to check the credential of user in the SQL table
    private void checkCredentials() {
        AddUserListener taskListener = new AddUserListener() {
            public void onTaskEnded() {
            }

            public void onTaskEnded(@Nullable List<UserLogin> userData) {

                ENTERED_USER_NAME = binding.userNameEt.getText().toString();

                if (userData != null && userData.isEmpty()) {
                    showSnack(binding.parent, "Login failed!");
                    binding.userName.setError("Check username and password!");
                    hide(binding.loginProgress);
                    enableTouch(requireActivity().getWindow());
                } else {
                    hide(binding.loginProgress);
                    enableTouch(requireActivity().getWindow());
                    onLoginSuccess();
                }
            }
        };
        new AsyncTask.GetLoggedInUserAsyncTask(requireActivity(),
                                               binding.userNameEt.getText().toString(),
                                               binding.passwordEt.getText().toString(),
                                               taskListener)
                .execute();

    }

    //Check if the user is exist before the signup in SQL tables
    private void checkIfUserNameExist() {
        AddUserListener taskListener = new AddUserListener() {
            public void onTaskEnded() {
            }

            public void onTaskEnded(@Nullable List<UserLogin> userData) {
                if (userData != null && !userData.isEmpty()) {
                    showSnack(binding.parent, "Signup failed!");
                    binding.signUpUserName.setError("Username already exits!");
                    hide(binding.signupProgress);
                    enableTouch(requireActivity().getWindow());
                } else
                    storeCredentials();
            }
        };

        new AsyncTask.CheckLoggedInUserAsyncTask(
                requireActivity(),
                binding.signUpUserNameEt.getText().toString(),
                taskListener)
                .execute();
    }

    //Opening the app on successfull signup or login
    private void onLoginSuccess() {
        toastShort(requireContext(), "Login success");
        prefRepository.setLoggedIn(true);
        findNavController(this).navigate(R.id.action_signupFragment_to_splashFragment);
    }

    //Store the signup credential in the SQL table
    private void storeCredentials() {


        AddUserListener taskListener = new AddUserListener() {
            public void onTaskEnded() {
                ENTERED_USER_NAME = binding.signUpUserNameEt.getText().toString();
                hide(binding.signupProgress);
                enableTouch(requireActivity().getWindow());
                onLoginSuccess();
            }

            public void onTaskEnded(@Nullable List<UserLogin> data) {
            }
        };
        new AsyncTask.AddUserAsyncTask(
                requireActivity(),
                binding.signUpUserNameEt.getText().toString(),
                binding.signUpPasswordEt.getText().toString(),
                taskListener)
                .execute();
    }

    //Remove error on changing the values.
    private void removeErrorOnChange() {
        binding.userNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.userName.setError(null);
            }
        });
        binding.passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.password.setError(null);
            }
        });
        binding.signUpUserNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.signUpUserName.setError(null);
            }
        });


        binding.signUpPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.signUpPassword.setError(null);
            }
        });

        binding.signUpConfirmPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.signUpConfirmPassword.setError(null);
            }
        });
    }
}
