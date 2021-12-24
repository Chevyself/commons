package me.googas.commons.modules.data.economy;

import lombok.Getter;
import lombok.NonNull;
import me.googas.commands.bukkit.utils.BukkitUtils;
import me.googas.commons.modules.placeholders.Line;

/** A transaction is an object that represents the result from an economic action */
public class Transaction {

  @NonNull @Getter private final TransactionType type;
  @Getter private final double amount;
  @Getter private final double balance;

  @NonNull @Getter
  private final String errorString = BukkitUtils.build("&cCannot proceed with transaction");

  @NonNull @Getter private Line error = new Line("&cCannot proceed with transaction");
  @NonNull @Getter private TransactionResponse transactionResponse = TransactionResponse.SUCCESS;

  /**
   * Create the transaction
   *
   * @param type the type of transaction
   * @param amount the amount of the transaction
   * @param balance the new balance of the account
   * @param error an error line in case the transaction goes wrong
   * @param transactionResponse the tpe of response from the transaction
   */
  public Transaction(
      @NonNull TransactionType type,
      double amount,
      double balance,
      @NonNull Line error,
      @NonNull TransactionResponse transactionResponse) {
    this.type = type;
    this.amount = amount;
    this.balance = balance;
    this.error = error;
    this.transactionResponse = transactionResponse;
  }

  /**
   * Create the transaction
   *
   * @param type the type of transaction
   * @param amount the amount of the transaction
   * @param balance the new balance of the account
   */
  public Transaction(@NonNull TransactionType type, double amount, double balance) {
    this.type = type;
    this.amount = amount;
    this.balance = balance;
  }

  /**
   * Set the error that caused the fail of a transaction
   *
   * @param error the line that represents the error of the transaction
   * @return this same instance
   */
  @NonNull
  public Transaction setError(@NonNull Line error) {
    this.error = error;
    return this;
  }

  /**
   * Set the response of the transaction
   *
   * @param transactionResponse the type of the response of this transaction
   * @return this same instance
   */
  @NonNull
  public Transaction setTransactionResponse(TransactionResponse transactionResponse) {
    this.transactionResponse = transactionResponse;
    return this;
  }
}
