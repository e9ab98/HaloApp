import common.randomUUID
import kotlin.test.Test
import kotlin.test.assertTrue

class CommonTest {

    @Test
    fun testRandomUUID() {
        val uuid = randomUUID()
        println("randomUUID:$uuid")
        assertTrue(uuid.matches(Regex("^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$")))
    }
}
