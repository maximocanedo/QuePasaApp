package frgp.utn.edu.ar.quepasa.presentation.ui.components.users.screen

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import frgp.utn.edu.ar.quepasa.R
import frgp.utn.edu.ar.quepasa.data.model.User
import frgp.utn.edu.ar.quepasa.data.model.enums.Role
import frgp.utn.edu.ar.quepasa.domain.context.user.AuthenticationProvider
import frgp.utn.edu.ar.quepasa.domain.context.user.LocalAuth
import frgp.utn.edu.ar.quepasa.presentation.ui.components.BaseComponent
import frgp.utn.edu.ar.quepasa.presentation.ui.components.basic.ConfSwitch
import frgp.utn.edu.ar.quepasa.presentation.viewmodel.auth.TotpSettingsScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun TotpSettingsScreen(
    navController: NavHostController,
    viewModel: TotpSettingsScreenViewModel = hiltViewModel()
) {
    val auth by LocalAuth.current.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    if(auth.isEmpty()) return
    val details by viewModel.details.collectAsState()
    LaunchedEffect(0) {
        viewModel.reload()
    }
    BaseComponent(
        navController = navController,
        user = auth.user,
        title = "Autenticación mediante TOTP",
        back = true,
        backRoute = navController.backQueue
        .takeIf { it.size > 1 }
            ?.let { it[it.size - 2] }
            ?.destination?.route ?: "advancedProfileSettings"
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            ConfSwitch(
                modifier = Modifier,
                label = "Usar TOTP para iniciar sesión",
                checked = details.enabled,
                onCheckedChange = {
                    scope.launch { if (!it) viewModel.disable() else viewModel.enable() }
                },
                loading = loading
            )
            if (details.enabled) ListItem(
                headlineContent = { Text("Configurar en este dispositivo ") },
                leadingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.open_in_phone),
                        contentDescription = "Abrir en app de autenticación"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(details.url)
                        }
                        if (intent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(intent)
                        } else {
                            Toast
                                .makeText(
                                    context,
                                    "No hay aplicaciones compatibles instaladas",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    }
            )
            if (details.enabled) ListItem(
                headlineContent = { Text("Copiar enlace") },
                supportingContent = { Text("Para configurarlo manualmente") },
                leadingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.content_copy),
                        contentDescription = "Copiar enlace"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val clipboard =
                            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = android.content.ClipData.newPlainText("url", details.url)
                        clipboard.setPrimaryClip(clip)
                        Toast
                            .makeText(context, "URL copiada al portapapeles", Toast.LENGTH_SHORT)
                            .show()
                    }
            )
        }
    }
}
