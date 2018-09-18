package com.njkol.mongo.scala.utils

import com.mongodb.casbah.Imports._
import com.njkol.mongo.scala.models.DataModels.PurchaseOrder

/**
 * Maps a PurchaseOrder to MongoDBObject & vice-versa
 *
 * @author Nilanjan Sarkar
 */
class PurchaseOrderMapper {

  /**
   * Convert a PurchaseOrder object into a BSON format that MongoDb can store
   */
  def buildMongoDbObject(po: PurchaseOrder): MongoDBObject = {
    val builder = MongoDBObject.newBuilder
    builder += "product" -> po.product
    builder += "price" -> po.price
    builder += "customer" -> po.customer
    builder.result
  }

  /**
   * Convert a MongoDBObject object back to JVM objects
   */
  def convertDbObjectToPurchaseOrder(obj: MongoDBObject): PurchaseOrder = {
    val product = obj.getAs[String]("product").getOrElse("")
    val price = obj.getAs[Double]("price").getOrElse(0.0)
    val customer = obj.getAs[String]("customer").getOrElse("")
    PurchaseOrder(product, price, customer)
  }
}