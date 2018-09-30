package com.marvin.lerna

import com.typesafe.config.ConfigFactory

trait Config {

  private lazy val config = ConfigFactory.load()

  lazy val serverHost = config.getString("server.host")
  lazy val serverPort = config.getInt("server.port")
}
