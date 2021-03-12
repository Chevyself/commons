package me.googas.starbox.utility.items.meta;

import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.starbox.reflect.APIVersion;
import me.googas.starbox.reflect.WrappedClass;
import me.googas.starbox.reflect.WrappedMethod;
import me.googas.starbox.utility.Versions;
import me.googas.starbox.utility.items.ItemBuilder;
import me.googas.starbox.wrappers.profile.WrappedGameProfile;
import me.googas.starbox.wrappers.properties.WrappedProperty;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class SkullMetaBuilder extends ItemMetaBuilder {

  @NonNull private static final WrappedClass SKULL_META = new WrappedClass(SkullMeta.class);

  @NonNull
  @APIVersion(value = 8, max = 11)
  private static final WrappedMethod SET_OWNER =
      SkullMetaBuilder.SKULL_META.getMethod("setOwner", String.class);

  @NonNull
  @APIVersion(12)
  private static final WrappedMethod SET_OWNING_PLAYER =
      SkullMetaBuilder.SKULL_META.getMethod("setOwningPlayer", OfflinePlayer.class);

  @Nullable private OfflinePlayer owner;
  @Nullable private String skin;

  public SkullMetaBuilder(@NonNull ItemBuilder itemBuilder) {
    super(itemBuilder);
  }

  private void appendSkin(@NonNull SkullMeta meta) {
    if (this.skin != null) {
      WrappedGameProfile gameProfile = WrappedGameProfile.construct(UUID.randomUUID(), null);
      gameProfile.getProperties().put("textures", WrappedProperty.construct("textures", this.skin));
      new WrappedClass(meta.getClass()).getDeclaredField("profile").set(meta, gameProfile.get());
    }
  }

  @NonNull
  public SkullMetaBuilder setOwner(@Nullable OfflinePlayer owner) {
    this.owner = owner;
    return this;
  }

  @NonNull
  public SkullMetaBuilder setSkin(@Nullable String skin) {
    this.skin = skin;
    return this;
  }

  @Override
  public SkullMeta build(@NonNull ItemStack stack) {
    ItemMeta itemMeta = super.build(stack);
    if (!(itemMeta instanceof SkullMeta)) return null;
    SkullMeta meta = (SkullMeta) itemMeta;
    if (this.owner != null) {
      if (Versions.BUKKIT > 11) {
        SkullMetaBuilder.SET_OWNING_PLAYER.invoke(meta, this.owner);
      } else {
        SkullMetaBuilder.SET_OWNER.invoke(meta, this.owner.getName());
      }
    }
    this.appendSkin(meta);
    return meta;
  }
}
