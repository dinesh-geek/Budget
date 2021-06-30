package com.arkapp.expensemanager.ui.signup;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.arkapp.expensemanager.data.models.UserLogin;
import com.arkapp.expensemanager.data.room.AppDatabase;
import com.arkapp.expensemanager.data.room.UserLoginDao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by Abdul Rehman on 08-06-2020.
 * Contact email - abdulrehman0796@gmail.com
 */
@SuppressLint("StaticFieldLeak")
class AsyncTask {

    public static class AddUserAsyncTask extends android.os.AsyncTask<Void, Void, Void> {
        private final Activity context;
        private final String username;
        private final String password;
        private final AddUserListener taskListener;

        public AddUserAsyncTask(@NotNull Activity context, @NotNull String username,
                                @NotNull String password, @NotNull AddUserListener taskListener) {
            this.context = context;
            this.username = username;
            this.password = password;
            this.taskListener = taskListener;
        }

        @Nullable
        @Override
        protected Void doInBackground(@NotNull Void... params) {
            UserLoginDao binding = (new AppDatabase.Companion()).getDatabase(context).userLoginDao();
            binding.insert(new UserLogin(null, username, password));
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint({"SetTextI18n"})
        @Override
        protected void onPostExecute(@Nullable Void unfinishedSummary) {
            this.taskListener.onTaskEnded();
        }
    }

    public static class GetLoggedInUserAsyncTask extends android.os.AsyncTask<Void, Void, List<UserLogin>> {
        private final Activity context;
        private final String username;
        private final String password;
        private final AddUserListener taskListener;

        public GetLoggedInUserAsyncTask(@NotNull Activity context, @NotNull String username,
                                        @NotNull String password,
                                        @NotNull AddUserListener taskListener) {
            this.context = context;
            this.username = username;
            this.password = password;
            this.taskListener = taskListener;
        }

        @Nullable
        @Override
        protected List<UserLogin> doInBackground(@NotNull Void... params) {
            UserLoginDao userLoginDao = (new AppDatabase.Companion()).getDatabase(context).userLoginDao();
            List<UserLogin> data = userLoginDao.getLoggedInUser(username, password);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return data;
        }

        @SuppressLint({"SetTextI18n"})
        @Override
        protected void onPostExecute(@Nullable List<UserLogin> data) {
            this.taskListener.onTaskEnded(data);
        }
    }

    public static class CheckLoggedInUserAsyncTask extends android.os.AsyncTask<Void, Void, List<UserLogin>> {
        private final Activity context;
        private final String username;
        private final AddUserListener taskListener;

        public CheckLoggedInUserAsyncTask(@NotNull Activity context, @NotNull String username,
                                          @NotNull AddUserListener taskListener) {
            this.context = context;
            this.username = username;
            this.taskListener = taskListener;
        }

        @Nullable
        @Override
        protected List<UserLogin> doInBackground(@NotNull Void... params) {
            UserLoginDao userLoginDao = (new AppDatabase.Companion()).getDatabase(context).userLoginDao();
            return userLoginDao.checkLoggedInUser(username);
        }

        @SuppressLint({"SetTextI18n"})
        @Override
        protected void onPostExecute(@Nullable List<UserLogin> data) {
            this.taskListener.onTaskEnded(data);
        }
    }
}
