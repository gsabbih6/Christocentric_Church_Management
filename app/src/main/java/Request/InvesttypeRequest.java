package Request;

import java.util.List;

import Models.Investment;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InvesttypeRequest {

    @GET("/investments")
    Call <List<Investment>> getInvestments(@Header("Authorization") String auth);
    @GET("/investments/{id}")
    Call <Investment> getInvestments(@Header("Authorization") String auth, @Path("id") int id);
}
