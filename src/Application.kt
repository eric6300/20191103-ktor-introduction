package io.kraftsman.dreieinigkeit

import io.kraftsman.dreieinigkeit.databases.entities.Task
import io.kraftsman.dreieinigkeit.responds.TaskRespond
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.html.respondHtml
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.html.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        jackson {

        }
    }

    install(CORS) {
        host("localhost:4200")
        header(HttpHeaders.AccessControlAllowOrigin)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.ContentType)
        method(HttpMethod.Options)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Patch)
        method(HttpMethod.Delete)
    }

    Database.connect(
        url = "jdbc:mysql://127.0.0.1:8889/dreieinigkeit_local?useUnicode=true&characterEncoding=utf-8&useSSL=false",
        driver = "com.mysql.jdbc.Driver",
        user = "root",
        password = "root"
    )

    val tasks = transaction {
        Task.all().sortedByDescending { it.id }.map {
            TaskRespond(it.title, it.completed)
        }
    }

    routing {

        get("/") {
            call.respondHtml {
                head {
                    title { +"ToDo List" }
                }
                body {
                    h1 { +"ToDo List" }
                    p { +"a simple ToDo application" }
                    ul {
                        tasks.map {
                            li { +it.title }
                        }
                    }
                }
            }
        }

        get("/api/v1/tasks") {
            call.respond(mapOf("tasks" to tasks))
        }

    }

}

