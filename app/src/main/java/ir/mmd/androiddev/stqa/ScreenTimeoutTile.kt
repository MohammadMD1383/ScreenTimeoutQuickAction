package ir.mmd.androiddev.stqa

import android.graphics.drawable.Icon
import android.provider.Settings
import android.service.quicksettings.TileService

class ScreenTimeoutTile : TileService() {
	private val currTimeout get() = ScreenTimeout.fromInt(Settings.System.getInt(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT))
	
	override fun onTileAdded() {
		updateTile(currTimeout.icon)
	}
	
	override fun onStartListening() {
		updateTile(currTimeout.icon)
	}
	
	override fun onClick() {
		val timeoutsPreferences = TimeoutsPreferences.load(this)
		val newTimeout = ScreenTimeout.next(currTimeout, timeoutsPreferences)
		
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