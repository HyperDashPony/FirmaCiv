package com.alekiponi.firmaciv.common.entity.vehicle;

import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.util.BoatVariant;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.registry.RegistryWood;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.Comparator;

public class SloopUnderConstructionEntity extends AbstractVehicleUnderConstruction {

    public final RegistryWood wood;

    public SloopUnderConstructionEntity(EntityType entityType, Level level, RegistryWood wood) {
        super(entityType, level);
        this.wood = wood;
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
    public ConstructionState getConstructionStage(){return ConstructionState.ANCHOR.getByOrdinal(this.entityData.get(DATA_ID_CONSTRUCTION_STAGE)); }
    public void setConstructionStage(ConstructionState stage){this.entityData.set(DATA_ID_CONSTRUCTION_STAGE,stage.ordinal()); }

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
    }
    public enum ConstructionState{
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
        public ConstructionState getByOrdinal(int ordinal ){
            return BY_ORDINAL[ordinal % BY_ORDINAL.length];
        }
    }

    protected Vec3 positionRiderByIndex(int index){
        float localX = 0.0F;
        float localZ = 0.0F;
        float localY = (float) ((this.isRemoved() ? (double) 0.01F : this.getPassengersRidingOffset()));
        localX = 0.0f;
        localZ = 0.0f;
        localY += 2.0f;
        return new Vec3(localX, localY, localZ);
    }

    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        return InteractionResult.PASS;
    }

    @Override
    public void interactFromConstructionEntity(Player player, InteractionHand hand) {
        final ItemStack stack = player.getItemInHand(hand);
        Item lumber = TFCItems.LUMBER.get((Wood)(wood)).get();
        Item stripped = wood.getBlock(Wood.BlockType.STRIPPED_LOG).get().asItem();
        Item planks = wood.getBlock(Wood.BlockType.PLANKS).get().asItem();
        //TODO correct items
        Item sail = TFCItems.WOOL_CLOTH.get();
        Item anchor = Items.IRON_INGOT;
        Item rigging = Items.LEAD;
        ConstructionState stage = this.getConstructionStage();
        switch (stage) {
            case KEEL -> {
                if(stack.is(stripped)){
                    this.setKeel(new ItemStack(stack.split(1).getItem(), this.getKeel().getCount()+1));
                    this.playSound(SoundEvents.WOOD_PLACE);
                    if(this.getKeel().getCount() >= 8){
                        this.setConstructionStage(ConstructionState.DECK);
                    }
                }
            }
            case DECK -> {
                if(stack.is(planks)){
                    this.setDeck(new ItemStack(stack.split(1).getItem(), this.getDeck().getCount()+1));
                    this.playSound(SoundEvents.WOOD_PLACE);
                    if(this.getDeck().getCount() >= 20){
                        this.setConstructionStage(ConstructionState.BOWSPRIT);
                    }
                }
            }
            case BOWSPRIT -> {
                if(stack.is(stripped)){
                    this.setBowsprit(new ItemStack(stack.split(1).getItem(), this.getBowsprit().getCount()+1));
                    this.playSound(SoundEvents.WOOD_PLACE);
                    if(this.getBowsprit().getCount() >= 4){
                        this.setConstructionStage(ConstructionState.MAST);
                    }
                }
            }
            case MAST -> {
                if(stack.is(stripped)){
                    this.setMast(new ItemStack(stack.split(1).getItem(), this.getMast().getCount()+1));
                    this.playSound(SoundEvents.WOOD_PLACE);
                    if(this.getMast().getCount() >= 12){
                        this.setConstructionStage(ConstructionState.BOOM);
                    }
                }
            }
            case BOOM -> {
                if(stack.is(stripped)){
                    this.setBoom(new ItemStack(stack.split(1).getItem(), this.getBoom().getCount()+1));
                    this.playSound(SoundEvents.WOOD_PLACE);
                    if(this.getBoom().getCount() >= 8){
                        this.setConstructionStage(ConstructionState.MAINSAIL);
                    }
                }
            }
            case MAINSAIL -> {
                if(stack.is(sail)){
                    this.setMainsail(new ItemStack(stack.split(1).getItem(), this.getMainsail().getCount()+1));
                    this.playSound(SoundEvents.WOOL_PLACE);
                    if(this.getMainsail().getCount() >= 1){
                        this.setConstructionStage(ConstructionState.JIBSAIl);
                    }
                }
            }
            case JIBSAIl -> {
                if(stack.is(sail)){
                    this.setJibsail(new ItemStack(stack.split(1).getItem(), this.getJibsail().getCount()+1));
                    this.playSound(SoundEvents.WOOL_PLACE);
                    if(this.getJibsail().getCount() >= 1){
                        this.setConstructionStage(ConstructionState.RAILINGS_STERN);
                    }
                }
            }
            case RAILINGS_STERN -> {
                if(stack.is(lumber)){
                    this.setRailingsStern(new ItemStack(stack.split(1).getItem(), this.getRailingsStern().getCount()+1));
                    this.playSound(SoundEvents.WOOD_PLACE);
                    if(this.getRailingsStern().getCount() >= 8){
                        this.setConstructionStage(ConstructionState.RAILINGS_BOW);
                    }
                }
            }
            case RAILINGS_BOW -> {
                if(stack.is(lumber)){
                    this.setRailingsBow(new ItemStack(stack.split(1).getItem(), this.getRailingsBow().getCount()+1));
                    this.playSound(SoundEvents.WOOD_PLACE);
                    if(this.getRailingsBow().getCount() >= 8){
                        this.setConstructionStage(ConstructionState.ANCHOR);
                    }
                }
            }
            case ANCHOR -> {
                if(stack.is(anchor)){
                    this.setAnchor(new ItemStack(stack.split(1).getItem(), this.getAnchor().getCount()+1));
                    this.playSound(SoundEvents.METAL_PLACE);
                    if(this.getAnchor().getCount() >= 1){
                        this.setConstructionStage(ConstructionState.RIGGING);
                    }
                }
            }
            case RIGGING -> {
                if(stack.is(rigging)){
                    this.setRigging(new ItemStack(stack.split(1).getItem(), this.getRigging().getCount()+1));
                    this.playSound(SoundEvents.LEASH_KNOT_PLACE);
                    if(this.getRigging().getCount() >= 8){
                        this.setConstructionStage(ConstructionState.COMPLETE);
                    }
                }
            }
        }
        if(stage == ConstructionState.COMPLETE){
            SloopEntity sloop = FirmacivEntities.SLOOPS.get(wood).get().create(this.level());
            sloop.setYRot(this.getYRot());
            sloop.setPos(this.getPosition(0));
            this.level().addFreshEntity(sloop);
            this.kill();
        }
    }

    @Override
    public void tick(){
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
