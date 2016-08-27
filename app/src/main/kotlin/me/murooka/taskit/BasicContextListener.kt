package me.murooka.taskit

import java.util.logging.Logger
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener

class BasicContextListener : ServletContextListener {
    val logger = Logger.getLogger(BasicContextListener::class.java.name)!!

    override fun contextInitialized(event: ServletContextEvent?) {
        logger.info("Servlet Context created, $event")
        OfyService.setup()
    }

    override fun contextDestroyed(event: ServletContextEvent?) {
        logger.info("Servlet Context destroyed, $event")
    }
}
