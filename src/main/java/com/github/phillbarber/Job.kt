package com.github.phillbarber

import java.util.*

data class Job (val id: String = UUID.randomUUID().toString(), val complete: Boolean)