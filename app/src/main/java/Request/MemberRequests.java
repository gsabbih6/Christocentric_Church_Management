package Request;

import java.util.List;

import Models.Member;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MemberRequests {

    @GET("/members")
    Call<List<Member>> getMembers();

    @GET("/members/{id}")
    Call<Member> getMember(@Path("id") int id);

    @PUT("/members/{id}")
    Call<Member> updateMember(@Body Member user, @Path("id") String id);

    @POST("/members/")
    Call<Member> addNewMember(@Body Member user);

//    @POST("/members/")
//    Call<Member> addNewMember(@Body Member user);

}
