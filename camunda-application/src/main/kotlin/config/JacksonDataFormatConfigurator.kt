package io.github.jangalinski.camunda.config

import io.github.jangalinski.lib.jackson.JacksonExt
import org.camunda.spin.impl.json.jackson.format.JacksonJsonDataFormat
import org.camunda.spin.spi.DataFormatConfigurator

/**
 * Configured SPIN Jackson Mapper.
 */
class JacksonDataFormatConfigurator : DataFormatConfigurator<JacksonJsonDataFormat> {

  override fun configure(dataFormat: JacksonJsonDataFormat) {
    val objectMapper = JacksonExt.defaultJacksonObjectMapper()
    dataFormat.objectMapper = objectMapper
  }

  override fun getDataFormatClass(): Class<JacksonJsonDataFormat> {
    return JacksonJsonDataFormat::class.java
  }

}
