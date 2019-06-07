package Request;

import java.util.List;

import Models.Payment;
import Models.PaymentB;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PaymentRequest {

    @GET("/payments")
    Call<List<PaymentB>> getPayments();

    @GET("/payments/{id}")
    Call<PaymentB> getPayment(@Path("id") int id);

    @PUT("/payments/{id}")
    Call<Payment> updatePayment(@Body Payment user, @Path("id") String id);

    @POST("/payments/")
    Call<Payment> addNewPayment(@Body Payment user);
}
