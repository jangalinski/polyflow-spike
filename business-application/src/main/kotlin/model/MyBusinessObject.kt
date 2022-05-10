package io.github.jangalinski.business.model

import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap

data class MyBusinessObject(
  val key: String,
  val name: String
)
