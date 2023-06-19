package org.sosoburger.api;

import okhttp3.MultipartBody;
import org.sosoburger.dto.PictureDTO;
import org.sosoburger.dto.TagDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface PictureApi {
    @Multipart
    @POST("/picture")
    Call<PictureDTO> upload(@Part MultipartBody.Part picture);

    @GET("/picture")
    Call<List<PictureDTO>> findBySearch(@Query("search") String search);

    @PUT("/tag")
    Call<TagDTO> updateTag(@Body TagDTO tag);

    @DELETE("/tag")
    Call<Void> deleteTag(@Query("pictureid") Integer pictureId, @Query("tagid") Integer tagId);
}
