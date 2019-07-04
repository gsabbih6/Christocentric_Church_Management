package Request;

import java.util.List;

import Models.Branch;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface BranchRequest {

    @GET("/branches")
    Call<List<Branch>> getBranches(@Header("Authorization") String auth);
}
