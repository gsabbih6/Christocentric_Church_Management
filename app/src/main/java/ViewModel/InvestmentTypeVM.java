package ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.dighisoft.christocentric.Utils;

import java.util.List;

import Models.Church;
import Models.Investment;
import Models.UserDBModel;
import Request.ChurchRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InvestmentTypeVM extends ViewModel {


    MutableLiveData<List<Investment>> invest;
    ChurchRequest service;

    public InvestmentTypeVM() {
        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ChurchRequest.class);
    }

    public MutableLiveData<List<Investment>> getInvestments(int churchID) {
        if (invest == null) {
            invest = new MutableLiveData<>();
            loadUser(churchID);
        }
        return invest;
    }

    private void loadUser(int churchID) {

        // Make retrofit InvestTyp calls
        service.getInvestments(churchID
                , UserDBModel.getUser().get(0).jwt
        ).enqueue(new Callback<Church>() {
            @Override
            public void onResponse(Call<Church> call, Response<Church> response) {
                if(response.isSuccessful()){
                    invest.setValue(response.body().getInvestments());
                }

            }

            @Override
            public void onFailure(Call<Church> call, Throwable t) {
                Log.e("User not success msg", t.getCause().getMessage());
            }
        });


    }

}
