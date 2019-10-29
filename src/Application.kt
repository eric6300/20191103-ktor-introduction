package io.kraftsman.dreieinigkeit

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.html.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

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

    }

}

