package com.njkol.mongo.scala

import com.njkol.mongo.scala.utils._
import com.njkol.mongo.scala.repo.PurchaseOrderRepo
import com.njkol.mongo.scala.models.DataModels.PurchaseOrder

/**
 * A sample app demonstrating Casbah - Scala API for MongoDB
 *
 * @author Nilanjan Sarkar
 */
object MongoScalaDemo extends App {

  val conn = MongoDBConnecter.getConnection
  val repo = new PurchaseOrderRepo(conn)

  // Test add
  val po1 = PurchaseOrder("Garnier Fructis", 100, "Nilanjan")
  val po2 = PurchaseOrder("Dabur Honey", 250, "Nilanjan")
  repo.savePurchaseOrder(po1)
  repo.savePurchaseOrder(po2)
  
  // Test update
  val latest = PurchaseOrder("Garnier Fructis", 95, "Nilanjan")
  repo.updatePurchaseOrder(po1,latest)

  // Test retrieval
  val res = repo.retrievePurchaseOrderByName("Nilanjan")
  for (order <- res) {
    println(order)
  }

  // Test delete
  repo.deletePurchaseOrder(po2)

  MongoDBConnecter.closeConnection(conn)
}