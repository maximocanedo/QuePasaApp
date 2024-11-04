package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.readonly

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun ReadOnlyUserField(value: String) {
    TextField(
        value = value,
        onValueChange = {},
        enabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = TextFieldDefaults.colors(Color.LightGray, Color.Transparent, Color.Black),
        shape = RoundedCornerShape(0.dp),
        textStyle = TextStyle(color = Color.White),
    )
}