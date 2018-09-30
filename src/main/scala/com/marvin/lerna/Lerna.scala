package com.marvin.lerna

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, pathPrefix}
import akka.http.scaladsl.server.{Route, RouteResult}
import akka.stream.ActorMaterializer
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.{ExecutionContext, Future}

object Lerna extends App {

  implicit val system: ActorSystem = ActorSystem("lerna")
  implicit val dispatcher: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val config = new Config{}

  implicit val logger: Logger = LoggerFactory.getLogger(classOf[App])

  val lernaApi = new LernaApi

  def routes: Route = lernaApi.routes

  val adminApiBindingFuture: Future[ServerBinding] = Http()
    .bindAndHandle(RouteResult.route2HandlerFlow(routes), config.serverHost, config.serverPort)
    .map(binding => {
      logger.info(s"Server started on ${config.serverHost}:${config.serverPort}")
      binding
    })


}
