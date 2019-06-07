package Request;

import Models.Church;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChurchRequest {

    @GET("/churches/{id}")
    Call<Church> getInvestments(@Path("id") int id);
}
