import com.ivo.ganev.dp_state.DiscretePartitionedState
import org.junit.Before
import org.junit.Test
import java.lang.IllegalStateException
import java.lang.IndexOutOfBoundsException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlin.test.fail

class DiscretePartitionedStateTest {
    private val a = "a"
    private val ab = "ab"
    private val abc = "abc"
    private val b = "b"
    private val ba = "ba"
    private val bac = "bac"
    private val c = "c"
    private val ca = "ca"
    private val cac = "cac"

    private lateinit var path: DiscretePartitionedState<String>

    @Before
    fun init() {
        path = DiscretePartitionedState()
    }

    @Test
    fun `test is empty`() {
        with(path) {
            println(this)
            assertTrue(isEmpty)
        }
    }

    @Test
    fun `test exceptions with two single rows`() {
        with(path) {
            add(a)
            push(b)
            try {
                left()
                fail()
            } catch (ex: IndexOutOfBoundsException) {
            }
            try {
                right()
                fail()
            } catch (ex: IndexOutOfBoundsException) {
            }
        }
    }


    @Test
    fun `test exceptions with two single rows and two elements`() {
        with(path) {
            try {
                current()
                fail()
            } catch (ex: IndexOutOfBoundsException) {
            }
            add(a).add(ab)
            push(b).add(ba)
            println(this)
            assertEquals(b, left())
            assertEquals(a, left())
        }
    }

    @Test
    fun `test empty rows`() {
        with(path) {
            try {
                left()
                fail()
            } catch (ex: IndexOutOfBoundsException) {
            }
            try {
                right()
                fail()
            } catch (ex: IndexOutOfBoundsException) {
            }
        }
    }

    @Test
    fun `test traversal with two rows with three elements`() {
        with(path) {
            add(a)

            assertEquals(a, current())
            add(ab)
            assertEquals(ab, current())

            add(abc)
            push(b)
            assertEquals(b, current())

            add(ba)
            add(bac)

            try {
                right()
                fail()
            } catch (ex: IndexOutOfBoundsException) {
            }

            assertEquals(bac, current())
            assertEquals(ba, left())

            assertEquals(b, left())
            assertEquals(ab, left())
            assertEquals(a, left())
            print(this)
            try {
                left()
                fail()
            } catch (ex: IndexOutOfBoundsException) {
            }
        }
    }

    @Test
    fun `test traversal exhaustive`() {
        with(path) {
            try {
                left()
                fail()
            } catch (ex: IndexOutOfBoundsException) {
            }
            add(a)
            push(b)
            push(c).add(ca)
            push(a).add(ab).add(abc)
            push(c).add(ca).add(cac)
            push(ba).add(ca)
            push(b)
            push(c)
            push(ab).add(abc)
            push(b).add(ba)
            push(a)
            push(b)

            assertEquals(b, left())
            assertEquals(ab, peekLeft())
            assertEquals(ba, peekRight())

            assertEquals(ab, left())
            assertEquals(ba, peekLeft())
            assertEquals(abc, peekRight())

            assertEquals(ba, left())
            assertEquals(ca, peekLeft())
            assertEquals(ca, peekRight())

            assertEquals(ca, left())
            assertEquals(c, left())
            assertEquals(ab, left())
            assertEquals(a, left())
            assertEquals(c, left())

            try {
                peekLeft()
                fail()
            } catch (ex: IndexOutOfBoundsException) {
            }

            assertEquals(ca, peekRight())

            try {
                left()
                fail()
            } catch (ex: IndexOutOfBoundsException) {
            }

            println(this)
            assertEquals(ca, right())
            assertEquals(ab, right())
            assertEquals(abc, right())
            assertEquals(ca, right())
            assertEquals(cac, right())
            assertEquals(ca, right())
            assertEquals(abc, right())
            assertEquals(ba, right())
            println(this)
            try {
                right()
                fail()
            } catch (ex: IndexOutOfBoundsException) {
            }
        }
    }
}
