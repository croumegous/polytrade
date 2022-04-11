package db;

case class User(
    email: String,
    password: String,
    balance: Map[String, Double]
)
