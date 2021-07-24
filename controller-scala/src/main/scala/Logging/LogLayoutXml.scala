import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

object LogLayoutXml extends PatternLayout {
  final def format(event: LoggingEvent): String = {
    // https://www.wideskills.com/log4j-tutorial/10-custom-appender-and-layout-in-log4j
    val user: UserObject = event.getMessage()

    val sb: StringBuffer = new StringBuffer()

    val name: String = user.getName()

    sb.append("<user>");
    sb.append("<name>").append(name).append("</name>");
    sb.append("</user>");
    sb.append("\n");

    sb.toString();
  }
}
