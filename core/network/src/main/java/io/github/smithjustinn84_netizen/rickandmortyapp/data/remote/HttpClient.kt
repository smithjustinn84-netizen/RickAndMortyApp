package io.github.smithjustinn84_netizen.rickandmortyapp.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.engine.okhttp.*

/**
 * Creates and configures an [HttpClient] for making network requests.
 *
 * The client is configured with:
 * - Content negotiation using JSON (Kotlinx Serialization)
 * - Logging (Android specific)
 * - Resources for type-safe routing
 * - HTTP request retry mechanism for server errors
 * - A default request URL pointing to the Rick and Morty API
 *
 * @return A configured [HttpClient] instance.
 */
fun httpClient(): HttpClient {
    return HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(
                Json {
                    // Avoid unnecessary JSON pretty printing for performance
                    prettyPrint = false
                    ignoreUnknownKeys = true
                    isLenient = true
                },
                contentType = ContentType.Any
            )
        }
        install(Logging) {
            logger = Logger.ANDROID
            // Reduce logging verbosity to decrease overhead and potential jank
            level = LogLevel.HEADERS
        }
        install(Resources)
        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 3)
            exponentialDelay()
        }
        defaultRequest {
            url("https://rickandmortyapi.com/api/")
        }
    }
}
