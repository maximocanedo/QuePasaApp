package frgp.utn.edu.ar.quepasa.presentation.activity.users

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.auth.Mail
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.domain.context.user.AuthenticationProvider
import frgp.utn.edu.ar.quepasa.presentation.activity.auth.AuthenticatedActivity
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.dataviewer.BasicUserInfoCard
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.dataviewer.MailsCard
import frgp.utn.edu.ar.quepasa.presentation.ui.components.users.profile.def.UserDisplayDesign
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.users.ProfileScreenViewModel
import java.sql.Timestamp

@AndroidEntryPoint
class ProfileScreen: AuthenticatedActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthenticationProvider {
                val viewModel: ProfileScreenViewModel = hiltViewModel()
                viewModel.updateUser()
                val user: User? by viewModel.user.collectAsState()
                if(user == null) return@AuthenticationProvider
                ProfileScreenContent(
                    user!!,
                    viewModel::onMailRegistrationRequest,
                    viewModel::onMailValidationRequest,
                    viewModel::onMailDeleteRequest
                )
            }
        }
    }
}

@Composable
fun ProfileScreenContent(
    user: User,
    onMailRegistration: suspend (String) -> Unit,
    onMailValidationRequest: suspend (Mail, String) -> Boolean,
    onMailDeleteRequest: suspend (Mail) -> Unit
) {
    val navController = rememberNavController()
    BaseComponent(navController, user, title = "Perfil", back = false) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            UserDisplayDesign(user, Modifier.fillMaxWidth())
            BasicUserInfoCard(user, Modifier.fillMaxWidth())
            MailsCard(
                user.email, Modifier.fillMaxWidth(),
                onMailRegistration, onMailDeleteRequest, onMailValidationRequest
            )
        }
    }
}

@Preview @Composable
fun ProfileScreenContentPreview() {
    ProfileScreenContent(User(
        id = 1,
        username = "root",
        name = "Root",
        phone = emptySet(),
        address = "",
        neighbourhood = null,
        picture = null,
        email = setOf(
            Mail(
                mail= "parravicini@gmail.com",
                user = null,
                requestedAt = Timestamp(System.currentTimeMillis() - 102410241024),
                verified = true,
                verifiedAt = Timestamp(System.currentTimeMillis())
            ),
            Mail(
                mail= "abc@gmail.com",
                user = null,
                requestedAt = Timestamp(System.currentTimeMillis() - 102410241024),
                verified = false,
                verifiedAt = Timestamp(System.currentTimeMillis())
            )
        ),
        role = Role.ADMIN,
        active = true
    ),
        { mailAddress -> },
        { mail, code -> true },
        { mail -> }
    )
}