package me.googas.starbox.modules.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.starbox.modules.data.type.Profile;
import org.bukkit.OfflinePlayer;

public class PlayersHandler {

  @NonNull @Getter private final List<ProfileProvider> profileProviders = new ArrayList<>();

  @Nullable
  public Profile getPlayer(@NonNull String player) {
    for (ProfileProvider provider : this.profileProviders) {
      Profile profile = provider.getPlayer(player);
      if (profile != null) return profile;
    }
    return null;
  }

  @NonNull
  public Profile getPlayer(OfflinePlayer player) {
    for (ProfileProvider provider : this.profileProviders) {
      Profile profile = provider.getPlayer(player);
      if (profile != null) return profile;
    }
    throw new IllegalStateException("Player data could not be created for " + player);
  }

  @NonNull
  public Profile getPlayer(@NonNull UUID uuid) {
    for (ProfileProvider provider : this.profileProviders) {
      Profile profile = provider.getPlayer(uuid);
      if (profile != null) return profile;
    }
    throw new IllegalStateException("Player data could not be created for " + uuid);
  }

  public interface ProfileProvider {

    @Nullable
    Profile getPlayer(@NonNull String player);

    @Nullable
    Profile getPlayer(@NonNull OfflinePlayer player);

    @Nullable
    Profile getPlayer(@NonNull UUID uuid);
  }
}
