package io.kraftsman.dreieinigkeit

import io.kraftsman.dreieinigkeit.responds.TaskRespond
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.html.respondHtml
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.html.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        jackson {

        }
    }

    routing {

        get("/") {
            val tasks = mutableListOf<String>()
            for (i in 0..9) {
                tasks.add("Task $i")
            }

            call.respondHtml {
                head {
                    title { +"ToDo List" }
                }
                body {
                    h1 { +"ToDo List" }
                    p { +"a simple ToDo application" }
                    ul {
                        tasks.map {
                            li { +it }
                        }
                    }
                }
            }
        }

        get("/api/v1/tasks") {
            val task = TaskRespond("Task 1")
            call.respond(mapOf("task" to task))
        }

    }

}

