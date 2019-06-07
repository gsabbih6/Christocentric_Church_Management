package ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.dighisoft.christocentric.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import Models.Payment;
import Models.PaymentB;
import Request.PaymentRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentViewModel extends ViewModel {

    private final PaymentRequest service;
    MutableLiveData<List<PaymentB>> paymentList;

    public PaymentViewModel() {
        super();
        if (paymentList == null) {
            paymentList = new MutableLiveData<>();
        }
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")

                .create();

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(PaymentRequest.class);
    }

    public MutableLiveData<List<PaymentB>> getAllPayments(String jwt) {

        service.getPayments().enqueue(new Callback<List<PaymentB>>() {
            @Override
            public void onResponse(Call<List<PaymentB>> call, Response<List<PaymentB>> response) {
                if (response.isSuccessful()) {
                    paymentList.setValue(response.body());
                    Log.d("Payment Succesful",response.message());
                }
            }

            @Override
            public void onFailure(Call<List<PaymentB>> call, Throwable t) {
                Log.d("Payment Not Succesful",t.getCause().getMessage());
            }
        });
        return paymentList;
    }

    public MutableLiveData<List<Payment>> getBranchPayments(String jwt) {
        return null;
    }

    public MutableLiveData<List<Payment>> getMemberPayments(String jwt) {
        return null;
    }

}
