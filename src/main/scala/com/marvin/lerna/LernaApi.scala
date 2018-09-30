package com.marvin.lerna

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, pathPrefix}
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import org.slf4j.Logger

import scala.concurrent.ExecutionContext

class LernaApi (
                 implicit logger: Logger,
                 actorSystem: ActorSystem,
                 executionContext: ExecutionContext,
                 materializer: Materializer
               ){

  val http = Http()

  def routes: Route = {
    //    import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
    //    import fommil.sjs.FamilyFormats._

    pathPrefix("healthCheck") {
      complete(StatusCodes.OK)
    }
  }

}
