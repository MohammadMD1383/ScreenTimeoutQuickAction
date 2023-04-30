package ir.mmd.androiddev.stqa

import android.content.Context
import androidx.compose.runtime.mutableStateOf

class TimeoutsPreferences(
	fifteenSecond: Boolean = false,
	thirtySecond: Boolean = false,
	oneMinute: Boolean = false,
	twoMinute: Boolean = false,
	fiveMinute: Boolean = false,
	tenMinute: Boolean = false,
	fifteenMinute: Boolean = false,
	thirtyMinute: Boolean = false,
	infinity: Boolean = false
) {
	val fifteenSecond = mutableStateOf(fifteenSecond)
	val thirtySecond = mutableStateOf(thirtySecond)
	val oneMinute = mutableStateOf(oneMinute)
	val twoMinute = mutableStateOf(twoMinute)
	val fiveMinute = mutableStateOf(fiveMinute)
	val tenMinute = mutableStateOf(tenMinute)
	val fifteenMinute = mutableStateOf(fifteenMinute)
	val thirtyMinute = mutableStateOf(thirtyMinute)
	val infinity = mutableStateOf(infinity)
	
	fun toLong(): Long {
		return (
			(fifteenSecond.value.toLong() shl 0) or
				(thirtySecond.value.toLong() shl 1) or
				(oneMinute.value.toLong() shl 2) or
				(twoMinute.value.toLong() shl 3) or
				(fiveMinute.value.toLong() shl 4) or
				(tenMinute.value.toLong() shl 5) or
				(fifteenMinute.value.toLong() shl 6) or
				(thirtyMinute.value.toLong() shl 7) or
				(infinity.value.toLong() shl 8)
			)
	}
	
	fun isEnabled(timeout: ScreenTimeout) = when (timeout) {
		ScreenTimeout.FifteenSecond -> fifteenSecond.value
		ScreenTimeout.ThirtySecond -> thirtySecond.value
		ScreenTimeout.OneMinute -> oneMinute.value
		ScreenTimeout.TwoMinute -> twoMinute.value
		ScreenTimeout.FiveMinute -> fiveMinute.value
		ScreenTimeout.TenMinute -> tenMinute.value
		ScreenTimeout.FifteenMinute -> fifteenMinute.value
		ScreenTimeout.ThirtyMinute -> thirtyMinute.value
		ScreenTimeout.Infinity -> infinity.value
	}
	
	companion object {
		private fun fromLong(l: Long): TimeoutsPreferences {
			return TimeoutsPreferences(
				fifteenSecond = (l shr 0 and 1).toBoolean(),
				thirtySecond = (l shr 1 and 1).toBoolean(),
				oneMinute = (l shr 2 and 1).toBoolean(),
				twoMinute = (l shr 3 and 1).toBoolean(),
				fiveMinute = (l shr 4 and 1).toBoolean(),
				tenMinute = (l shr 5 and 1).toBoolean(),
				fifteenMinute = (l shr 6 and 1).toBoolean(),
				thirtyMinute = (l shr 7 and 1).toBoolean(),
				infinity = (l shr 8 and 1).toBoolean()
			)
		}
		
		fun load(context: Context): TimeoutsPreferences {
			return fromLong(
				context.getSharedPreferences(SHARED_PREFS_ID, Context.MODE_PRIVATE).getLong(TIMEOUTS_PREFS_KEY, TIMEOUTS_DEFAULT_VALUE)
			)
		}
	}
}

private fun Boolean.toLong() = if (this) 1L else 0L
private fun Long.toBoolean() = this != 0L
