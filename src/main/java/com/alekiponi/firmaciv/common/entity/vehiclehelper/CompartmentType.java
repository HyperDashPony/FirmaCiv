package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.AbstractCompartmentEntity;
import com.google.common.collect.ImmutableSet;
import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.network.PlayMessages;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/**
 * Besides {@link #register(CompartmentType)} and {@link #fromStack(ItemStack)} This is essentially to keep the vanilla
 * {@link EntityType#create(Level)} api but with an additional ItemStack parameter to allow for constructor logic based
 * on the ItemStack. For example using the stack NBT to initialize the compartment
 *
 * @param <T> The type of compartment
 */
public class CompartmentType<T extends AbstractCompartmentEntity> extends EntityType<T> {
    private static final List<CompartmentType<? extends AbstractCompartmentEntity>> COMPARTMENT_TYPES = new ArrayList<>();
    private final CompartmentFactory<T> factory;
    private final Predicate<ItemStack> predicate;


    @SuppressWarnings("unused")
    public CompartmentType(final EntityFactory<T> entityFactory, final MobCategory mobCategory, final boolean serialize,
            final boolean summon, final boolean fireImmune, final boolean canSpawnFarFromPlayer,
            final ImmutableSet<Block> immuneTo, final EntityDimensions dimensions, final int clientTrackingRange,
            final int updateInterval, final FeatureFlagSet requiredFeatures,
            final CompartmentFactory<T> compartmentFactory, final Predicate<ItemStack> predicate) {
        super(entityFactory, mobCategory, serialize, summon, fireImmune, canSpawnFarFromPlayer, immuneTo, dimensions,
                clientTrackingRange, updateInterval, requiredFeatures);
        this.factory = compartmentFactory;
        this.predicate = predicate;
    }

    public CompartmentType(final EntityFactory<T> entityFactory, final MobCategory mobCategory, final boolean serialize,
            final boolean summon, final boolean fireImmune, final boolean canSpawnFarFromPlayer,
            final ImmutableSet<Block> immuneTo, final EntityDimensions dimensions, final int clientTrackingRange,
            final int updateInterval, final FeatureFlagSet requiredFeatures,
            final Predicate<EntityType<?>> velocityUpdateSupplier,
            final ToIntFunction<EntityType<?>> trackingRangeSupplier,
            final ToIntFunction<EntityType<?>> updateIntervalSupplier,
            @Nullable final BiFunction<PlayMessages.SpawnEntity, Level, T> customClientFactory,
            final CompartmentFactory<T> factory, final Predicate<ItemStack> predicate) {
        //noinspection DataFlowIssue    customClientFactory can be null
        super(entityFactory, mobCategory, serialize, summon, fireImmune, canSpawnFarFromPlayer, immuneTo, dimensions,
                clientTrackingRange, updateInterval, requiredFeatures, velocityUpdateSupplier, trackingRangeSupplier,
                updateIntervalSupplier, customClientFactory);
        this.factory = factory;
        this.predicate = predicate;
    }

    /**
     * Registers a Compartment Type to be automatically picked and constructed when empty compartments are right-clicked
     * with a ItemStack matching the CompartmentTypes ItemStack predicate
     */
    public static <T extends AbstractCompartmentEntity> CompartmentType<T> register(
            final CompartmentType<T> compartmentType) {
        COMPARTMENT_TYPES.add(compartmentType);
        return compartmentType;
    }

    /**
     * Gets an applicable compartment type for an item stack
     *
     * @param itemStack The item stack
     * @return An applicable compartment for the given item stack. Returns the first compartment found,
     * this means registry order can effect which is chosen
     */
    public static Optional<CompartmentType<?>> fromStack(final ItemStack itemStack) {
        for (final CompartmentType<?> compartmentType : COMPARTMENT_TYPES) {
            if (compartmentType.predicate.test(itemStack)) return Optional.of(compartmentType);
        }

        return Optional.empty();
    }

    /**
     * Create a Compartment Entity for this CompartmentType.
     * Respects the levels enabled features
     */
    @Nullable
    public T create(final Level level, final ItemStack itemStack) {
        return !this.isEnabled(level.enabledFeatures()) ? null : this.factory.create(this, level, itemStack);
    }

    /**
     * Like vanillas {@link EntityType.EntityFactory} but takes an additional {@link ItemStack} parameter
     *
     * @param <T> The type of compartment
     */
    public interface CompartmentFactory<T extends AbstractCompartmentEntity> {
        T create(final CompartmentType<T> entityType, final Level level, final ItemStack itemStack);
    }

