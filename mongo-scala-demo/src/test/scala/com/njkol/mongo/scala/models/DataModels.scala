package com.njkol.mongo.scala.models

object DataModels {

  case class PurchaseOrder(product: String, price: Double, customer: String)
}