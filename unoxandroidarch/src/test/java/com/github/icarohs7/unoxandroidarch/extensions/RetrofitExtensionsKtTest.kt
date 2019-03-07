package com.github.icarohs7.unoxandroidarch.extensions

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import retrofit2.http.GET
import se.lovef.assert.v1.shouldEqual

class RetrofitExtensionsKtTest {
    interface TestApiService {
        @GET("/message")
        suspend fun getMessage(): TestClass

        @GET("/numbers")
        suspend fun getNumbers(): List<Int>
    }

    @Serializable
    data class TestClass(val message: String)

    @Test
    fun `should create and execute retrofit request`() {
        val server = MockWebServer()
        server.enqueue(MockResponse().setBody("""{"message":"Omai wa mou shindeiru!"}"""))
        server.enqueue(MockResponse().setBody("[10,20,30,40]"))
        server.start()

        val retrofit = createRetrofitService<TestApiService>(server.url("/").toString())

        val response1 = runBlocking { retrofit.getMessage() }
        response1 shouldEqual TestClass("Omai wa mou shindeiru!")

        val response2 = runBlocking { retrofit.getNumbers() }
        response2 shouldEqual listOf(10, 20, 30, 40)
    }
}