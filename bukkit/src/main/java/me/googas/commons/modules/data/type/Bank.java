package me.googas.commons.modules.data.type;

import lombok.NonNull;

/** A bank is an object which can be used to store money */
public interface Bank extends Account {

  /**
   * Checks if the given account is the owner of this bank
   *
   * @param account the account to check if it is the owner
   * @return true if the account is the owner of this bank
   */
  default boolean isOwner(@NonNull Account account) {
    return this.getOwner().equals(account);
  }

  /**
   * Get the account id of this bank
   *
   * @return the id of the bank
   */
  @NonNull
  String getId();

  /**
   * Get the account owner of this bank
   *
   * @return the owner of this bank
   */
  @NonNull
  Account getOwner();
}
