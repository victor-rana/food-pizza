package co.app.food.common.test

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.Collections
import java.util.Arrays
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class LiveDataObserver<T> private constructor() : Observer<T?> {
    private val valueHistory: MutableList<T?> = ArrayList()
    private val onChanged: MutableList<Consumer<T?>> = ArrayList()
    private var valueLatch = CountDownLatch(1)
    override fun onChanged(value: T?) {
        valueHistory.add(value)
        valueLatch.countDown()
        for (consumer in onChanged) {
            consumer.accept(value)
        }
    }

    /**
     * Returns a last received value. Fails if no value was received yet.
     *
     * @return a last received value
     */
    fun value(): T? {
        assertHasValue()
        return valueHistory[valueHistory.size - 1]
    }

    /**
     * Returns a unmodifiable list of received values.
     *
     * @return a list of received values
     */
    fun valueHistory(): MutableList<T> {
        return Collections.unmodifiableList(valueHistory)
    }

    /**
     * Assert that this LiveDataObserver received at least one value.
     *
     * @return this
     */
    fun assertHasValue(): LiveDataObserver<T> {
        if (valueHistory.isEmpty()) {
            throw fail("Observer never received any value")
        }
        return this
    }

    /**
     * Assert that this LiveDataObserver never received any value.
     *
     * @return this
     */
    fun assertNoValue(): LiveDataObserver<T> {
        if (!valueHistory.isEmpty()) {
            throw fail("Expected no value, but received: " + value())
        }
        return this
    }

    /**
     * Assert that this LiveDataObserver received the specified number of values.
     *
     * @param expectedSize the expected number of received values
     * @return this
     */
    fun assertHistorySize(expectedSize: Int): LiveDataObserver<T> {
        val size = valueHistory.size
        if (size != expectedSize) {
            throw fail("History size differ; Expected: $expectedSize, Actual: $size")
        }
        return this
    }

    /**
     * Assert that this LiveDataObserver last received value is equal to
     * the given value.
     *
     * @param expected the value to expect being equal to last value, can be null
     * @return this
     */
    fun assertValue(expected: T): LiveDataObserver<T> {
        val value = value()
        if (notEquals(value, expected)) {
            throw fail("Expected: " + valueAndClass(expected) + ", Actual: " + valueAndClass(value))
        }
        return this
    }

    /**
     * Asserts that for this LiveDataObserver last received value
     * the provided predicate returns true.
     *
     * @param valuePredicate the predicate that receives the observed value
     * and should return true for the expected value.
     * @return this
     */
    fun assertValue(valuePredicate: Function<T?, Boolean?>): LiveDataObserver<T> {
        val value = value()
        if (!valuePredicate.apply(value)!!) {
            throw fail(
                "Value " + valueAndClass(value) + " does not match the predicate " +
                    valuePredicate.toString() + "."
            )
        }
        return this
    }

    /**
     * Asserts that the LiveDataObserver received only the specified values in the specified order.
     *
     * @param values the values expected
     * @return this
     */
    fun assertValueHistory(vararg values: T): LiveDataObserver<T> {
        val valueHistory = valueHistory()
        val size = valueHistory.size
        if (size != values.size) {
            throw fail(
                "Value count differs; expected: " + values.size + " " + Arrays.toString(values) +
                    " but was: " + size + " " + this.valueHistory
            )
        }
        for (valueIndex in 0 until size) {
            val historyItem = valueHistory[valueIndex]
            val expectedItem = values[valueIndex]
            if (notEquals(expectedItem, historyItem)) {
                throw fail(
                    "Values at position " + valueIndex + " differ; expected: " + valueAndClass(
                        expectedItem
                    ) + " but was: " + valueAndClass(historyItem)
                )
            }
        }
        return this
    }

    /**
     * Asserts that this LiveDataObserver did not receive any value for which
     * the provided predicate returns true.
     *
     * @param valuePredicate the predicate that receives the observed values
     * and should return true for the value not supposed to be received.
     * @return this
     */
    fun assertNever(valuePredicate: Function<T, Boolean>): LiveDataObserver<T> {
        val size = valueHistory.size
        for (valueIndex in 0 until size) {
            val value: T? = valueHistory[valueIndex]
            if (valuePredicate.apply(value)) {
                throw fail(
                    "Value at position " + valueIndex + " matches predicate " +
                        valuePredicate.toString() + ", which was not expected."
                )
            }
        }
        return this
    }

    /**
     * Allows assertion of some mapped value extracted from originally observed values.
     * History of observed values is retained.
     *
     *
     * This can became useful when you want to perform assertions on some complex structure and
     * you want to assert only on one field.
     *
     * @param mapper Function to map originally observed value.
     * @param <N>    Type of mapper.
     * @return LiveDataObserver for mapped value
     </N> */
    fun <N> map(mapper: Function<T?, N>): LiveDataObserver<N> {
        val newObserver: LiveDataObserver<N> = create()
        // We want the history match the current one
        for (value in valueHistory) {
            newObserver.onChanged(mapper.apply(value))
        }
        doOnChanged(Map(newObserver, mapper))
        return newObserver
    }

    /**
     * Adds a Consumer which will be triggered on each value change to allow assertion on the value.
     *
     * @param onChanged Consumer to call when new value is received
     * @return this
     */
    fun doOnChanged(onChanged: Consumer<T?>): LiveDataObserver<T> {
        this.onChanged.add(onChanged)
        return this
    }

    /**
     * Awaits until this LiveDataObserver has any value.
     *
     *
     * If this LiveDataObserver has already value then this method returns immediately.
     *
     * @return this
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    @Throws(InterruptedException::class)
    fun awaitValue(): LiveDataObserver<T> {
        valueLatch.await()
        return this
    }

    /**
     * Awaits the specified amount of time or until this LiveDataObserver has any value.
     *
     *
     * If this LiveDataObserver has already value then this method returns immediately.
     *
     * @return this
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    @Throws(InterruptedException::class)
    fun awaitValue(timeout: Long, timeUnit: TimeUnit?): LiveDataObserver<T> {
        valueLatch.await(timeout, timeUnit)
        return this
    }

    /**
     * Awaits until this LiveDataObserver receives next value.
     *
     *
     * If this LiveDataObserver has already value then it awaits for another one.
     *
     * @return this
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    @Throws(InterruptedException::class)
    fun awaitNextValue(): LiveDataObserver<T> {
        return withNewLatch().awaitValue()
    }

    /**
     * Awaits the specified amount of time or until this LiveDataObserver receives next value.
     *
     *
     * If this LiveDataObserver has already value then it awaits for another one.
     *
     * @return this
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    @Throws(InterruptedException::class)
    fun awaitNextValue(timeout: Long, timeUnit: TimeUnit?): LiveDataObserver<T> {
        return withNewLatch().awaitValue(timeout, timeUnit)
    }

    private fun withNewLatch(): LiveDataObserver<T> {
        valueLatch = CountDownLatch(1)
        return this
    }

    private fun fail(message: String): AssertionError {
        return AssertionError(message)
    }

    internal class Map<T, N>(
        private val newObserver: LiveDataObserver<N>,
        private val mapper: Function<T, N>
    ) : Consumer<T> {
        override fun accept(value: T) {
            newObserver.onChanged(mapper.apply(value))
        }
    }

    companion object {
        private fun notEquals(o1: Any?, o2: Any?): Boolean {
            return o1 !== o2 && (o1 == null || o1 != o2)
        }

        private fun valueAndClass(value: Any?): String {
            return if (value != null) {
                value.toString() + " (class: " + value.javaClass.simpleName + ")"
            } else "null"
        }

        fun <T> create(): LiveDataObserver<T> {
            return LiveDataObserver()
        }

        fun <T> test(liveData: LiveData<T>): LiveDataObserver<T> {
            val observer = LiveDataObserver<T>()
            liveData.observeForever(observer)
            return observer
        }
    }
}

interface Consumer<T> {
    fun accept(t: T)
}
