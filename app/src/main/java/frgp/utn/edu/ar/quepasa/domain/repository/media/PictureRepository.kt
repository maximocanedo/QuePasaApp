package frgp.utn.edu.ar.quepasa.domain.repository.media

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import frgp.utn.edu.ar.quepasa.data.source.remote.media.PictureService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class PictureRepository @Inject constructor(
    private val pictureService: PictureService
) {
    fun viewPicture(pictureId: UUID, onComplete: (Bitmap?) -> Unit) {
        pictureService.viewPicture(pictureId).enqueue(object : Callback<ResponseBody> {

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onComplete(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (!response.isSuccessful || response.body() == null || response.errorBody() != null) {
                    onComplete(null)
                    return
                }
                val bytes = response.body()!!.bytes()
                onComplete(BitmapFactory.decodeByteArray(bytes, 0, bytes.size))
            }
        })
    }
}