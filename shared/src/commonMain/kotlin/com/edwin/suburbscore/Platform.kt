package com.edwin.suburbscore

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform