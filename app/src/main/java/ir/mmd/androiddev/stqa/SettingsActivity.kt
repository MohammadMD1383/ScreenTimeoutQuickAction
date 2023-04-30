package ir.mmd.androiddev.stqa

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.mmd.androiddev.stqa.ui.theme.ScreenTimeoutQuickActionTheme

class SettingsActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			ScreenTimeoutQuickActionTheme {
				MainComponent()
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun MainComponent() {
	val editMode = LocalView.current.isInEditMode
	val context = LocalContext.current
	val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_ID, Context.MODE_PRIVATE)
	val timeoutsPreferences = remember {
		if (!editMode) {
			TimeoutsPreferences.load(context)
		} else {
			TimeoutsPreferences(
				fifteenSecond = true,
				tenMinute = true
			)
		}
	}
	
	Scaffold { contentPadding ->
		Column(
			modifier = Modifier
				.padding(contentPadding)
				.padding(8.dp)
				.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(text = "Cycle through these values:")
			
			Spacer(modifier = Modifier.height(16.dp))
			
			FlowRow(
				horizontalArrangement = Arrangement.Center,
			) {
				TimeoutIcon(
					icon = R.drawable.fifteen_second_24,
					tag = "15s",
					enabled = timeoutsPreferences.fifteenSecond,
					modifier = Modifier.padding(4.dp),
				)
				
				TimeoutIcon(
					icon = R.drawable.thirty_second_24,
					tag = "30s",
					enabled = timeoutsPreferences.thirtySecond,
					modifier = Modifier.padding(4.dp)
				)
				
				TimeoutIcon(
					icon = R.drawable.one_minute_24,
					tag = "1m",
					enabled = timeoutsPreferences.oneMinute,
					modifier = Modifier.padding(4.dp)
				)
				
				TimeoutIcon(
					icon = R.drawable.two_minute_24,
					tag = "2m",
					enabled = timeoutsPreferences.twoMinute,
					modifier = Modifier.padding(4.dp)
				)
				
				TimeoutIcon(
					icon = R.drawable.five_minute_24,
					tag = "5m",
					enabled = timeoutsPreferences.fiveMinute,
					modifier = Modifier.padding(4.dp)
				)
				
				TimeoutIcon(
					icon = R.drawable.ten_minute_24,
					tag = "10m",
					enabled = timeoutsPreferences.tenMinute,
					modifier = Modifier.padding(4.dp)
				)
				
				TimeoutIcon(
					icon = R.drawable.fifteen_minute_24,
					tag = "15m",
					enabled = timeoutsPreferences.fifteenMinute,
					modifier = Modifier.padding(4.dp)
				)
				
				TimeoutIcon(
					icon = R.drawable.thirty_minute_24,
					tag = "30m",
					enabled = timeoutsPreferences.thirtyMinute,
					modifier = Modifier.padding(4.dp)
				)
				
				TimeoutIcon(
					icon = R.drawable.infinity_24,
					tag = "infinity",
					enabled = timeoutsPreferences.infinity,
					modifier = Modifier.padding(4.dp)
				)
			}
			
			Spacer(modifier = Modifier.weight(1F))
			
			Button(
				modifier = Modifier.fillMaxWidth(),
				onClick = {
					sharedPreferences.edit().putLong(TIMEOUTS_PREFS_KEY, timeoutsPreferences.toLong()).apply()
					(context as Activity).finish()
				}
			) {
				Text(text = "Save")
			}
		}
	}
}

@Composable
private fun TimeoutIcon(icon: Int, tag: String, enabled: MutableState<Boolean>, modifier: Modifier = Modifier) {
	val tint by animateColorAsState(if (enabled.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
	
	Icon(
		painter = painterResource(icon),
		tint = tint,
		contentDescription = tag,
		modifier = Modifier
			.then(modifier)
			.border(2.dp, tint, CircleShape)
			.padding(8.dp)
			.size(32.dp)
			.clickable(
				interactionSource = remember { MutableInteractionSource() },
				indication = rememberRipple(false),
				onClick = { enabled.value = !enabled.value }
			)
	)
}

@Preview
@Composable
private fun LightPreview() {
	ScreenTimeoutQuickActionTheme(darkTheme = false) {
		MainComponent()
	}
}

@Preview
@Composable
private fun DarkPreview() {
	ScreenTimeoutQuickActionTheme(darkTheme = true) {
		MainComponent()
	}
}
