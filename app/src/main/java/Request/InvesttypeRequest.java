package Request;

import java.util.List;

import Models.Investment;
import retrofit2.Call;
import retrofit2.http.POST;

public interface InvesttypeRequest {

    @POST("/investments")
    Call <List<Investment>> getInvestments();
}