    /**
     * Mostly a copy of {@link EntityType.Builder} but it additionally takes a compartment
     * factory and an item stack predicate
     */
    @SuppressWarnings({"unused", "UnusedReturnValue"})
    public static class Builder<T extends AbstractCompartmentEntity> {
        private final EntityType.EntityFactory<T> factory;
        private final MobCategory category;
        private final CompartmentFactory<T> compartmentFactory;
        private final Predicate<ItemStack> predicate;
        private ImmutableSet<Block> immuneTo = ImmutableSet.of();
        private boolean serialize = true;
        private boolean summon = true;
        private boolean fireImmune;
        private boolean canSpawnFarFromPlayer;
        private int clientTrackingRange = 5;
        private int updateInterval = 3;
        private EntityDimensions dimensions = EntityDimensions.scalable(0.6F, 1.8F);
        private FeatureFlagSet requiredFeatures = FeatureFlags.VANILLA_SET;
        private Predicate<EntityType<?>> velocityUpdateSupplier = entityType -> true;
        private ToIntFunction<EntityType<?>> trackingRangeSupplier = entityType -> entityType.clientTrackingRange;
        private ToIntFunction<EntityType<?>> updateIntervalSupplier = entityType -> entityType.updateInterval;
        @Nullable
        private BiFunction<PlayMessages.SpawnEntity, Level, T> customClientFactory;

        private Builder(final EntityType.EntityFactory<T> entityFactory, final CompartmentFactory<T> compartmentFactory,
                final Predicate<ItemStack> predicate, final MobCategory mobCategory) {
            this.factory = entityFactory;
            this.compartmentFactory = compartmentFactory;
            this.predicate = predicate;
            this.category = mobCategory;
            this.canSpawnFarFromPlayer = mobCategory == MobCategory.CREATURE || mobCategory == MobCategory.MISC;
        }

        public static <T extends AbstractCompartmentEntity> Builder<T> of(
                final EntityType.EntityFactory<T> entityFactory, final CompartmentFactory<T> compartmentFactory,
                final Predicate<ItemStack> predicate, final MobCategory mobCategory) {
            return new Builder<>(entityFactory, compartmentFactory, predicate, mobCategory);
        }

        public static <T extends AbstractCompartmentEntity> Builder<T> createNothing(final MobCategory mobCategory) {
            //noinspection DataFlowIssue
            return new Builder<>((entityType, level) -> null, (entityType, level, itemStack) -> null,
                    itemStack -> false, mobCategory);
        }

        public Builder<T> sized(final float width, final float height) {
            this.dimensions = EntityDimensions.scalable(width, height);
            return this;
        }

        public Builder<T> noSummon() {
            this.summon = false;
            return this;
        }

        public Builder<T> noSave() {
            this.serialize = false;
            return this;
        }

        public Builder<T> fireImmune() {
            this.fireImmune = true;
            return this;
        }

        public Builder<T> immuneTo(final Block... blocks) {
            this.immuneTo = ImmutableSet.copyOf(blocks);
            return this;
        }

        public Builder<T> canSpawnFarFromPlayer() {
            this.canSpawnFarFromPlayer = true;
            return this;
        }

        public Builder<T> clientTrackingRange(final int clientTrackingRange) {
            this.clientTrackingRange = clientTrackingRange;
            return this;
        }

        public Builder<T> updateInterval(final int updateInterval) {
            this.updateInterval = updateInterval;
            return this;
        }

        public Builder<T> requiredFeatures(final FeatureFlag... requiredFeatures) {
            this.requiredFeatures = FeatureFlags.REGISTRY.subset(requiredFeatures);
            return this;
        }

        public Builder<T> setUpdateInterval(final int interval) {
            this.updateIntervalSupplier = t -> interval;
            return this;
        }

        public Builder<T> setTrackingRange(final int range) {
            this.trackingRangeSupplier = t -> range;
            return this;
        }

        public Builder<T> setShouldReceiveVelocityUpdates(final boolean value) {
            this.velocityUpdateSupplier = t -> value;
            return this;
        }

        /**
         * By default, entities are spawned clientside via {@link EntityType#create(Level)}}.
         * If you need finer control over the spawning process, use this to get read access to the spawn packet.
         */
        public Builder<T> setCustomClientFactory(
                final BiFunction<PlayMessages.SpawnEntity, Level, T> customClientFactory) {
            this.customClientFactory = customClientFactory;
            return this;
        }

        public CompartmentType<T> build(final String key) {
            if (this.serialize) {
                Util.fetchChoiceType(References.ENTITY_TREE, key);
            }

            return new CompartmentType<>(this.factory, this.category, this.serialize, this.summon, this.fireImmune,
                    this.canSpawnFarFromPlayer, this.immuneTo, this.dimensions, this.clientTrackingRange,
                    this.updateInterval, this.requiredFeatures, this.velocityUpdateSupplier, this.trackingRangeSupplier,
                    this.updateIntervalSupplier, this.customClientFactory, this.compartmentFactory, this.predicate);
        }
    }
}