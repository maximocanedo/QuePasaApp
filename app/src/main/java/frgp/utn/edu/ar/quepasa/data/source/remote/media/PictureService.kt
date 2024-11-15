package frgp.utn.edu.ar.quepasa.data.source.remote.media

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.UUID

interface PictureService {
    @GET("pictures/{pictureId}/view")
    fun viewPicture(@Path("pictureId") pictureId: UUID): Call<ResponseBody>
}