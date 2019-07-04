package com.dighisoft.christocentric;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Models.Branch;
import Models.BranchDatabase;
import Models.Investment;
import Models.InvestmentDatabase;
import Models.Member;
import Models.MemberDatabase;
import Models.PaymentB;
import Models.PaymentDatabase;
import Models.UserDBModel;
import Models.UserDTO;
import Request.BranchRequest;
import Request.InvesttypeRequest;
import Request.MemberRequests;
import Request.PaymentRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {

//        public static String BASE_URL = "http://10.0.2.2:1337";
    public static String BASE_URL = "https://aqueous-everglades-86605.herokuapp.com";
    public static String LOGIN_TOKEN;

    public static void startActivty(Activity ctx, Class clx) {

        Intent i = new Intent(ctx, clx);
        ctx.startActivity(i);

    }

    public static List<BranchDatabase> getUserBranch() {
        final ArrayList<Branch> br = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BranchRequest service = retrofit.create(BranchRequest.class);

//        service.getBranches().enqueue(new Callback<List<Branch>>() {
//            @Override
//            public void onResponse(Call<List<Branch>> call, Response<List<Branch>> response) {
//                for (Branch bg : response.body()) {
//
//                    for (UserDTO user : bg.getUserDTO()
//                    ) {
//                        if (user.getId().equals(UserDBModel.getUser().get(0).userid)) {
//                            br.add(bg);
//
//                            Log.d("geting branched", bg.getName());
//                        }
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Branch>> call, Throwable t) {
//                Log.d("Branches failed", t.getCause().getMessage());
//            }
//        });
//        return br;
//        try {
//
//            for (Branch bg : service.getBranches().execute().body()) {
////
//                for (UserDTO user : bg.getUserDTO()
//                ) {
//                    if (user.getId().equals(UserDBModel.getUser().get(0).userid)) {
//                        br.add(bg);
//
//                        Log.d("geting branched", bg.getName());
//                    }
//                }
//            }
//            return br;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        for (BranchDatabase bd : BranchDatabase.getUserBranches(UserDBModel.getUser().get(0).userid)) {
//            Branch b = new Branch();
//            b.setName(bd.getName());
//            b.setId(Math.toIntExact(bd.getId()));
//            br.add(b);
//        }


        return BranchDatabase.getUserBranches(UserDBModel.getUser().get(0).userid);

    }

    public static Investment getInvestment(int id) {
        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//        final Investment[] in = {new Investment()};
//
        InvesttypeRequest service = retrofit.create(InvesttypeRequest.class);
//
//        service.getInvestments(id).enqueue(new Callback<Investment>() {
//            @Override
//            public void onResponse(Call<Investment> call, Response<Investment> response) {
//                in[0] = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<Investment> call, Throwable t) {
//
//            }
//        });
//        return in[0];
//    }
        try {
            return service.getInvestments(UserDBModel.getUser().get(0).jwt,id).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateModels(String jwt) {
        // update Payment
        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//        final Investment[] in = {new Investment()};
//
        PaymentRequest service = retrofit.create(PaymentRequest.class);


        service.getPayments(UserDBModel.getUser().get(0).jwt).enqueue(new Callback<List<PaymentB>>() {
            @Override
            public void onResponse(Call<List<PaymentB>> call, Response<List<PaymentB>> response) {

                if (response.body() != null)
                    for (PaymentB p : response.body()) {

                        PaymentDatabase pdb = new PaymentDatabase();

                        pdb.setAmount(p.getAmount());
                        pdb.setCreatedAt(p.getCreatedAt());
                        pdb.setId(Long.valueOf(p.getId()));
                        pdb.setBranch(Long.valueOf(p.getBranch().getId()));
                        pdb.setInvestmentid(Long.valueOf(p.getInvestment().getId()));
                        pdb.setUpdatedAt(p.getUpdatedAt());
//                    pdb.setMemberId(Long.valueOf(p.getMember().getId()));
                        pdb.save();
                    }

                Log.e("loaded Payment", "True");
            }

            @Override
            public void onFailure(Call<List<PaymentB>> call, Throwable t) {

                Log.e("Failed to load Payment", t.getCause().getMessage());

            }
        });


        // save Members
//
        MemberRequests serviceMember = retrofit.create(MemberRequests.class);


        serviceMember.getMembers(UserDBModel.getUser().get(0).jwt).enqueue(new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {

//                Log.e("BranchIIIS",response.body().toString());
                if (response.body() != null)
                    for (Member m : response.body()) {

                        MemberDatabase mbd = new MemberDatabase();
                        mbd.setAddress(m.getAddress());
                        mbd.setBranchId(Long.valueOf(m.getBranch().getId()));
                        mbd.setCreatedAt(m.getCreatedAt());
                        mbd.setUpdatedAt(m.getUpdatedAt());
                        mbd.setDob(m.getDob());
                        mbd.setDom(m.getDom());
                        mbd.setEmail(m.getEmail());
                        mbd.setFirstname(m.getFirstname());
                        mbd.setId(Long.valueOf(m.getId()));
                        mbd.setLastname(m.getLastname());
                        mbd.setMaritalStatus(m.getMaritalStatus());
                        mbd.setOccupation(m.getOccupation());
                        mbd.setOthername(m.getOthername());
//

//                    StringUtils.

                        try {
                            Log.e("BranchIS",
                                    m.getPhoto()
                                            .getUrl()
                                            .trim() == null ? "" :
                                            m.getPhoto().getUrl().trim());
                            mbd.setPhotoUrl(m.getPhoto().getUrl());

                        } catch (NullPointerException e) {

                            Log.e("BranchIS", e.getLocalizedMessage());
                            mbd.setPhotoUrl("");

                        }
//                    finally {
//                        mbd.setPhotoUrl("");
//                        Log.e("Finally", "finale");
//                    }

                        mbd.setSchool(m.getSchool());
                        mbd.setSpecialNotes(m.getSpecialNotes());
                        mbd.setStatus(m.getStatus());
                        mbd.setTelephone(m.getTelephone());


                        //save

                        mbd.save();
                    }

            }

            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {

                Log.e("Failed to branch", t.getCause().getMessage());
            }
        });

        BranchRequest serviceBranch = retrofit.create(BranchRequest.class);

        serviceBranch.getBranches(UserDBModel.getUser().get(0).jwt).enqueue(new Callback<List<Branch>>() {
            @Override
            public void onResponse(Call<List<Branch>> call, Response<List<Branch>> response) {
//                ReActiveAndroid.;
                if (response.body() != null)
                    for (Branch b : response.body()) {


                        for (UserDTO u : b.getUserDTO()) {
                            if (u.getId().equals(UserDBModel.getUser().get(0).userid.intValue())) {

                                BranchDatabase bdb = new BranchDatabase();
                                bdb.setAddress(b.getAddress());
                                bdb.setChurchId(String.valueOf(b.getChurch().getId()));
                                bdb.setCreatedAt(b.getCreatedAt());
                                bdb.setUpdatedAt(b.getUpdatedAt());
                                bdb.setName(b.getName());
                                bdb.setTelephone(b.getTelephone());
                                bdb.setLogoUrl(b.getLogo().getUrl());
                                bdb.setId(Long.valueOf(b.getId()));
                                Log.e("IDbra", String.valueOf(Long.valueOf(b.getId())));
                                bdb.save();
                                break;
                            } else {
                                Log.e("Unreac", String.valueOf(Long.valueOf(b.getId())));
                            }
                        }


                    }

            }

            @Override
            public void onFailure(Call<List<Branch>> call, Throwable t) {

                Log.e("Failed to load Brance", t.getCause().getMessage());
            }
        });


        InvesttypeRequest serviceInvest = retrofit.create(InvesttypeRequest.class);
        serviceInvest.getInvestments(UserDBModel.getUser().get(0).jwt).enqueue(new Callback<List<Investment>>() {
            @Override
            public void onResponse(Call<List<Investment>> call,
                                   Response<List<Investment>> response) {

                if (response.body() != null)
                    for
                    (Investment inv : response.body()) {
                        InvestmentDatabase idb = new InvestmentDatabase();
                        idb.setDescription(inv.getDescription());
                        idb.setId(Long.valueOf(inv.getId()));
                        idb.setName(inv.getName());


                        //save

                        idb.save();
                    }

            }

            @Override
            public void onFailure(Call<List<Investment>> call, Throwable t) {
                Log.e("Failed to load Inves", t.getCause().getMessage());
            }
        });
    }

    public static boolean isEmpty(View... vs) {

        for (View v : vs) {
            if (v instanceof EditText) {
                if (((EditText) v).getText().toString().matches("")) {
                    return true;
                }
            }
        }


        return false;
    }

    public static MemberRequests getMemberRequest() {
        // update Payment
        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//        final Investment[] in = {new Investment()};
//
        return retrofit.create(MemberRequests.class);
    }

    public static void reset(EditText... txts) {
        for (EditText t : txts) {
            t.setText("");
        }
    }
}