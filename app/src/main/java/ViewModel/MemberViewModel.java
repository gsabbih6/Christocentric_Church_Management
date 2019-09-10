package ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.dighisoft.christocentric.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import Models.Branch;
import Models.Member;
import Models.UserDBModel;
import Request.BranchRequest;
import Request.MemberRequests;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MemberViewModel extends ViewModel {
    private final MemberRequests service;
    // TODO: Implement the ViewModel

    public MemberViewModel() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")

                .create();

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(MemberRequests.class);
    }

    MutableLiveData<List<Member>> members;
    MutableLiveData<Member> selected = new MutableLiveData<>();

    public MutableLiveData<List<Member>> getMembers() {

        if (members == null) {
            members = new MutableLiveData<>();
            loadMembers();
        }

        return members;
    }

    private void loadMembers() {

        // make retrofit calls hear

        service.getMembers(
                UserDBModel.getUser().get(0).jwt
        ).enqueue(new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {
                if (response.isSuccessful()) {
                    members.setValue(response.body());

                    // passing response of member request to the members object
//
                    Log.d("test", response.body().toArray().toString());
                }
                Log.d("test", response.message());
            }

            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {
                Log.e("FIAL", t.getCause().getMessage());
            }
        });


    }

    public void setSelected(Member m) {
        if (selected != null) {
            this.selected.setValue(m);
        }

    }

    public LiveData<Member> getSelectedMember(int memberID) {

        service.getMember(
                UserDBModel.getUser().get(0).jwt,
 memberID
        ).enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                selected.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                selected.setValue(new Member());
            }
        });
        return selected;
    }

    public MutableLiveData<List<Member>> getMembersByBranch(final int branchID) {

//        if (members == null) {
        members = new MutableLiveData<>();
//            loadMembers();
//        }
        // make retrofit calls hear
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")

                .create();

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BranchRequest serviceb = retrofit.create(BranchRequest.class);
        serviceb.getBranches(
                UserDBModel.getUser().get(0).jwt
        ).enqueue(new Callback<List<Branch>>() {
            @Override
            public void onResponse(Call<List<Branch>> call, Response<List<Branch>> response) {
                List<Member> list = new ArrayList<>();

//                for (Branch br : response.body()
//                ) {
//
//                    if (br.getId().equals(branchID)) {
//                        list.addAll(br.getMembers());
//                    }
//
//
//                }
//                members.
                members.setValue(list);

            }

            @Override
            public void onFailure(Call<List<Branch>> call, Throwable t) {
                Log.e("Members not success msg", t.getCause().getMessage());
            }
        });
        return members;
    }
}
