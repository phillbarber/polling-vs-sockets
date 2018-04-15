package com.github.phillbarber.job

import java.util.*

data class Job (val id: String = UUID.randomUUID().toString(), var complete: Boolean = false)