package com.marvin.lerna

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, pathPrefix}
import akka.http.scaladsl.server.{Route, RouteResult}
import akka.stream.ActorMaterializer
import com.typesafe.config.{ConfigFactory, ConfigValueFactory}
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.MySQLProfile.api._

object Lerna extends App {

  implicit val system: ActorSystem = ActorSystem("lerna")
  implicit val dispatcher: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val config = new Config{}

  implicit val logger: Logger = LoggerFactory.getLogger(classOf[App])

  val lernaApi = new LernaApi

  val customDbConf = ConfigFactory.load()
    .withValue("mysqldb.registerMbeans", ConfigValueFactory.fromAnyRef(true))
    .withValue("mysqldb.properties.url", ConfigValueFactory.fromAnyRef(config.dbUrl))
    .withValue("mysqldb.properties.user", ConfigValueFactory.fromAnyRef(config.dbUser))
    .withValue("mysqldb.properties.password", ConfigValueFactory.fromAnyRef(config.dbPassword))
    .withValue("mysqldb.properties.driver", ConfigValueFactory.fromAnyRef("slick.jdbc.MySQLProfile"))

  val db = Database.forConfig("mysqldb", customDbConf)

  def routes: Route = lernaApi.routes

  val adminApiBindingFuture: Future[ServerBinding] = Http()
    .bindAndHandle(RouteResult.route2HandlerFlow(routes), config.serverHost, config.serverPort)
    .map(binding => {
      logger.info(s"Server started on ${config.serverHost}:${config.serverPort}")
      binding
    })


}
