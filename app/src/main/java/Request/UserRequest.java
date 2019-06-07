package Request;

import Models.User;
import Models.UserDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserRequest {
    @FormUrlEncoded
    @POST("/auth/local")
    Call<User> loginUser(@Field("identifier") String identifier, @Field("password") String password);

    @POST("/auth/local")
    Call<UserDTO> updateUser(@Body UserDTO user);
}
