package me.googas.commons.gameplay.world;

import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.RandomUtils;
import me.googas.commons.Starbox;
import me.googas.commons.builder.Builder;
import me.googas.commons.builder.ToStringBuilder;
import me.googas.commons.reflect.APIVersion;
import me.googas.commons.reflect.wrappers.WrappedClass;
import me.googas.commons.reflect.wrappers.WrappedMethod;
import me.googas.commons.utility.Versions;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class MatchWorldBuilder implements Builder<MatchWorld> {

  @NonNull private final WrappedClass WORLD_CREATOR = new WrappedClass(WorldCreator.class);

  @NonNull
  @APIVersion(15)
  private final WrappedMethod HARDCORE = this.WORLD_CREATOR.getMethod("hardcore", boolean.class);

  @NonNull @Getter private String path = "/matches/unknown/world";
  private long seed = RandomUtils.getRandom().nextLong();
  @NonNull @Getter private World.Environment environment = World.Environment.NORMAL;
  @Nullable @Getter private ChunkGenerator generator = null;
  @NonNull @Getter private WorldType type = WorldType.NORMAL;
  @Getter private boolean generateStructures = true;
  @Nullable @Getter private String generatorSettings = null;
  @Getter private boolean hardcore = false;
  @Nullable @Getter private List<BlockPopulator> populators = null;

  public MatchWorldBuilder setPath(@NonNull String path) {
    this.path = path;
    return this;
  }

  public MatchWorldBuilder setSeed(long seed) {
    this.seed = seed;
    return this;
  }

  public MatchWorldBuilder setEnvironment(@NonNull World.Environment environment) {
    this.environment = environment;
    return this;
  }

  public MatchWorldBuilder setGenerator(@Nullable ChunkGenerator generator) {
    this.generator = generator;
    return this;
  }

  public MatchWorldBuilder setType(@NonNull WorldType type) {
    this.type = type;
    return this;
  }

  public MatchWorldBuilder setPopulators(@Nullable List<BlockPopulator> populators) {
    this.populators = populators;
    return this;
  }

  public MatchWorldBuilder setGenerateStructures(boolean generateStructures) {
    this.generateStructures = generateStructures;
    return this;
  }

  public MatchWorldBuilder setGeneratorSettings(@Nullable String generatorSettings) {
    this.generatorSettings = generatorSettings;
    return this;
  }

  public MatchWorldBuilder setHardcore(boolean hardcore) {
    this.hardcore = hardcore;
    return this;
  }

  @Override
  public @NonNull MatchWorld build() {
    WorldCreator creator =
        new WorldCreator(this.path)
            .seed(this.seed)
            .environment(this.environment)
            .type(this.type)
            .generateStructures(this.generateStructures);
    if (this.generator != null) creator.generator(this.generator);
    if (this.generatorSettings != null) creator.generatorSettings(this.generatorSettings);
    if (Versions.BUKKIT >= 15) this.HARDCORE.invoke(creator, this.hardcore);
    PopulatorAdder populatorAdder = null;
    if (this.populators != null && !this.populators.isEmpty()) {
      populatorAdder = new PopulatorAdder(this);
      Bukkit.getServer().getPluginManager().registerEvents(populatorAdder, Starbox.getPlugin());
    }
    World world = creator.createWorld();
    if (populatorAdder != null) HandlerList.unregisterAll(populatorAdder);
    if (world == null) throw new IllegalStateException("Could not create world in " + this);
    return new MatchWorld(world);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("path", this.path)
        .append("seed", this.seed)
        .append("environment", this.environment)
        .append("generator", this.generator)
        .append("type", this.type)
        .append("generateStructures", this.generateStructures)
        .append("generatorSettings", this.generatorSettings)
        .append("hardcore", this.hardcore)
        .build();
  }

  public static class PopulatorAdder implements Listener {

    @NonNull private final MatchWorldBuilder builder;

    public PopulatorAdder(@NonNull MatchWorldBuilder builder) {
      this.builder = builder;
    }

    @EventHandler
    public void onWorldInit(WorldInitEvent event) {
      World world = event.getWorld();
      if (!world.getName().equalsIgnoreCase(this.builder.getPath())
          || this.builder.getPopulators() == null) return;
      world.getPopulators().addAll(this.builder.getPopulators());
    }
  }
}
