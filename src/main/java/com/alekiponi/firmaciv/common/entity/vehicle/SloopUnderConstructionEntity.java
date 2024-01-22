package com.alekiponi.firmaciv.common.entity.vehicle;

import com.alekiponi.firmaciv.common.block.FirmacivBlocks;
import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.common.item.FirmacivItems;
import com.alekiponi.firmaciv.util.BoatVariant;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.registry.RegistryWood;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.Comparator;

public class SloopUnderConstructionEntity extends AbstractVehicleUnderConstruction {

    public final RegistryWood wood;
    public final Item lumber;
    public final Item stripped;
    public final Item planks;
    //TODO correct items
    public final Item mainsail;
    public final Item jibsail;
    public final Item anchor;
    public final Item rigging;

    public final int KEEL_ITEM_NUMBER = 8;

    public final int DECK_ITEM_NUMBER = 20;

    public final int BOWSPRIT_ITEM_NUMBER = 6;

    public final int MAST_ITEM_NUMBER = 12;

    public final int BOOM_ITEM_NUMER = 8;

    public final int MAINSAIL_ITEM_NUMBER = 1;

    public final int JIBSAIL_ITEM_NUMBER = 1;

    public final int STERN_RAILING_ITEM_NUMBER = 8;

    public final int BOW_RAILING_ITEM_NUMBER = 8;

    public final int ANCHOR_ITEM_NUMBER = 1;

    public final int RIGGING_ITEM_NUMBER = 8;

    public SloopUnderConstructionEntity(EntityType entityType, Level level, RegistryWood wood) {
        super(entityType, level);
        this.wood = wood;
        this.lumber = TFCItems.LUMBER.get((Wood) (wood)).get();
        this.stripped = wood.getBlock(Wood.BlockType.STRIPPED_LOG).get().asItem();
        this.planks = wood.getBlock(Wood.BlockType.PLANKS).get().asItem();
        //TODO correct items
        this.mainsail = FirmacivItems.MEDIUM_TRIANGULAR_SAIL.get();
        this.jibsail = FirmacivItems.SMALL_TRIANGULAR_SAIL.get();
        this.anchor = FirmacivItems.ANCHOR.get();
        this.rigging = FirmacivItems.ROPE_COIL.get();
    }

