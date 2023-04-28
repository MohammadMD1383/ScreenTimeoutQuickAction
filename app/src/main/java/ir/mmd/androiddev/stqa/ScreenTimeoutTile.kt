package ir.mmd.androiddev.stqa

import android.graphics.drawable.Icon
import android.provider.Settings
import android.service.quicksettings.TileService

private sealed interface ScreenTimeout {
	val timeoutMillis: Int
	val icon: Int
	
	object FifteenSecond : ScreenTimeout {
		override val timeoutMillis get() = 1_000 * 15
		override val icon get() = R.drawable.fifteen_second_24
	}
	
	object ThirtySecond : ScreenTimeout {
		override val timeoutMillis get() = 1_000 * 30
		override val icon get() = R.drawable.thirty_second_24
	}
	
	object OneMinute : ScreenTimeout {
		override val timeoutMillis get() = 1_000 * 60
		override val icon get() = R.drawable.one_minute_24
	}
	
	object TwoMinute : ScreenTimeout {
		override val timeoutMillis get() = 1_000 * 60 * 2
		override val icon get() = R.drawable.two_minute_24
	}
	
	object FiveMinute : ScreenTimeout {
		override val timeoutMillis get() = 1_000 * 60 * 5
		override val icon get() = R.drawable.five_minute_24
	}
	
	object TenMinute : ScreenTimeout {
		override val timeoutMillis get() = 1_000 * 60 * 10
		override val icon get() = R.drawable.ten_minute_24
	}
	
	object FifteenMinute : ScreenTimeout {
		override val timeoutMillis get() = 1_000 * 60 * 15
		override val icon get() = R.drawable.fifteen_minute_24
	}
	
	object ThirtyMinute : ScreenTimeout {
		override val timeoutMillis get() = 1_000 * 60 * 30
		override val icon get() = R.drawable.thirty_minute_24
	}
	
	companion object {
		fun fromInt(i: Int): ScreenTimeout = when (i) {
			FifteenSecond.timeoutMillis -> FifteenSecond
			ThirtySecond.timeoutMillis -> ThirtySecond
			OneMinute.timeoutMillis -> OneMinute
			TwoMinute.timeoutMillis -> TwoMinute
			FiveMinute.timeoutMillis -> FiveMinute
			TenMinute.timeoutMillis -> TenMinute
			FifteenMinute.timeoutMillis -> FifteenMinute
			ThirtyMinute.timeoutMillis -> ThirtyMinute
			else -> throw IllegalArgumentException("Unknown Timeout")
		}
	}
}

class ScreenTimeoutTile : TileService() {
	private val currTimeout get() = Settings.System.getInt(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT)
	
	override fun onTileAdded() {
		updateTile(ScreenTimeout.fromInt(currTimeout).icon)
	}
	
	override fun onStartListening() {
		updateTile(ScreenTimeout.fromInt(currTimeout).icon)
	}
	
	override fun onClick() {
		val newTimeout = when (ScreenTimeout.fromInt(currTimeout)) {
			ScreenTimeout.FifteenSecond -> ScreenTimeout.TenMinute
			ScreenTimeout.TenMinute -> ScreenTimeout.FifteenSecond
			else -> ScreenTimeout.TenMinute
		}
		
		Settings.System.putInt(
			contentResolver,
			Settings.System.SCREEN_OFF_TIMEOUT,
			newTimeout.timeoutMillis
		)
		
		updateTile(newTimeout.icon)
	}
	
	private fun updateTile(icon: Int) {
		qsTile.icon = Icon.createWithResource(this, icon)
		qsTile.updateTile()
	}
}