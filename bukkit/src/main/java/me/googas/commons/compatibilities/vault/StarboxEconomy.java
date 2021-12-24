package me.googas.commons.compatibilities.vault;

import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.Starbox;
import me.googas.commons.modules.data.DataModule;
import me.googas.commons.modules.data.economy.EconomyHandler;
import me.googas.commons.modules.data.economy.Transaction;
import me.googas.commons.modules.data.economy.TransactionResponse;
import me.googas.commons.modules.data.type.Bank;
import me.googas.commons.modules.data.type.Profile;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

public class StarboxEconomy extends AbstractEconomy implements VaultImplementation {

  @Getter @NonNull private DataModule dataModule;

  public StarboxEconomy(@NonNull DataModule dataModule) {
    this.dataModule = dataModule;
  }

  @NonNull
  public static EconomyResponse parse(@NonNull Transaction transaction) {
    return new EconomyResponse(
        transaction.getAmount(),
        transaction.getBalance(),
        StarboxEconomy.parseResponse(transaction),
        transaction.getErrorString());
  }

  @NonNull
  public static EconomyResponse.ResponseType parseResponse(@NonNull Transaction transaction) {
    return StarboxEconomy.parse(transaction.getTransactionResponse());
  }

  @NonNull
  public static EconomyResponse.ResponseType parse(@NonNull TransactionResponse response) {
    switch (response) {
      case ERROR:
        return EconomyResponse.ResponseType.FAILURE;
      case SUCCESS:
        return EconomyResponse.ResponseType.SUCCESS;
      default:
        return EconomyResponse.ResponseType.NOT_IMPLEMENTED;
    }
  }

  @NonNull
  public EconomyHandler.BankProvider banks() {
    return this.dataModule.getEconomyHandler().getBankProvider();
  }

  @Nullable
  public Bank getBank(@Nullable String accountId) {
    return this.banks().getBank(accountId);
  }

  @NonNull
  public StarboxEconomy setDataModule(@NonNull DataModule dataModule) {
    this.dataModule = dataModule;
    return this;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String getName() {
    return "Starbox";
  }

  @Override
  public boolean hasBankSupport() {
    return true;
  }

  @Override
  public int fractionalDigits() {
    return this.dataModule.getEconomyHandler().getCurrency().getFractionalDigits();
  }

  @Override
  public String format(double value) {
    return this.dataModule.getEconomyHandler().getCurrency().format(value);
  }

  @Override
  public String currencyNamePlural() {
    return this.dataModule.getEconomyHandler().getCurrency().getNamePlural();
  }

  @Override
  public String currencyNameSingular() {
    return this.dataModule.getEconomyHandler().getCurrency().getName();
  }

  @Deprecated
  @Override
  public boolean hasAccount(String player) {
    Profile profile = this.getPlayer(player);
    if (profile != null) return profile.hasAccount(Starbox.getContext());
    return true;
  }

  @Deprecated
  @Override
  public boolean hasAccount(String player, String world) {
    Profile profile = this.getPlayer(player);
    if (profile != null) return profile.hasAccount(Starbox.buildContext(world));
    return true;
  }

  @Deprecated
  @Override
  public double getBalance(String player) {
    Profile profile = this.getPlayer(player);
    if (profile != null) return profile.getBalance(Starbox.getContext());
    return 0;
  }

  @Deprecated
  @Override
  public double getBalance(String player, String world) {
    Profile profile = this.getPlayer(player);
    if (profile != null) return profile.getBalance(Starbox.buildContext(world));
    return 0;
  }

  @Deprecated
  @Override
  public boolean has(String player, double amount) {
    Profile profile = this.getPlayer(player);
    if (profile != null) return profile.hasBalance(amount, Starbox.getContext());
    return false;
  }

  @Deprecated
  @Override
  public boolean has(String player, String world, double amount) {
    Profile profile = this.getPlayer(player);
    if (profile != null) return profile.hasBalance(amount, Starbox.buildContext(world));
    return false;
  }

  @Deprecated
  @Override
  public EconomyResponse withdrawPlayer(String player, double amount) {
    Profile profile = this.getPlayer(player);
    if (profile != null)
      return StarboxEconomy.parse(profile.withdraw(amount, Starbox.getContext()));
    return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "Player not found");
  }

