package ru.sber.rdbms

import java.sql.DriverManager

class TransferOptimisticLock {
    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        val connect = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/postgres",
            "postgres",
            "postgres"
        )
        var oldVersion: Int

        connect.use { connect ->
            val commit = connect.autoCommit
            try {
                connect.autoCommit = false
                val getVersionStatement1 = connect.prepareStatement("SELECT amount, version FROM account1 WHERE id = ?")
                getVersionStatement1.setLong(1, accountId1)
                getVersionStatement1.use { statement ->
                    val result = statement.executeQuery()
                    result.use {
                        it.next()
                        val amount1 = it.getInt(1)
                        oldVersion = it.getInt(2)
                        if (amount1 - amount < 0) {
                            throw Exception("not enough money")
                        }
                    }
                }
                val decAmountStatement = connect.prepareStatement("UPDATE account1 SET amount = amount - ?," +
                        "version = version + 1 WHERE id = ? and version = ?")
                decAmountStatement.setLong(1, amount)
                decAmountStatement.setLong(2, accountId1)
                decAmountStatement.setInt(3, oldVersion)

                decAmountStatement.use {
                    val update = it.executeUpdate()
                    if (update == 0) {
                        throw Exception("data integrity violation")
                    }
                }
                val getVersionStatement2 = connect.prepareStatement("SELECT version FROM account1 where id = ?")
                getVersionStatement2.setLong(1, accountId2)
                getVersionStatement2.use { statement ->
                    val result = statement.executeQuery()
                    result.use {
                        it.next()
                        oldVersion = it.getInt(1)
                    }
                }
                val incAmountStatement = connect.prepareStatement("UPDATE account1 SET amount = amount + ?," +
                        "version = version + 1 WHERE id = ? and version = ?")
                incAmountStatement.setLong(1, amount)
                incAmountStatement.setLong(2, accountId2)
                incAmountStatement.setInt(3, oldVersion)
                incAmountStatement.use {
                    val update = it.executeUpdate()
                    if (update == 0) {
                        throw Exception("data integrity violation")
                    }
                }

                connect.commit()
            } catch (ex: Exception) {
                println(ex)
                connect.rollback()
            } finally {
                connect.autoCommit = commit
            }
        }
    }
}
