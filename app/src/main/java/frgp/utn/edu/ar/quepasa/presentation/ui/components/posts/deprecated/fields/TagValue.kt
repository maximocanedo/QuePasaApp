package frgp.utn.edu.ar.quepasa.presentation.ui.components.posts.deprecated.fields

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.posts.PostViewModel

@Composable
fun TagValue(
    modifier: Modifier,
    value: String,
    viewModel: PostViewModel
) {
    Box(
        modifier = Modifier
            .border(
                BorderStroke(2.dp, color = Color.White),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(4.dp)
    ) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Text(text = value, color = Color.White)

            IconButton(onClick = {
                viewModel.removeTag(value)
            } ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Tag Clear",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun TagValuePreview() {
    val viewModel: PostViewModel = hiltViewModel()
    val tag by remember { mutableStateOf("") }
    TagValue(
        modifier = Modifier,
        value = tag,
        viewModel = viewModel
    )
}