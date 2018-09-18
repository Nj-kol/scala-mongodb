package com.njkol.mongo.scala.utils

import com.mongodb.casbah.{ MongoClient, MongoClientURI, MongoClientOptions, MongoCollection }

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * A utility to manage MongoDB connections
 *
 * @author Nilanjan Sarkar
 */
object MongoDBConnecter extends Serializable with Configurable {

  private val mongoUri = conf.getString("casbah-demo.config.mongo-uri")
  private val mongouri = MongoClientURI(mongoUri)
  private var mongoClient = null.asInstanceOf[MongoClient]
  private val optionsBuilder = new MongoClientOptions.Builder();

  optionsBuilder.maxConnectionIdleTime(120000);
  optionsBuilder.maxConnectionLifeTime(3600000);
  var clientOptions = optionsBuilder.build();

  def getConnection: MongoClient = {
    if (null == mongoClient) {
      mongoClient = MongoClient(mongouri)
      mongoClient.addOption(clientOptions.getMaxConnectionIdleTime);
      mongoClient.addOption(clientOptions.getMaxConnectionLifeTime);
    }
    return mongoClient
  }

  def getCollection(dbName: String, collName: String): MongoCollection = return mongoClient(dbName)(collName)

  def closeConnection(conn: MongoClient) {
    conn.close
  }
}