    private static final EntityDataAccessor<ItemStack> DATA_ID_KEEL = SynchedEntityData.defineId(SloopUnderConstructionEntity.class,
            EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ID_DECK = SynchedEntityData.defineId(SloopUnderConstructionEntity.class,
            EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ID_BOWSPRIT = SynchedEntityData.defineId(SloopUnderConstructionEntity.class,
            EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ID_MAST = SynchedEntityData.defineId(SloopUnderConstructionEntity.class,
            EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ID_BOOM = SynchedEntityData.defineId(SloopUnderConstructionEntity.class,
            EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ID_MAINSAIL = SynchedEntityData.defineId(SloopUnderConstructionEntity.class,
            EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ID_JIBSAIL = SynchedEntityData.defineId(SloopUnderConstructionEntity.class,
            EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ID_RAILINGS_STERN = SynchedEntityData.defineId(SloopUnderConstructionEntity.class,
            EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ID_RAILINGS_BOW = SynchedEntityData.defineId(SloopUnderConstructionEntity.class,
            EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ID_ANCHOR = SynchedEntityData.defineId(SloopUnderConstructionEntity.class,
            EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ID_RIGGING = SynchedEntityData.defineId(SloopUnderConstructionEntity.class,
            EntityDataSerializers.ITEM_STACK);

    private static final EntityDataAccessor<Integer> DATA_ID_CONSTRUCTION_STAGE = SynchedEntityData.defineId(SloopUnderConstructionEntity.class,
            EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_KEEL, ItemStack.EMPTY);
        this.entityData.define(DATA_ID_DECK, ItemStack.EMPTY);
        this.entityData.define(DATA_ID_BOWSPRIT, ItemStack.EMPTY);
        this.entityData.define(DATA_ID_MAST, ItemStack.EMPTY);
        this.entityData.define(DATA_ID_BOOM, ItemStack.EMPTY);
        this.entityData.define(DATA_ID_MAINSAIL, ItemStack.EMPTY);
        this.entityData.define(DATA_ID_JIBSAIL, ItemStack.EMPTY);
        this.entityData.define(DATA_ID_RAILINGS_STERN, ItemStack.EMPTY);
        this.entityData.define(DATA_ID_RAILINGS_BOW, ItemStack.EMPTY);
        this.entityData.define(DATA_ID_ANCHOR, ItemStack.EMPTY);
        this.entityData.define(DATA_ID_RIGGING, ItemStack.EMPTY);
        this.entityData.define(DATA_ID_CONSTRUCTION_STAGE, 0);
    }

    public ItemStack getKeel() {
        return this.entityData.get(DATA_ID_KEEL);
    }

    public void setKeel(final ItemStack itemStack) {
        this.entityData.set(DATA_ID_KEEL, itemStack.copy());
    }

    public ItemStack getDeck() {
        return this.entityData.get(DATA_ID_DECK);
    }

    public void setDeck(final ItemStack itemStack) {
        this.entityData.set(DATA_ID_DECK, itemStack.copy());
    }

    public ItemStack getBowsprit() {
        return this.entityData.get(DATA_ID_BOWSPRIT);
    }

    public void setBowsprit(final ItemStack itemStack) {
        this.entityData.set(DATA_ID_BOWSPRIT, itemStack.copy());
    }

    public ItemStack getMast() {
        return this.entityData.get(DATA_ID_MAST);
    }

    public void setMast(final ItemStack itemStack) {
        this.entityData.set(DATA_ID_MAST, itemStack.copy());
    }

    public ItemStack getBoom() {
        return this.entityData.get(DATA_ID_BOOM);
    }

    public void setBoom(final ItemStack itemStack) {
        this.entityData.set(DATA_ID_BOOM, itemStack.copy());
    }

    public ItemStack getMainsail() {
        return this.entityData.get(DATA_ID_MAINSAIL);
    }

    public void setMainsail(final ItemStack itemStack) {
        this.entityData.set(DATA_ID_MAINSAIL, itemStack.copy());
    }

    public ItemStack getJibsail() {
        return this.entityData.get(DATA_ID_JIBSAIL);
    }

    public void setJibsail(final ItemStack itemStack) {
        this.entityData.set(DATA_ID_JIBSAIL, itemStack.copy());
    }

    public ItemStack getRailingsStern() {
        return this.entityData.get(DATA_ID_RAILINGS_STERN);
    }

    public void setRailingsStern(final ItemStack itemStack) {
        this.entityData.set(DATA_ID_RAILINGS_STERN, itemStack.copy());
    }

    public ItemStack getRailingsBow() {
        return this.entityData.get(DATA_ID_RAILINGS_BOW);
    }

    public void setRailingsBow(final ItemStack itemStack) {
        this.entityData.set(DATA_ID_RAILINGS_BOW, itemStack.copy());
    }

    public ItemStack getAnchor() {
        return this.entityData.get(DATA_ID_ANCHOR);
    }

    public void setAnchor(final ItemStack itemStack) {
        this.entityData.set(DATA_ID_ANCHOR, itemStack.copy());
    }

    public ItemStack getRigging() {
        return this.entityData.get(DATA_ID_RIGGING);
    }

    public void setRigging(final ItemStack itemStack) {
        this.entityData.set(DATA_ID_RIGGING, itemStack.copy());
    }

    public ConstructionState getConstructionStage() {
        return ConstructionState.ANCHOR.getByOrdinal(this.entityData.get(DATA_ID_CONSTRUCTION_STAGE));
    }

    public void setConstructionStage(ConstructionState stage) {
        this.entityData.set(DATA_ID_CONSTRUCTION_STAGE, stage.ordinal());
    }

    @Override
    protected void readAdditionalSaveData(final CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setKeel(ItemStack.of(compoundTag.getCompound("keel")));
        this.setDeck(ItemStack.of(compoundTag.getCompound("deck")));
        this.setBowsprit(ItemStack.of(compoundTag.getCompound("bowsprit")));
        this.setMast(ItemStack.of(compoundTag.getCompound("mast")));
        this.setBoom(ItemStack.of(compoundTag.getCompound("boom")));
        this.setMainsail(ItemStack.of(compoundTag.getCompound("mainsail")));
        this.setJibsail(ItemStack.of(compoundTag.getCompound("jibsail")));
        this.setRailingsBow(ItemStack.of(compoundTag.getCompound("railingsBow")));
        this.setRailingsStern(ItemStack.of(compoundTag.getCompound("railingStern")));
        this.setAnchor(ItemStack.of(compoundTag.getCompound("anchor")));
        this.setRigging(ItemStack.of(compoundTag.getCompound("rigging")));
        this.setConstructionStage(ConstructionState.getByOrdinal(compoundTag.getInt("stage")));
    }

    @Override
    protected void addAdditionalSaveData(final CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.put("keel", this.getKeel().save(new CompoundTag()));
        compoundTag.put("deck", this.getDeck().save(new CompoundTag()));
        compoundTag.put("bowsprit", this.getBowsprit().save(new CompoundTag()));
        compoundTag.put("mast", this.getMast().save(new CompoundTag()));
        compoundTag.put("boom", this.getBoom().save(new CompoundTag()));
        compoundTag.put("mainsail", this.getMainsail().save(new CompoundTag()));
        compoundTag.put("jibsail", this.getJibsail().save(new CompoundTag()));
        compoundTag.put("railingsBow", this.getRailingsBow().save(new CompoundTag()));
        compoundTag.put("railingsStern", this.getRailingsStern().save(new CompoundTag()));
        compoundTag.put("anchor", this.getAnchor().save(new CompoundTag()));
        compoundTag.put("rigging", this.getRigging().save(new CompoundTag()));
        compoundTag.putInt("stage", this.getConstructionStage().ordinal());
    }

    public static enum ConstructionState {
        KEEL,
        DECK,
        BOWSPRIT,
        MAST,
        BOOM,
        MAINSAIL,
        JIBSAIl,
        RAILINGS_STERN,
        RAILINGS_BOW,
        ANCHOR,
        RIGGING,
        COMPLETE;

        private static final ConstructionState[] BY_ORDINAL = Arrays.stream(values())
                .sorted(Comparator.comparingInt(ConstructionState::ordinal)).toArray(ConstructionState[]::new);

        public static ConstructionState getByOrdinal(int ordinal) {
            return BY_ORDINAL[ordinal % BY_ORDINAL.length];
        }
    }

    protected Vec3 positionRiderByIndex(int index) {
        float localX = 0.0F;
        float localZ = 0.0F;
        float localY = (float) ((this.isRemoved() ? (double) 0.01F : this.getPassengersRidingOffset()));
        ConstructionState stage = this.getConstructionStage();
        switch (stage) {
            case KEEL -> {
                localX = 0.0f;
                localZ = 0.0f;
                localY += -0.0f;
            }
            case DECK -> {
                localX = -1.0f;
                localZ = 0.0f;
                localY += 0.5f;
            }
            case BOWSPRIT -> {
                localX = 4.0f;
                localZ = 0.0f;
                localY += 0.7f;
            }
            case MAST -> {
                localX = 2.0f;
                localZ = 0.0f;
                localY += 2.0f;
            }
            case BOOM -> {
                localX = 1.8f;
                localZ = 0.0f;
                localY += 2.25f;
            }
            case MAINSAIL -> {
                localX = 1.5f;
                localZ = 0.0f;
                localY += 2.5f;
            }
            case JIBSAIl -> {
                localX = 3.0f;
                localZ = 0.0f;
                localY += 0.5f;
            }
            case RAILINGS_STERN -> {
                localX = -3.0f;
                localZ = 0.0f;
                localY += 0.8f;
            }
            case RAILINGS_BOW -> {
                localX = 4.0f;
                localZ = 0.0f;
                localY += 0.8f;
            }
            case ANCHOR -> {
                localZ = -1.34f;
                localX = 2.52f;
                localY += 0.7f;
            }
            case RIGGING -> {
                localX = 1.5f;
                localZ = 0.0f;
                localY += 1.5f;
            }
        }
        return new Vec3(localX, localY, localZ);
    }

    public Item getCurrentRequiredItem() {
        ConstructionState stage = this.getConstructionStage();
        switch (stage) {
            case KEEL, MAST, BOOM, BOWSPRIT -> {
                return this.stripped;
            }
            case DECK -> {
                return this.planks;
            }
            case MAINSAIL -> {
                return this.mainsail;
            }
            case JIBSAIl -> {
                return this.jibsail;
            }
            case RAILINGS_STERN, RAILINGS_BOW -> {
                return this.lumber;
            }
            case ANCHOR -> {
                return this.anchor;
            }
            case RIGGING -> {
                return this.rigging;
            }
        }
        return null;
    }

    public int getNumberItemsLeft() {
        ConstructionState stage = this.getConstructionStage();
        switch (stage) {
            case KEEL -> {
                return KEEL_ITEM_NUMBER - getKeel().getCount();
            }
            case DECK -> {
                return DECK_ITEM_NUMBER - getDeck().getCount();
            }
            case BOWSPRIT -> {
                return BOWSPRIT_ITEM_NUMBER - getBowsprit().getCount();
            }
            case MAST -> {
                return MAST_ITEM_NUMBER - getMast().getCount();
            }
            case BOOM -> {
                return BOOM_ITEM_NUMER - getBoom().getCount();
            }
            case MAINSAIL -> {
                return MAINSAIL_ITEM_NUMBER - getMainsail().getCount();
            }
            case JIBSAIl -> {
                return JIBSAIL_ITEM_NUMBER - getJibsail().getCount();
            }
            case RAILINGS_STERN -> {
                return STERN_RAILING_ITEM_NUMBER - getRailingsStern().getCount();
            }
            case RAILINGS_BOW -> {
                return BOW_RAILING_ITEM_NUMBER - getRailingsBow().getCount();
            }
            case ANCHOR -> {
                return ANCHOR_ITEM_NUMBER - getAnchor().getCount();
            }
            case RIGGING -> {
                return RIGGING_ITEM_NUMBER - getRigging().getCount();
            }
        }
        return -1;
    }

    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        return InteractionResult.PASS;
    }

    @Override
    public void interactFromConstructionEntity(Player player, InteractionHand hand) {
        final ItemStack stack = player.getItemInHand(hand);

        ConstructionState stage = this.getConstructionStage();
        BlockPos thisPos = this.blockPosition();
        Direction thisDir = this.getDirection();
        thisPos = thisPos.relative(thisDir, 4);
        thisPos = thisPos.relative(thisDir.getCounterClockWise(), 2);
        switch (stage) {
            case KEEL -> {
                if (stack.is(stripped)) {
                    this.setKeel(new ItemStack(stack.split(1).getItem(), this.getKeel().getCount() + 1));
                    this.playSound(SoundEvents.WOOD_PLACE);
                    if (this.getKeel().getCount() >= KEEL_ITEM_NUMBER) {
                        this.setConstructionStage(ConstructionState.DECK);
                        for (int x = 0; x < 4; x++) {
                            for (int y = 0; y < 7; y++) {
                                this.playSound(SoundEvents.WOOD_BREAK);
                                this.level().addDestroyBlockEffect(thisPos.relative(thisDir.getOpposite(), y).relative(thisDir.getClockWise(), x), this.wood.getBlock(Wood.BlockType.PLANKS).get().defaultBlockState());
                            }
                        }
                    }
                }
            }
            case DECK -> {
                if (stack.is(planks)) {
                    this.setDeck(new ItemStack(stack.split(1).getItem(), this.getDeck().getCount() + 1));
                    this.playSound(SoundEvents.WOOD_PLACE);
                    if (this.getDeck().getCount() >= DECK_ITEM_NUMBER) {
                        this.setConstructionStage(ConstructionState.BOWSPRIT);
                        for (int x = 0; x < 4; x++) {
                            for (int y = 0; y < 7; y++) {
                                this.playSound(SoundEvents.WOOD_BREAK);
                                this.level().addDestroyBlockEffect(thisPos.relative(thisDir.getOpposite(), y).relative(thisDir.getClockWise(), x), this.wood.getBlock(Wood.BlockType.PLANKS).get().defaultBlockState());
                            }
                        }
                    }
                }
            }
            case BOWSPRIT -> {
                if (stack.is(stripped)) {
                    this.setBowsprit(new ItemStack(stack.split(1).getItem(), this.getBowsprit().getCount() + 1));
                    this.playSound(SoundEvents.WOOD_PLACE);
                    if (this.getBowsprit().getCount() >= BOWSPRIT_ITEM_NUMBER) {
                        this.setConstructionStage(ConstructionState.MAST);
                        for (int x = 0; x < 4; x++) {
                            for (int y = 0; y < 7; y++) {
                                this.playSound(SoundEvents.WOOD_BREAK);
                                this.level().addDestroyBlockEffect(thisPos.relative(thisDir.getOpposite(), y).relative(thisDir.getClockWise(), x), this.wood.getBlock(Wood.BlockType.PLANKS).get().defaultBlockState());
                            }
                        }
                    }
                }
            }
            case MAST -> {
                if (stack.is(stripped)) {
                    this.setMast(new ItemStack(stack.split(1).getItem(), this.getMast().getCount() + 1));
                    this.playSound(SoundEvents.WOOD_PLACE);
                    if (this.getMast().getCount() >= MAST_ITEM_NUMBER) {
                        this.setConstructionStage(ConstructionState.BOOM);
                        for (int x = 0; x < 4; x++) {
                            for (int y = 0; y < 7; y++) {
                                this.playSound(SoundEvents.WOOD_BREAK);
                                this.level().addDestroyBlockEffect(thisPos.relative(thisDir.getOpposite(), y).relative(thisDir.getClockWise(), x), this.wood.getBlock(Wood.BlockType.PLANKS).get().defaultBlockState());
                            }
                        }
                    }
                }
            }
            case BOOM -> {
                if (stack.is(stripped)) {
                    this.setBoom(new ItemStack(stack.split(1).getItem(), this.getBoom().getCount() + 1));
                    this.playSound(SoundEvents.WOOD_PLACE);
                    if (this.getBoom().getCount() >= BOOM_ITEM_NUMER) {
                        this.setConstructionStage(ConstructionState.MAINSAIL);
                        for (int x = 0; x < 4; x++) {
                            for (int y = 0; y < 7; y++) {
                                this.playSound(SoundEvents.WOOD_BREAK);
                                this.level().addDestroyBlockEffect(thisPos.relative(thisDir.getOpposite(), y).relative(thisDir.getClockWise(), x), this.wood.getBlock(Wood.BlockType.PLANKS).get().defaultBlockState());
                            }
                        }
                    }
                }
            }
            case MAINSAIL -> {
                if (stack.is(mainsail)) {
                    this.setMainsail(new ItemStack(stack.split(1).getItem(), this.getMainsail().getCount() + 1));
                    this.playSound(SoundEvents.WOOL_PLACE);
                    if (this.getMainsail().getCount() >= MAINSAIL_ITEM_NUMBER) {
                        this.setConstructionStage(ConstructionState.JIBSAIl);
                        for (int x = 0; x < 4; x++) {
                            for (int y = 0; y < 7; y++) {
                                this.playSound(SoundEvents.WOOL_BREAK);
                                this.level().addDestroyBlockEffect(thisPos.relative(thisDir.getOpposite(), y).relative(thisDir.getClockWise(), x), Blocks.WHITE_WOOL.defaultBlockState());
                            }
                        }
                    }
                }
            }
            case JIBSAIl -> {
                if (stack.is(jibsail)) {
                    this.setJibsail(new ItemStack(stack.split(1).getItem(), this.getJibsail().getCount() + 1));
                    this.playSound(SoundEvents.WOOL_PLACE);
                    if (this.getJibsail().getCount() >= JIBSAIL_ITEM_NUMBER) {
                        this.setConstructionStage(ConstructionState.RAILINGS_STERN);
                        for (int x = 0; x < 4; x++) {
                            for (int y = 0; y < 7; y++) {
                                this.playSound(SoundEvents.WOOL_BREAK);
                                this.level().addDestroyBlockEffect(thisPos.relative(thisDir.getOpposite(), y).relative(thisDir.getClockWise(), x), Blocks.WHITE_WOOL.defaultBlockState());

                            }
                        }
                    }
                }
            }
            case RAILINGS_STERN -> {
                if (stack.is(lumber)) {
                    this.setRailingsStern(new ItemStack(stack.split(1).getItem(), this.getRailingsStern().getCount() + 1));
                    this.playSound(SoundEvents.WOOD_PLACE);
                    if (this.getRailingsStern().getCount() >= STERN_RAILING_ITEM_NUMBER) {
                        this.setConstructionStage(ConstructionState.RAILINGS_BOW);
                        for (int x = 0; x < 4; x++) {
                            for (int y = 0; y < 7; y++) {
                                this.playSound(SoundEvents.WOOD_BREAK);
                                this.level().addDestroyBlockEffect(thisPos.relative(thisDir.getOpposite(), y).relative(thisDir.getClockWise(), x), this.wood.getBlock(Wood.BlockType.PLANKS).get().defaultBlockState());
                            }
                        }
                    }
                }
            }
            case RAILINGS_BOW -> {
                if (stack.is(lumber)) {
                    this.setRailingsBow(new ItemStack(stack.split(1).getItem(), this.getRailingsBow().getCount() + 1));
                    this.playSound(SoundEvents.WOOD_PLACE);
                    if (this.getRailingsBow().getCount() >= BOW_RAILING_ITEM_NUMBER) {
                        this.setConstructionStage(ConstructionState.ANCHOR);
                        for (int x = 0; x < 4; x++) {
                            for (int y = 0; y < 7; y++) {
                                this.playSound(SoundEvents.WOOD_BREAK);
                                this.level().addDestroyBlockEffect(thisPos.relative(thisDir.getOpposite(), y).relative(thisDir.getClockWise(), x), this.wood.getBlock(Wood.BlockType.PLANKS).get().defaultBlockState());
                            }
                        }
                    }
                }
            }
            case ANCHOR -> {
                if (stack.is(anchor)) {
                    this.setAnchor(new ItemStack(stack.split(1).getItem(), this.getAnchor().getCount() + 1));
                    this.playSound(SoundEvents.METAL_PLACE);
                    if (this.getAnchor().getCount() >= ANCHOR_ITEM_NUMBER) {
                        this.setConstructionStage(ConstructionState.RIGGING);
                        for (int x = 0; x < 4; x++) {
                            for (int y = 0; y < 7; y++) {
                                this.playSound(SoundEvents.WOOD_BREAK);
                                this.level().addDestroyBlockEffect(thisPos.relative(thisDir.getOpposite(), y).relative(thisDir.getClockWise(), x), this.wood.getBlock(Wood.BlockType.PLANKS).get().defaultBlockState());
                            }
                        }
                    }
                }
            }
            case RIGGING -> {
                if (stack.is(rigging)) {
                    this.setRigging(new ItemStack(stack.split(1).getItem(), this.getRigging().getCount() + 1));
                    this.playSound(SoundEvents.LEASH_KNOT_PLACE);
                    if (this.getRigging().getCount() >= RIGGING_ITEM_NUMBER) {
                        SloopEntity sloop = FirmacivEntities.SLOOPS.get(wood).get().create(this.level());
                        sloop.setYRot(this.getYRot());
                        sloop.setPos(this.getPosition(0));
                        this.level().addFreshEntity(sloop);

                        for (int x = 0; x < 4; x++) {
                            for (int y = 0; y < 7; y++) {
                                for (int z = 0; z < 11; z++) {
                                    this.playSound(SoundEvents.WOOD_BREAK);
                                    this.level().addDestroyBlockEffect(thisPos.relative(thisDir.getOpposite(), y).relative(thisDir.getClockWise(), x).relative(Direction.UP, z), this.wood.getBlock(Wood.BlockType.PLANKS).get().defaultBlockState());
                                    this.level().addDestroyBlockEffect(thisPos.relative(thisDir.getOpposite(), y).relative(thisDir.getClockWise(), x).relative(Direction.UP, z), FirmacivBlocks.BOAT_FRAME_ANGLED.get().defaultBlockState());
                                }
                            }
                        }
                        this.kill();
                    }
                }
            }
        }
    }

    @Override
    public void tick() {
        if (this.getDamage() > this.getDamageThreshold()) {
            this.kill();
            //TODO drop all materials
        }
        super.tick();

    }

    @Override
    public int[] getCleatIndices() {
        return new int[0];
    }

    @Override
    public BoatVariant getVariant() {
        return null;
    }

    protected float getMomentumSubtractor() {
        return 0;
    }

    @Override
    public int[] getColliderIndices() {
        return new int[0];
    }

    @Override
    public int[] getCanAddOnlyBlocksIndices() {
        return new int[0];
    }

    @Override
    public int getCompartmentRotation(int i) {
        return 0;
    }

    @Override
    public float getPassengerSizeLimit() {
        return 0;
    }

    @Override
    public int[][] getCompartmentRotationsArray() {
        return new int[0][];
    }

    @Override
    protected void tickCleatInput() {

    }

    @Override
    public float getDamageThreshold() {
        return 20;
    }

    @Override
    public float getDamageRecovery() {
        return 0;
    }
}
