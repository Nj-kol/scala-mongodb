package com.njkol.mongo.scala.repo

import scala.collection.mutable.ArrayBuffer
import com.mongodb.casbah.Imports._

import com.njkol.mongo.scala.models.DataModels.PurchaseOrder
import com.njkol.mongo.scala.utils._

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Repository for purchase order
 *
 * @author Nilanjan Sarkar
 */
class PurchaseOrderRepo(conn: MongoClient) extends Configurable {

  private val dbName = conf.getString("casbah-demo.config.db-name")
  
  private val collName = conf.getString("casbah-demo.config.collection-name")
  private val mapper = new PurchaseOrderMapper
  private val logger = LogManager.getLogger(this.getClass)

  /**
   * Saves a purchase order
   *
   * @param po A Purchase order
   */
  def savePurchaseOrder(po: PurchaseOrder) {
    val mongoObj = mapper.buildMongoDbObject(po)
    val collection = MongoDBConnecter.getCollection(dbName, collName)
    collection.save(mongoObj)
    logger.info("Successfully saved purchase order")
  }

  /**
   * Updates a purchase order
   *
   * @param old    Existing Purchase order
   * @param latest Updated Purchase order
   */
  def updatePurchaseOrder(old: PurchaseOrder, latest: PurchaseOrder) {

    val collection = MongoDBConnecter.getCollection(dbName, collName)
    val q = mapper.buildMongoDbObject(old)
    collection.findAndModify(q, mapper.buildMongoDbObject(latest))
    logger.info("Successfully updated purchase order")
  }

  /**
   * Removes a purchase order
   *
   * @param po A Purchase order
   */
  def deletePurchaseOrder(po: PurchaseOrder) {
    val mongoObj = mapper.buildMongoDbObject(po)
    val collection = MongoDBConnecter.getCollection(dbName, collName)
    collection.findAndRemove(mongoObj)
    logger.info("Successfully removed purchase order")
  }

  /**
   * Retrieves all the orders of a particular customer
   *
   *  @param custName Name of the customer
   */
  def retrievePurchaseOrderByName(custName: String): Array[PurchaseOrder] = {

    var pos = ArrayBuffer[PurchaseOrder]()
    var q = MongoDBObject("customer" -> custName)
    val collection = MongoDBConnecter.getCollection(dbName, collName)
    val res = collection.find("customer" $eq custName)

    var i = 0
    for (dbObject <- res) {
      i = i + 1
      logger.info(s"Found ${i} entry/entries")
      pos += mapper.convertDbObjectToPurchaseOrder(dbObject)
    }
    pos.toArray
  }
}