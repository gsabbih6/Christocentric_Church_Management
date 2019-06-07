package com.dighisoft.christocentric;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Models.Branch;
import Models.UserDBModel;
import Models.UserDTO;
import Request.BranchRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class Utils {

    public static String BASE_URL = "http://10.0.2.2:1337";
    public static String LOGIN_TOKEN;

    public static void startActivty(Activity ctx, Class clx) {

        Intent i = new Intent(ctx, clx);
        ctx.startActivity(i);

    }

    public static ArrayList<Branch> getUserBranch() {
        final ArrayList<Branch> br = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BranchRequest service = retrofit.create(BranchRequest.class);

        service.getBranches().enqueue(new Callback<List<Branch>>() {
            @Override
            public void onResponse(Call<List<Branch>> call, Response<List<Branch>> response) {
                for (Branch bg : response.body()) {

                    for (UserDTO user : bg.getUserDTO()
                    ) {
                        if (user.getId().equals(UserDBModel.getUser().get(0).userid)) {
                            br.add(bg);

                            Log.d("geting branched",bg.getName());
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Branch>> call, Throwable t) {
                Log.d("Branches failed", t.getCause().getMessage());
            }
        });
        return br;
    }

    public static List<Branch> getInvestment() {
        return null;
    }
}
