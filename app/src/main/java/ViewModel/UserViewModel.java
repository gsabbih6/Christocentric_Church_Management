package ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.dighisoft.christocentric.Utils;

import Models.User;
import Request.UserRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserViewModel extends ViewModel {

    MutableLiveData<User> user;
    UserRequest service;

    public UserViewModel() {
        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(UserRequest.class);
    }

    public MutableLiveData<User> getUser(String username, String password) {
        if (user == null) {
            user = new MutableLiveData<>();
            loadUser(username, password);
        }
        return user;
    }

    private void loadUser(String username, String password) {

        // Make retrofit user calls

        service.loginUser(username, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    user.setValue(response.body());
                    Log.d("New user", "login successful");
                    Log.d("New user", response.body().getJwt());
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // will think about it later

                Log.e("User not success msg", t.getCause().getMessage());

            }
        });
    }
}
