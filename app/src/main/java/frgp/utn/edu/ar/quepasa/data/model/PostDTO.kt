package frgp.utn.edu.ar.quepasa.data.model

import java.sql.Timestamp

data class PostDTO(
    var id: Int? = null,
    var active: Boolean = true,
    var audience: String? = null,
    var description: String? = null,
    var tags: String? = null,
    var timestamp: Timestamp? = null,
    var title: String? = null,
    var neighbourhood: Int? = null,
    var op: Int? = null,
    var subtype: Int? = null,
    var postTypeDescription: String? = null,
    var postSubtypeDescription: String? = null,
    var totalVotes: Int? = null,
    var userVotes: Int? = null,
    var commentId: Int? = null,
    var pictureId: Int? = null,
    var pictureDescription: String? = null,
    var pictureUploadedAt: Timestamp? = null,
    var pictureMediaType: String? = null,
    var score: Int? = null
)







