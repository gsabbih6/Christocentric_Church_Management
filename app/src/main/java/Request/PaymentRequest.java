package Request;

import java.util.List;

import Models.Payment;
import Models.PaymentB;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PaymentRequest {

    @GET("/payments")
    Call<List<PaymentB>> getPayments(
            @Header("Authorization") String auth
    );

    @GET("/payments/{id}")
    Call<PaymentB> getPayment(@Path("id") int id
            , @Header("Authorization") String auth
    );

    @PUT("/payments/{id}")
    Call<Payment> updatePayment(
            @Header("Authorization") String auth,
            @Body Payment user, @Path("id") String id);

    @POST("/payments/")
    Call<Payment> addNewPayment(
            @Header("Authorization") String auth,
            @Body Payment user);
}
