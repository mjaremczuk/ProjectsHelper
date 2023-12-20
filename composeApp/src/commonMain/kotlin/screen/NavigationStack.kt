package screen

import androidx.compose.runtime.mutableStateListOf

class NavigationStack<T>(val initial: T) {
    private val stack = mutableStateListOf(initial)
    fun push(t: T) {
        stack.add(t)
    }

    fun back() {
        if (stack.size > 1) {
            stack.removeLast()
        }
    }

    fun lastWithIndex() = stack.withIndex().last()
    fun clear() {
        stack.clear()
        stack.add(initial)
//        stack = mutableStateListOf(initial)
    }
}