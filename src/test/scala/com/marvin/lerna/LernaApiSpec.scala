package com.marvin.lerna

import akka.http.scaladsl.HttpExt
import akka.http.scaladsl.model.StatusCodes
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.slf4j.Logger

import scala.concurrent.ExecutionContextExecutor

class LernaApiSpec extends WordSpecLike with Matchers with BeforeAndAfterAll with MockitoSugar with ScalatestRouteTest {
  implicit val dispatcher: ExecutionContextExecutor = system.dispatcher
  implicit val logger: Logger = mock[Logger]

  override def afterAll(): Unit = {
    system.terminate()
  }

  val mockHttp: HttpExt = mock[HttpExt]

  val lernaApi: LernaApi = new LernaApi {
//    override val http: HttpExt = mockHttp
  }

  val routes = lernaApi.routes

  "Routes" should {
    "return ok for healthCheck" in {
      Get("/healthCheck") ~> routes ~> check {
        status shouldBe StatusCodes.OK
      }
    }
  }

}
