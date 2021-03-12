package me.googas.starbox.modules.data.economy;

import me.googas.starbox.modules.data.type.Profile;

/** The type of transaction */
public enum TransactionType {
  /** When a certain amount is taken from a {@link me.googas.starbox.modules.data.type.Account} */
  WITHDRAW,
  /** When a certain amount is given to a {@link me.googas.starbox.modules.data.type.Account} */
  DEPOSIT,
  /** If the transaction type could not be matches */
  UNKNOWN,
  /**
   * When a bank is deleted by {@link
   * me.googas.starbox.modules.data.economy.EconomyHandler.BankProvider#deleteBank(String)}
   */
  DELETE_BANK,
  /**
   * When a bank is created by {@link
   * me.googas.starbox.modules.data.economy.EconomyHandler.BankProvider#createBank(String, Profile)}
   */
  CREATE_BANK
}
