import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

package com.log4j.custom;

object LogLayoutXml extends PatternLayout {
  final def format(event: LoggingEvent): String = {
    // https://www.wideskills.com/log4j-tutorial/10-custom-appender-and-layout-in-log4j
    val e: ServerError       = event.getMessage()
    val buffer: StringBuffer = new StringBuffer()

    // TODO: use a real XML object using java so that it auto-formats it correctly
    buffer.append(s"""
    |<error>
    |    <timestamp>${e.timestamp}</timestamp>
    |    <method>${e.method}</method>
    |    <uri>${e.uri}</uri>
    |    <cause>
    |        ${e.cause}
    |    </cause>
    |</error>
    |""")

    buffer.toString();
  }
}
