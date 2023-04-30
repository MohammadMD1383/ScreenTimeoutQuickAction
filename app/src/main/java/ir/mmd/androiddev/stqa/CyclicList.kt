package ir.mmd.androiddev.stqa

class CyclicList<E>(private val items: List<E>) {
	init {
		if (items.isEmpty()) {
			throw IllegalArgumentException("Given list mustn't be empty")
		}
	}
	
	private var i = 0
	
	fun setIndexToElement(element: E) {
		val index = items.indexOf(element)
		
		if (index == -1) {
			throw IllegalArgumentException("element doesn't exist in the list")
		}
		
		i = index
	}
	
	fun next(): E {
		if (++i > items.lastIndex) {
			i = 0
		}
		
		return items[i]
	}
}
