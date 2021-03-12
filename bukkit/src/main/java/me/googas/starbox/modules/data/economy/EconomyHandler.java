package me.googas.starbox.modules.data.economy;

import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.starbox.modules.data.type.Bank;
import me.googas.starbox.modules.data.type.Currency;
import me.googas.starbox.modules.data.type.Profile;
import me.googas.starbox.modules.placeholders.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * This object is in charge of handling the economy of the server with a given currency and banks
 */
public class EconomyHandler {

  @NonNull @Getter private Currency currency = new DefaultCurrency();
  @NonNull @Getter private BankProvider bankProvider = new DefaultBankProvider();

  @NonNull
  public EconomyHandler setBankProvider(BankProvider bankProvider) {
    this.bankProvider = bankProvider;
    return this;
  }

  @NonNull
  public EconomyHandler setCurrency(Currency currency) {
    this.currency = currency;
    return this;
  }

  /** This interface must be implemented by bank providers */
  public interface BankProvider {

    /**
     * Create a bank
     *
     * @param accountId the account id of the bank
     * @param profile the player owner of the bank
     * @return the transaction of the bank being created
     */
    @NonNull
    Transaction createBank(@NonNull String accountId, @NonNull Profile profile);

    /**
     * Delete a bank
     *
     * @param accountId the id of the bank account
     * @return the transaction of the bank being deleted
     */
    @NonNull
    Transaction deleteBank(@NonNull String accountId);

    /**
     * Get a bank by its account id
     *
     * @param accountId the account id of the bank
     * @return the bank if found else null
     */
    @Nullable
    Bank getBank(String accountId);

    /**
     * Check if a player has at least one bank account
     *
     * @param profile the player to check if it has at least one bank account
     * @return true if the player has one bank account
     */
    boolean hasAccount(@NonNull Profile profile);

    /**
     * Get all the banks that exist
     *
     * @return all the banks
     */
    @NonNull
    List<Bank> getBanks();

    /**
     * Get all the banks ids
     *
     * @return the banks ids
     */
    @NonNull
    default List<String> getBanksIds() {
      List<String> ids = new ArrayList<>();
      for (Bank bank : this.getBanks()) {
        ids.add(bank.getId());
      }
      return ids;
    }
  }

  /** A default implementation for currency */
  public static class DefaultCurrency implements Currency {

    @Override
    public @NonNull String getName() {
      return "Credit";
    }

    @Override
    public @NonNull String getNamePlural() {
      return "Credits";
    }
  }

  /** A default implementation for bank provider */
  public static class DefaultBankProvider implements BankProvider {

    @Override
    public @NonNull Transaction createBank(@Nullable String accountId, @NonNull Profile profile) {
      return new Transaction(
          TransactionType.CREATE_BANK,
          0,
          0,
          new Line("Not implemented"),
          TransactionResponse.ERROR);
    }

    @Override
    public @NonNull Transaction deleteBank(@Nullable String accountId) {
      return new Transaction(
          TransactionType.DELETE_BANK,
          0,
          0,
          new Line("Not implemented"),
          TransactionResponse.ERROR);
    }

    @Nullable
    @Override
    public Bank getBank(String accountId) {
      return null;
    }

    @Override
    public boolean hasAccount(@NonNull Profile profile) {
      return false;
    }

    @Override
    public @NonNull List<Bank> getBanks() {
      return new ArrayList<>();
    }
  }
}
