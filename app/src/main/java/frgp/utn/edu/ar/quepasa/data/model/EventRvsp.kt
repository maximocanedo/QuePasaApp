package frgp.utn.edu.ar.quepasa.data.model

/*
private int id;
    private Event event;
    private User user;
    private boolean confirmed = false;
 */
data class EventRvsp(
    var id: Int? = null,
    var event: Event? = null,
    var user: User? = null,
    var confirmed: Boolean = false
)