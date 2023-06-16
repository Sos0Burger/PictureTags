package org.sosoburger.api;

import okhttp3.MultipartBody;
import org.sosoburger.dto.PictureDTO;
import org.sosoburger.dto.TagDTO;
import retrofit2.Call;

import java.util.List;

public class PictureApiImpl implements PictureApi{
    private static final PictureApi pictureApi = RetrofitClient.getInstance().create(PictureApi.class);
    @Override
    public Call<PictureDTO> upload(MultipartBody.Part picture) {
        return pictureApi.upload(picture);
    }

    @Override
    public Call<List<PictureDTO>> findBySearch(String search) {
        return pictureApi.findBySearch(search);
    }

    @Override
    public Call<PictureDTO> updatePicture(Integer id, List<TagDTO> tagDTOs) {
        return pictureApi.updatePicture(id, tagDTOs);
    }
}
