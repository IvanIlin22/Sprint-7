package ru.sber.rdbms

import java.sql.DriverManager

class TransferPessimisticLock {
    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        val connect = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/postgres",
            "postgres",
            "postgres"
        )

        connect.use { connect ->
            val commit = connect.autoCommit
            try {
                connect.autoCommit = false
                val getAmountStatement = connect.prepareStatement("SELECT amount FROM account1 WHERE id = ?")
                getAmountStatement.setLong(1, accountId1)
                getAmountStatement.use { statement ->
                    val result = statement.executeQuery()
                    result.use {
                        it.next()
                        val amount1 = it.getInt(1)
                        if (amount1 - amount < 0) {
                            throw Exception("not enough money")
                        }
                    }
                }
                val getLockStatement = connect.prepareStatement("SELECT * FROM account1 where id in(?, ?) FOR UPDATE")
                getLockStatement.setLong(1, accountId1)
                getLockStatement.setLong(2, accountId2)
                getLockStatement.use {
                    it.executeQuery()
                }

                val decAmountStatement = connect.prepareStatement("UPDATE account1 SET amount = amount - ? WHERE id = ?")
                decAmountStatement.setLong(1, amount)
                decAmountStatement.setLong(2, accountId1)
                decAmountStatement.use {
                    it.executeUpdate()
                }
                val incAmountStatement = connect.prepareStatement("UPDATE account1 SET amount = amount + ? WHERE id = ?")
                incAmountStatement.setLong(1, amount)
                incAmountStatement.setLong(2, accountId2)
                incAmountStatement.use {
                    it.executeUpdate()
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
