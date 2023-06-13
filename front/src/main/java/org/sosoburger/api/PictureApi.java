package org.sosoburger.api;

import okhttp3.MultipartBody;
import org.sosoburger.dto.PictureDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import java.util.List;

public interface PictureApi {
    @Multipart
    @POST("/picture")
    Call<PictureDTO> upload(@Part MultipartBody.Part picture);

    @GET("/picture")
    Call<List<PictureDTO>> getAll();

}
