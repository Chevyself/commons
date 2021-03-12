package me.googas.starbox.gameplay.world;

import lombok.Getter;
import lombok.NonNull;
import me.googas.commons.RandomUtils;
import me.googas.commons.math.geometry.Point;
import me.googas.commons.math.geometry.Sphere;
import me.googas.starbox.Starbox;
import me.googas.starbox.reflect.APIVersion;
import me.googas.starbox.reflect.WrappedClass;
import me.googas.starbox.reflect.WrappedMethod;
import me.googas.starbox.utility.Versions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

/**
 * A match world is where the match is happening please note that some matches could have more than
 * one world
 */
public class MatchWorld {

  @NonNull private static final WrappedClass WORLD = new WrappedClass(World.class);

  @NonNull
  @APIVersion(13)
  private static final WrappedMethod IS_CHUNK_GENERATED =
      MatchWorld.WORLD.getMethod("isChunkGenerated", int.class, int.class);

  @NonNull @Getter private final World world;

  /**
   * Create the match world
   *
   * @param world the actual bukkit world
   */
  public MatchWorld(@NonNull World world) {
    this.world = world;
  }

  /**
   * This method can be used to make the Bukkit world generate to certain portion
   *
   * @param size the size of the Bukkit world to be generated
   */
  public void generate(int size) {
    Starbox.getScheduler().async(new GenerateWorldTask(size));
  }

  /**
   * Generate the world to the size of the world border
   *
   * @see World#getWorldBorder()
   */
  public void generate() {
    this.generate((int) (this.world.getWorldBorder().getSize() / 2));
  }

  /**
   * Checks if the chunk of the given coordinates is generated
   *
   * @param x the x coordinate of the chunk
   * @param z the z coordinate of the chunk
   * @return true if the chunk is generated
   */
  private boolean isGenerated(int x, int z) {
    if (Versions.BUKKIT < 13) {
      return this.world.getChunkAt(x, z).isLoaded();
    } else {
      Object invoke = MatchWorld.IS_CHUNK_GENERATED.invoke(this.world, x, z);
      if (invoke instanceof Boolean) {
        return (boolean) invoke;
      }
      return true;
    }
  }

  /**
   * Get a random location in this world
   *
   * @param size the maximum coordinate to which the coordinate can be located
   * @param safe whether the location must be safe
   * @return the random location
   */
  @NonNull
  public Location getRandomLocation(double size, boolean safe) {
    double x = Math.floor(RandomUtils.nextDouble(-size, size)) + 0.5;
    double z = Math.floor(RandomUtils.nextDouble(-size, size)) + 0.5;
    double y = this.world.getHighestBlockYAt((int) x, (int) z);
    if (safe && !this.isSafe(x, y, z)) {
      Sphere sphere = new Sphere(null, new Point(x, y, z), 2);
      for (Point point : sphere.getPointsInside().getPoints()) {
        if (this.isSafe(point.getX(), point.getY(), point.getZ())) {
          return new Location(this.world, point.getX() + 0.5, point.getY(), point.getZ() + 0.5);
        }
      }
    }
    return new Location(this.world, x, y, z);
  }

  /**
   * Check if the location of the given coordinates is safe
   *
   * @param x the x coordinate of the block
   * @param y the y coordinate of the block
   * @param z the z coordinate of the block
   * @return true if the block is safe to spawn
   */
  private boolean isSafe(@NonNull Number x, @NonNull Number y, @NonNull Number z) {
    Block head = this.world.getBlockAt(x.intValue(), y.intValue() + 1, z.intValue());
    Block feet = this.world.getBlockAt(x.intValue(), y.intValue(), z.intValue());
    Block below = this.world.getBlockAt(x.intValue(), y.intValue() - 1, z.intValue());
    return this.isSafe(head, feet, below);
  }

  /**
   * Check if the three blocks for a player to stand are safe this means that the head must be air
   * the feet must be air and the block standing solid or water
   *
   * @param head the block in the head
   * @param feet the block in the feet
   * @param below the block where the player will stand
   * @return true if all the checks are met
   */
  private boolean isSafe(@NonNull Block head, @NonNull Block feet, @NonNull Block below) {
    return head.getType() == Material.AIR
        && feet.getType() == Material.AIR
        && (below.getType().isSolid() || below.getType() == Material.WATER);
  }

  /**
   * @see #getRandomLocation(double, boolean) but the size will be the world border of the world
   * @param safe whether the location must be safe
   * @return the location
   */
  @NonNull
  public Location getRandomLocation(boolean safe) {
    return this.getRandomLocation(this.world.getWorldBorder().getSize() / 2, safe);
  }

  /**
   * Get the spawn location of the world
   *
   * @return the spawn location of the world
   */
  @NotNull
  public Location getSpawnLocation() {
    return this.world.getSpawnLocation();
  }

  /**
   * This is the task that makes the match generate. This can be useful for minigames such as UHC to
   * have its world generated to reduce lag
   */
  private class GenerateWorldTask implements Runnable {

    private final int size;

    private GenerateWorldTask(int size) {
      this.size = size;
    }

    @Override
    public void run() {
      int maxX = this.size / 16;
      int minX = -this.size;
      int maxZ = this.size / 16;
      int minZ = -this.size;
      for (int x = minX; x < maxX; x++) {
        for (int z = minZ; z < maxZ; z++) {
          if (!MatchWorld.this.isGenerated(x, z)) {
            Starbox.getScheduler().sync(new GenerateChunkTask(x, z));
          }
        }
      }
    }
  }

  /** This task is used by {@link GenerateWorldTask} to generate a chunk */
  private class GenerateChunkTask implements Runnable {
    private final int x;
    private final int z;

    private GenerateChunkTask(int x, int z) {
      this.x = x;
      this.z = z;
    }

    @Override
    public void run() {
      MatchWorld.this.world.getChunkAt(this.x, this.z).load(true);
    }
  }
}
