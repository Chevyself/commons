package me.googas.starbox.modules.data.type;

import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.starbox.modules.data.economy.Transaction;
import me.googas.starbox.modules.data.economy.TransactionResponse;
import me.googas.starbox.modules.data.economy.TransactionType;
import me.googas.starbox.modules.placeholders.LocalizedLine;

import java.util.Map;

/** An account is an interface that objects implement when they are able to interact with economy */
public interface Account {

  /**
   * Checks if this account has currency in a global context
   *
   * @return true if this account has currency in a global context
   */
  default boolean hasAccount() {
    return this.hasAccount("global");
  }

  /**
   * Checks if this account has currency in the given context, if no context is given it will check
   * using {@link #hasAccount()}
   *
   * @param context the context to check the account
   * @return true if the account has currency in the given context
   */
  default boolean hasAccount(@Nullable String context) {
    if (context == null) context = "global";
    return this.getAccounts().get(context) != null;
  }

  /**
   * Withdraws the given amount from this account
   *
   * @param amount the amount to withdraw
   * @return the resulting transaction
   */
  @NonNull
  default Transaction withdraw(double amount) {
    return this.withdraw(amount, "global");
  }

  /**
   * Withdraws the given amount from this account in the given context
   *
   * @param amount the amount to withdraw
   * @param context the context in which the transaction will be completed if none is given it will
   *     use the global context
   * @return the resulting transaction
   */
  @NonNull
  default Transaction withdraw(double amount, @Nullable String context) {
    if (context == null) context = "global";
    if (!this.hasAccount(context)) {
      return new Transaction(
          TransactionType.WITHDRAW,
          amount,
          0,
          new LocalizedLine("account.not-created"),
          TransactionResponse.ERROR);
    }
    double balance = this.getBalance(context);
    if (balance < amount) {
      return new Transaction(
          TransactionType.WITHDRAW,
          amount,
          0,
          new LocalizedLine("account.no-balance"),
          TransactionResponse.ERROR);
    }
    double newBalance = balance - amount;
    this.getAccounts().put(context, newBalance);
    return new Transaction(TransactionType.WITHDRAW, amount, newBalance);
  }

  /**
   * Deposit money on this account
   *
   * @param amount the amount of money to deposit
   * @return the resulting transaction
   */
  @NonNull
  default Transaction deposit(double amount) {
    return this.deposit(amount, "global");
  }

  /**
   * Deposit money on this account
   *
   * @param amount the amount of money to deposit
   * @param context the context in which money will be given. If none is provided it will be given
   *     in the global context
   * @return the resulting transaction
   */
  @NonNull
  default Transaction deposit(double amount, @Nullable String context) {
    if (context == null) context = "global";
    if (!this.hasAccount(context)) {
      this.createAccount(context);
    }
    double newBalance = this.getBalance(context) + amount;
    this.getAccounts().put(context, newBalance);
    return new Transaction(TransactionType.DEPOSIT, amount, newBalance);
  }

  /**
   * Create the account for the global context
   *
   * @return true if the account was created
   */
  default boolean createAccount() {
    return this.createAccount("global");
  }

  /**
   * Create the account for the given context if no context is given then it will be created for the
   * global context
   *
   * @param context the context to create the account for
   * @return true if the account was created
   */
  default boolean createAccount(@Nullable String context) {
    if (context == null) context = "global";
    if (this.getAccounts().get(context) != null) return false;
    this.getAccounts().put(context, 0D);
    return true;
  }

  /**
   * Get the balance of this account in the given context
   *
   * @param context the context to get the balance of the account on. If no context is given it will
   *     use the global context
   * @return the balance of the account in the given context
   */
  default double getBalance(@Nullable String context) {
    if (context == null) context = "global";
    Double balance = this.getAccounts().get(context);
    if (balance == null)
      throw new IllegalStateException(
          "Account for the context " + context + " has not been created");
    return balance;
  }

  /**
   * Check if the account has enough balance of the amount
   *
   * @param amount the amount to match
   * @return true if the account has more or equal the amount
   */
  default boolean hasBalance(double amount) {
    if (!this.hasAccount()) return false;
    return this.getBalance() >= amount;
  }

  /**
   * Check if the account has enough balance of the amount in the given context
   *
   * @param amount the amount to match
   * @param context the context to check the balance if no context is given then the global context
   *     will be used
   * @return true if the account has more or equal the amount
   */
  default boolean hasBalance(double amount, @Nullable String context) {
    if (!this.hasAccount(context)) return false;
    return this.getBalance(context) >= amount;
  }

  /**
   * Get the balance of the account
   *
   * @return the balance of the account might be less than 0 if {@link #createAccount()} hasn't been
   *     called
   */
  default double getBalance() {
    return this.getBalance("global");
  }

  /**
   * Get the map of accounts for this account
   *
   * @return the map of accounts
   */
  @NonNull
  Map<String, Double> getAccounts();
}
