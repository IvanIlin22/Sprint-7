package ru.sber.rdbms

import java.sql.DriverManager
import java.sql.SQLException

class TransferConstraint {
    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        val connect = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/postgres",
            "postgres",
            "postgres"
        )

        connect.use { connect ->
            try {
                val decAmountStatement = connect.prepareStatement("UPDATE account1 SET amount = amount - ? WHERE id=?")
                decAmountStatement.setLong(1, amount)
                decAmountStatement.setLong(2, accountId1)
                decAmountStatement.use { it.executeUpdate() }

                val incAmountStatement = connect.prepareStatement("UPDATE account1 SET amount = amount + ? WHERE id=?")
                incAmountStatement.setLong(1, amount)
                incAmountStatement.setLong(2, accountId2)
                incAmountStatement.use { it.executeUpdate() }
            } catch (ex: SQLException) {
                println(ex.message)
            }
        }
    }
}
