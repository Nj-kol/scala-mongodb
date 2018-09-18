
# Casbah - A Scala library for MongoDB

## Introduction

* Casbah integrates a layer on top of the official mongo-java-driver 
  for better integration with Scala

* This is as opposed to a native implementation of the MongoDB wire protocol, 
  which the Java driver does exceptionally well. Rather than a complete rewrite, 
  Casbah uses implicits, and Pimp My Library code to enhance the existing Java code

* Casbah aims to remain fully compatible with the existing Java driver—it 
  does not talk to MongoDB directly, preferring to wrap the Java code

## Dependencies

#### Maven

	<dependency>
		<groupId>org.mongodb</groupId>
		<artifactId>casbah-core_2.10</artifactId>
		<version>2.8.1</version>
	</dependency>

#### SBT

libraryDependencies += "org.mongodb" %% "casbah" % "3.1.1"

##  Using Casbah

* Core lives in the package namespace com.mongodb.casbah

* Casbah uses a few tricks to act as self contained as possible - it provides 
  an Imports object which automatically imports everything you need including 
  Implicit conversions and type aliases to a few common MongoDB types.
* This means you should only need to use our Imports package for the majority of your work
* The Imports call will make common types such as DBObject, MongoConnection 
  and MongoCollection available
* Core‘s Imports also run the imports from Commons and the Query DSL
* The general idea is that common Java types (such as ArrayList) will be 
  returned as the equivalent Scala type.

## Connecting to MongoDB

* The core Connection class is com.mongodb.casbah.MongoConnection

* There are two ways to create an instance of it :

  First, you can invoke .asScala from a MongoDB builtin Connection (com.mongodb.Mongo)

  The pure Scala way to do it is to invoke one of the apply methods on the companion object:
  
  // Connect to default - localhost, 27017
  val mongoConn = MongoConnection()
  val mongoConn = MongoConnection("mongodb01")
  val mongoConn = MongoConnection("mongodb02", 42001)
  
  // Using the URI method ( preferable )
  val mongouri = MongoClientURI("mongodb://127.0.0.1")
  val mongoConn = MongoClient(mongouri)

  // Get a collection
  val collection:MongoCollection = mongoConn("<db_name>")("<collection_name>")

* Casbah’s MongoCollection object implements Scala’s Iterable[A] interface
  (specifically Iterable[DBObject]), which provides a full monadic interface
  to your MongoDB collection

* Beginning iteration on the MongoCollection instance is fundamentally equivalent 
  to invoking find on the MongoCollection

## MongoDBObject - A Scala-ble DBObject Implementation

* MongoDBObject and its companion trait provide a series of ways to work 
  with Mongo’s DBObjects which closely match the Collection interface Scala 2.8 provides. 

* Further, MongoDBObject can be implicitly converted to a DBObject 
  - so any existing Mongo Java code will accept it without complain

* There are two easy ways to create a new MongoDBObject

* You could also use a Scala 2.8 style builder to create your object instead:

  val builder = MongoDBObject.newBuilder
  builder += "foo" -> "bar"
  builder += "x" -> "y"
  builder += ("pie" -> 3.14)
  builder += ("spam" -> "eggs", "mmm" -> "bacon")
  val newObj = builder.result
  Being a builder - you must call result to get a DBObject. 

## Saving data with Casbah

val obj = MongoDBObject("product" -> "Colgate maxfresh","price" -> 60 ,"customer" -> "Nilanjan")
collection.save(obj)

## Updating data with Casbah

val q = MongoDBObject("product" -> "Colgate maxfresh","price" -> 60 ,"customer" -> "Nilanjan")
val latest = MongoDBObject("product" -> "Colgate maxfresh","price" -> 66 ,"customer" -> "Nilanjan")
collection.findAndModify(q, mapper.buildMongoDbObject(latest))

## Querying with Casbah

val res = collection.find("customer" $eq "Nilanjan")

## Deleting with Casbah

val q: MongoDBObject = MongoDBObject("product" -> "Colgate maxfresh","price" -> 60 ,"customer" -> "Nilanjan")
collection.findAndRemove(mongoObj)

## Important Classes

com.mongodb.casbah.MongoClientURI
com.mongodb.casbah.MongoClientOptions
com.mongodb.casbah.MongoClient 
com.mongodb.casbah.MongoCollection

Sources
=======
https://mongodb.github.io/casbah/
http://api.mongodb.com/scala/casbah/2.0/tutorial.htmlS
https://alvinalexander.com/search/node?keys=casbah








