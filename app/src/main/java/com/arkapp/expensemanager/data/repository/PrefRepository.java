package com.arkapp.expensemanager.data.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.arkapp.expensemanager.data.models.UserLogin;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.arkapp.expensemanager.data.preferences.constants.PREF_LOGGED_IN;
import static com.arkapp.expensemanager.data.preferences.constants.PREF_USER_LOGIN;

/**
 * This is the UTILITY class for using shared preferences easily.
 */

public class PrefRepository {
    private SharedPreferences pref;
    private Editor editor;
    private Gson gson;

    @SuppressLint("CommitPrefEdits")
    public PrefRepository(@NotNull Context context) {
        this.pref = context.getSharedPreferences("PARTY_PLANER_MAIN_PREF", 0);
        this.editor = pref.edit();
        this.gson = new Gson();
    }

    public void clearData() {
        editor.clear();
        editor.commit();
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(PREF_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public boolean setLoggedIn() {
        return pref.getBoolean(PREF_LOGGED_IN, false);
    }

    @Nullable
    public UserLogin getCurrentUser() {
        String var1 = pref.getString(PREF_USER_LOGIN, "");
        if (var1.isEmpty()) {
            return null;
        } else
            return gson.fromJson(var1, UserLogin.class);
    }

    public void setCurrentUser(@NotNull UserLogin userLogin) {
        String var10002 = gson.toJson(userLogin);
        editor.putString(PREF_USER_LOGIN, var10002);
        editor.commit();
    }
}
