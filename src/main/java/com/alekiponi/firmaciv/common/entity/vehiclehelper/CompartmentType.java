package com.alekiponi.firmaciv.common.entity.vehiclehelper;

import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.AbstractCompartmentEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.vanilla.BarrelCompartmentEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.TFCChestCompartmentEntity;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.WorkbenchCompartmentEntity;
import com.alekiponi.firmaciv.util.FirmacivTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class CompartmentType<T extends AbstractCompartmentEntity> {
    private static final List<CompartmentType<? extends AbstractCompartmentEntity>> COMPARTMENT_TYPES = new ArrayList<>();

    public static final CompartmentType<WorkbenchCompartmentEntity> WORKBENCH = register(
            FirmacivEntities.WORKBENCH_COMPARTMENT_ENTITY.get(), WorkbenchCompartmentEntity::new,
            itemStack -> itemStack.is(FirmacivTags.Items.WORKBENCHES));

    public static final CompartmentType<TFCChestCompartmentEntity> TFC_CHEST = register(
            FirmacivEntities.TFC_CHEST_COMPARTMENT_ENTITY.get(), TFCChestCompartmentEntity::new,
            itemStack -> itemStack.is(FirmacivTags.Items.CHESTS));

    public static final CompartmentType<BarrelCompartmentEntity> BARREL = register(
            FirmacivEntities.BARREL_COMPARTMENT_ENTITY.get(), BarrelCompartmentEntity::new,
            itemStack -> itemStack.is(Blocks.BARREL.asItem()));

    private final EntityType<T> entityType;
    private final CompartmentFactory<T> factory;
    private final Predicate<ItemStack> predicate;

    private CompartmentType(final EntityType<T> entityType, final CompartmentFactory<T> factory,
            final Predicate<ItemStack> predicate) {
        this.entityType = entityType;
        this.factory = factory;
        this.predicate = predicate;
    }

    /**
     * Registers a Compartment Type. These are used to dynamically find a compartment for an arbitrary item stack
     *
     * @param factory   The entity's factory
     * @param predicate The predicate for checking when an item stack is applicable
     */
    public static <T extends AbstractCompartmentEntity> CompartmentType<T> register(final EntityType<T> entityType,
            final CompartmentFactory<T> factory, Predicate<ItemStack> predicate) {
        final CompartmentType<T> type = new CompartmentType<>(entityType, factory, predicate);
        COMPARTMENT_TYPES.add(type);
        return type;
    }

    /**
     * Gets an applicable compartment for an item stack
     *
     * @param itemStack The item stack
     * @return An applicable compartment for the given item stack. Returns the first compartment found,
     * this means registry order can effect which is chosen
     */
    public static Optional<CompartmentType<?>> fromStack(final ItemStack itemStack) {
        for (final CompartmentType<? extends AbstractCompartmentEntity> compartmentType : COMPARTMENT_TYPES) {
            if (compartmentType.predicate.test(itemStack)) return Optional.of(compartmentType);
        }

        return Optional.empty();
    }

    public T create(final Level level, final ItemStack itemStack) {
        return this.factory.create(this.entityType, level, itemStack);
    }

    public interface CompartmentFactory<T extends AbstractCompartmentEntity> {
        T create(final EntityType<T> entityType, final Level level, final ItemStack itemStack);
    }
}