package com.njkol.mongo.scala.utils

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

trait Configurable {
  
    val conf = com.typesafe.config.ConfigFactory.load()
}