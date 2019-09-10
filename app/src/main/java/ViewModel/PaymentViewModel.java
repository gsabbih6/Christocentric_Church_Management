package ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.dighisoft.christocentric.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import Models.Branch;
import Models.Payment;
import Models.PaymentB;
import Models.UserDBModel;
import Request.PaymentRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentViewModel extends ViewModel {

    private final PaymentRequest service;
    MutableLiveData<List<PaymentB>> paymentList;
    MutableLiveData<List<PaymentB>> paymentListB;

    public PaymentViewModel() {
        super();
        if (paymentList == null) {
            paymentList = new MutableLiveData<>();

        }
        if (paymentListB == null) {
            paymentListB = new MutableLiveData<>();

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
        final List<PaymentB> ls = new ArrayList<>();
        service.getPayments(
                UserDBModel.getUser().get(0).jwt
        ).enqueue(new Callback<List<PaymentB>>() {
            @Override
            public void onResponse(Call<List<PaymentB>> call, Response<List<PaymentB>> response) {
                if (response.isSuccessful()) {
//
//                    for(PaymentB pb:response.body()){
//                    for (Branch b : Utils.getUserBranch()) {
//                        for (Payment p : b.getPayments()) {
//                            for (PaymentB pb : response.body()) {
//                                if (pb.getId().equals(p.getId())) {
//                                    ls.add(pb);
//                                    break;
//                                }
//                            }
//                        }
//                    }
////                    }
                    paymentList.setValue(ls);
                    Log.d("Payment Succesful", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<PaymentB>> call, Throwable t) {
                Log.d("Payment Not Succesful", t.getCause().getMessage());
            }
        });
        return paymentList;
    }

    public MutableLiveData<List<PaymentB>> getBranchPayments(String jwt, final int branchId) {

        final List<PaymentB> ls = new ArrayList<>();

        service.getPayments(
                UserDBModel.getUser().get(0).jwt
        ).enqueue(new Callback<List<PaymentB>>() {
            @Override
            public void onResponse(Call<List<PaymentB>> call, Response<List<PaymentB>> response) {
                if (response.isSuccessful()) {

//                    for (PaymentB p : response.body()) {
//                        if (p.getBranch().getId().equals(branchId)) {
//                            ls.add(p);
//                        }
//                    }


                    paymentListB.setValue(ls);
                    Log.d("Payment Succesful", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<PaymentB>> call, Throwable t) {
                Log.d("Payment Not Succesful", t.getCause().getMessage());
            }
        });
        return paymentListB;
    }

    public MutableLiveData<List<Payment>> getMemberPayments(String jwt) {
        return null;
    }

}
