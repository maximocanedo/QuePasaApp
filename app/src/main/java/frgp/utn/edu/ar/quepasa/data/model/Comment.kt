package frgp.utn.edu.ar.quepasa.data.model

import java.sql.Timestamp

/*
private UUID id;
    private String content;
    private User author;
    private Timestamp timestamp;
    private boolean active;
 */

data class Comment(
    var id: Int,
    var content: String,
    var author: User? = null,
    var timestamp: Timestamp,
    var active: Boolean = true
)
