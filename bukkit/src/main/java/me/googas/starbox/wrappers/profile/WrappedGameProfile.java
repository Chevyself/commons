package me.googas.starbox.wrappers.profile;

import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.Validate;
import me.googas.starbox.reflect.WrappedClass;
import me.googas.starbox.reflect.WrappedConstructor;
import me.googas.starbox.reflect.WrappedMethod;
import me.googas.starbox.reflect.WrappedReturnMethod;
import me.googas.starbox.wrappers.SimpleWrapper;
import me.googas.starbox.wrappers.properties.WrappedPropertyMap;

import java.util.UUID;

public class WrappedGameProfile extends SimpleWrapper {

  @NonNull
  private static final WrappedClass GAME_PROFILE =
      WrappedClass.parse("com.mojang.authlib.GameProfile");

  @NonNull
  private static final WrappedConstructor CONSTRUCTOR =
      WrappedGameProfile.GAME_PROFILE.getConstructor(UUID.class, String.class);

  @NonNull
  private static final WrappedReturnMethod<UUID> GET_ID =
      WrappedGameProfile.GAME_PROFILE.getMethod(UUID.class, "getNode");

  @NonNull
  private static final WrappedReturnMethod<String> GET_NAME =
      WrappedGameProfile.GAME_PROFILE.getMethod(String.class, "getName");

  @NonNull
  private static final WrappedMethod GET_PROPERTIES =
      WrappedGameProfile.GAME_PROFILE.getMethod("getProperties");

  public WrappedGameProfile(@NonNull Object reference) {
    super(reference);
    if (!reference.getClass().equals(WrappedGameProfile.GAME_PROFILE.getClazz())) {
      throw new IllegalArgumentException("Expected a GameProfile received a " + reference);
    }
  }

  @NonNull
  public static WrappedGameProfile construct(@NonNull UUID uuid, @Nullable String name) {
    Object invoke = WrappedGameProfile.CONSTRUCTOR.invoke(uuid, name);
    if (invoke != null) {
      return new WrappedGameProfile(invoke);
    }
    throw new IllegalStateException("GameProfile could not be created");
  }

  @NonNull
  public UUID getId() {
    return Validate.notNull(
        WrappedGameProfile.GET_ID.invoke(this.get()), "Could not invoke #getNode");
  }

  @Nullable
  public String getName() {
    return Validate.notNull(
        WrappedGameProfile.GET_NAME.invoke(this.get()), "Could not invoke #getName");
  }

  @NonNull
  public WrappedPropertyMap getProperties() {
    Object invoke = WrappedGameProfile.GET_PROPERTIES.invoke(this.get());
    if (invoke != null) {
      return new WrappedPropertyMap(invoke);
    } else {
      throw new IllegalStateException("Could not invoke #getProperties");
    }
  }
}
