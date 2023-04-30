package ir.mmd.androiddev.stqa

sealed interface ScreenTimeout {
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
	
	object Infinity : ScreenTimeout {
		override val timeoutMillis get() = Int.MAX_VALUE
		override val icon get() = R.drawable.infinity_24
	}
	
	companion object {
		private val cyclicList = CyclicList(
			listOf(
				FifteenSecond,
				ThirtySecond,
				OneMinute,
				TwoMinute,
				FiveMinute,
				TenMinute,
				FifteenMinute,
				ThirtyMinute,
				Infinity
			)
		)
		
		fun fromInt(i: Int): ScreenTimeout = when (i) {
			FifteenSecond.timeoutMillis -> FifteenSecond
			ThirtySecond.timeoutMillis -> ThirtySecond
			OneMinute.timeoutMillis -> OneMinute
			TwoMinute.timeoutMillis -> TwoMinute
			FiveMinute.timeoutMillis -> FiveMinute
			TenMinute.timeoutMillis -> TenMinute
			FifteenMinute.timeoutMillis -> FifteenMinute
			ThirtyMinute.timeoutMillis -> ThirtyMinute
			Infinity.timeoutMillis -> Infinity
			else -> throw IllegalArgumentException("Unknown Timeout")
		}
		
		fun next(timeout: ScreenTimeout, prefs: TimeoutsPreferences): ScreenTimeout {
			cyclicList.setIndexToElement(timeout)
			var next: ScreenTimeout
			
			while (true) {
				next = cyclicList.next()
				if (prefs.isEnabled(next) || next == timeout) {
					break
				}
			}
			
			return next
		}
	}
}