  @Deprecated
  @Override
  public EconomyResponse withdrawPlayer(String player, String world, double amount) {
    Profile profile = this.getPlayer(player);
    if (profile != null)
      return StarboxEconomy.parse(profile.withdraw(amount, Starbox.buildContext(world)));
    return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "Player not found");
  }

  @Deprecated
  @Override
  public EconomyResponse depositPlayer(String player, double amount) {
    Profile profile = this.getPlayer(player);
    if (profile != null) return StarboxEconomy.parse(profile.deposit(amount, Starbox.getContext()));
    return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "Player not found");
  }

  @Deprecated
  @Override
  public EconomyResponse depositPlayer(String player, String world, double amount) {
    Profile profile = this.getPlayer(player);
    if (profile != null)
      return StarboxEconomy.parse(profile.deposit(amount, Starbox.buildContext(world)));
    return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "Player not found");
  }

  @Deprecated
  @Override
  public EconomyResponse createBank(String account, String player) {
    Profile profile = this.getPlayer(player);
    if (profile != null) {
      return StarboxEconomy.parse(this.banks().createBank(account, profile));
    }
    return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "Player not found");
  }

  @Override
  public EconomyResponse deleteBank(String account) {
    return StarboxEconomy.parse(this.banks().deleteBank(account));
  }

  @Override
  public EconomyResponse bankBalance(String account) {
    Bank bank = this.getBank(account);
    if (bank != null)
      return new EconomyResponse(
          0, bank.getBalance(Starbox.getContext()), EconomyResponse.ResponseType.SUCCESS, "");
    return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "Bank not found");
  }

  @Override
  public EconomyResponse bankHas(String account, double amount) {
    Bank bank = this.getBank(account);
    if (bank != null) {
      if (bank.hasBalance(amount, Starbox.getContext())) {
        return new EconomyResponse(
            0, bank.getBalance(Starbox.getContext()), EconomyResponse.ResponseType.SUCCESS, "");
      } else {
        return new EconomyResponse(
            0, bank.getBalance(Starbox.getContext()), EconomyResponse.ResponseType.FAILURE, "");
      }
    }
    return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "Bank not found");
  }

  @Override
  public EconomyResponse bankWithdraw(String account, double amount) {
    Bank bank = this.getBank(account);
    if (bank != null) return StarboxEconomy.parse(bank.withdraw(amount, Starbox.getContext()));
    return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "Bank not found");
  }

  @Override
  public EconomyResponse bankDeposit(String account, double amount) {
    Bank bank = this.getBank(account);
    if (bank != null) return StarboxEconomy.parse(bank.deposit(amount, Starbox.getContext()));
    return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "Bank not found");
  }

  @Deprecated
  @Override
  public EconomyResponse isBankOwner(String account, String player) {
    Profile profile = this.getPlayer(player);
    if (profile == null) {
      return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "Player not found");
    }
    Bank bank = this.getBank(account);
    if (bank == null) {
      return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "Bank not found");
    }
    EconomyResponse.ResponseType type = EconomyResponse.ResponseType.FAILURE;
    if (bank.isOwner(profile)) type = EconomyResponse.ResponseType.SUCCESS;
    return new EconomyResponse(0, bank.getBalance(), type, "");
  }

  @Deprecated
  @Override
  public EconomyResponse isBankMember(String account, String player) {
    Profile profile = this.getPlayer(player);
    if (profile == null) {
      return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "Player not found");
    }
    EconomyResponse.ResponseType type = EconomyResponse.ResponseType.FAILURE;
    if (this.banks().hasAccount(profile)) type = EconomyResponse.ResponseType.SUCCESS;
    return new EconomyResponse(0, 0, type, "");
  }

  @Override
  public List<String> getBanks() {
    return this.banks().getBanksIds();
  }

  @Deprecated
  @Override
  public boolean createPlayerAccount(String player) {
    Profile profile = this.getPlayer(player);
    if (profile == null) {
      return false;
    } else {
      return profile.createAccount(Starbox.getContext());
    }
  }

  @Deprecated
  @Override
  public boolean createPlayerAccount(String world, String player) {
    Profile profile = this.getPlayer(player);
    if (profile == null) {
      return false;
    } else {
      return profile.createAccount(Starbox.buildContext(world));
    }
  }

  @Override
  public boolean hasAccount(OfflinePlayer player) {
    return this.getPlayer(player).hasAccount(Starbox.getContext());
  }

  @Override
  public boolean hasAccount(OfflinePlayer player, String worldName) {
    return this.getPlayer(player).hasAccount(Starbox.buildContext(worldName));
  }

  @Override
  public double getBalance(OfflinePlayer player) {
    return this.getPlayer(player).getBalance(Starbox.getContext());
  }

  @Override
  public double getBalance(OfflinePlayer player, String world) {
    return this.getPlayer(player).getBalance(Starbox.buildContext(world));
  }

  @Override
  public boolean has(OfflinePlayer player, double amount) {
    return this.getPlayer(player).hasBalance(amount, Starbox.getContext());
  }

  @Override
  public boolean has(OfflinePlayer player, String worldName, double amount) {
    return this.getPlayer(player).hasBalance(amount, Starbox.buildContext(worldName));
  }

  @Override
  public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
    return StarboxEconomy.parse(this.getPlayer(player).withdraw(amount, Starbox.getContext()));
  }

  @Override
  public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
    return StarboxEconomy.parse(
        this.getPlayer(player).withdraw(amount, Starbox.buildContext(worldName)));
  }

  @Override
  public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
    return StarboxEconomy.parse(this.getPlayer(player).deposit(amount, Starbox.getContext()));
  }

  @Override
  public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
    return StarboxEconomy.parse(
        this.getPlayer(player).deposit(amount, Starbox.buildContext(worldName)));
  }

  @Override
  public EconomyResponse createBank(String name, OfflinePlayer player) {
    return StarboxEconomy.parse(this.banks().createBank(name, this.getPlayer(player)));
  }

  @Override
  public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
    Bank bank = this.getBank(name);
    if (bank == null) {
      return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "Bank not found");
    }
    EconomyResponse.ResponseType type = EconomyResponse.ResponseType.FAILURE;
    if (bank.isOwner(this.getPlayer(player))) type = EconomyResponse.ResponseType.SUCCESS;
    return new EconomyResponse(0, bank.getBalance(), type, "");
  }

  @Override
  public EconomyResponse isBankMember(String name, OfflinePlayer player) {
    EconomyResponse.ResponseType type = EconomyResponse.ResponseType.FAILURE;
    if (this.banks().hasAccount(this.getPlayer(player)))
      type = EconomyResponse.ResponseType.SUCCESS;
    return new EconomyResponse(0, 0, type, "");
  }

  @Override
  public boolean createPlayerAccount(OfflinePlayer player) {
    return this.getPlayer(player).createAccount(Starbox.getContext());
  }

  @Override
  public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
    return this.getPlayer(player).createAccount(Starbox.buildContext(worldName));
  }
}
