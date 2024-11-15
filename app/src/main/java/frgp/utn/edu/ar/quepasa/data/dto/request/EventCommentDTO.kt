package frgp.utn.edu.ar.quepasa.data.dto.request

import frgp.utn.edu.ar.quepasa.data.model.Event

data class EventCommentDTO(
    val content: String,
    val file: Event
)