package com.github.phillbarber.job


import java.time.Instant
import java.util.*

data class Job (val id: String = UUID.randomUUID().toString(),
                val createTime: Long = Instant.now().toEpochMilli(),
                var complete: Boolean = false)