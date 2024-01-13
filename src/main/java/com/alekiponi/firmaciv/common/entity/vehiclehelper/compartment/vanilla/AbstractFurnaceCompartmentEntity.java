package com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.vanilla;

import com.alekiponi.firmaciv.common.entity.vehiclehelper.CompartmentType;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.ContainerCompartmentEntity;
import com.alekiponi.firmaciv.common.menu.AbstractFurnaceCompartmentMenu;
import com.alekiponi.firmaciv.common.menu.BlastFurnaceCompartmentMenu;
import com.alekiponi.firmaciv.common.menu.FurnaceCompartmentMenu;
import com.alekiponi.firmaciv.common.menu.SmokerCompartmentMenu;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity.*;

/**
 * This compartment entity mimics vanillas {@link AbstractFurnaceBlockEntity}. If your BE extends from that class you'll
 * want to extend from this for your compartment. We also provide {@link FurnaceCompartmentMenu},
 * {@link BlastFurnaceCompartmentMenu} and {@link SmokerCompartmentMenu}. If you extend or use the vanilla menu
 * counterparts you'll need to use or extend our menus as well
 */
public abstract class AbstractFurnaceCompartmentEntity extends ContainerCompartmentEntity implements WorldlyContainer, RecipeHolder, StackedContentsCompatible {

    protected static final int SLOT_INPUT = 0;
    protected static final int SLOT_FUEL = 1;
    protected static final int SLOT_RESULT = 2;
    private static final int[] SLOTS_FOR_UP = new int[]{SLOT_INPUT};
    private static final int[] SLOTS_FOR_DOWN = new int[]{SLOT_RESULT, SLOT_FUEL};
    private static final int[] SLOTS_FOR_SIDES = new int[]{SLOT_FUEL};
    private final RecipeType<? extends AbstractCookingRecipe> recipeType;
    private final RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> quickCheck;
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    protected int litTime;
    protected int litDuration;
    protected int cookingProgress;
    protected int cookingTotalTime;
    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(final int dataType) {
            return switch (dataType) {
                case DATA_LIT_TIME -> AbstractFurnaceCompartmentEntity.this.litTime;
                case DATA_LIT_DURATION -> AbstractFurnaceCompartmentEntity.this.litDuration;
                case DATA_COOKING_PROGRESS -> AbstractFurnaceCompartmentEntity.this.cookingProgress;
                case DATA_COOKING_TOTAL_TIME -> AbstractFurnaceCompartmentEntity.this.cookingTotalTime;
                default -> 0;
            };
        }

        @Override
        public void set(final int dataType, final int dataValue) {
            switch (dataType) {
                case DATA_LIT_TIME:
                    AbstractFurnaceCompartmentEntity.this.litTime = dataValue;
                    break;
                case DATA_LIT_DURATION:
                    AbstractFurnaceCompartmentEntity.this.litDuration = dataValue;
                    break;
                case DATA_COOKING_PROGRESS:
                    AbstractFurnaceCompartmentEntity.this.cookingProgress = dataValue;
                    break;
                case DATA_COOKING_TOTAL_TIME:
                    AbstractFurnaceCompartmentEntity.this.cookingTotalTime = dataValue;
            }
        }

        @Override
        public int getCount() {
            return NUM_DATA_VALUES;
        }
    };

    public AbstractFurnaceCompartmentEntity(final EntityType<? extends AbstractFurnaceCompartmentEntity> entityType,
            final Level level, final RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(entityType, level, 3);
        this.quickCheck = RecipeManager.createCheck(recipeType);
        this.recipeType = recipeType;
    }

    public AbstractFurnaceCompartmentEntity(
            final CompartmentType<? extends AbstractFurnaceCompartmentEntity> entityType, final Level level,
            final RecipeType<? extends AbstractCookingRecipe> recipeType, final ItemStack itemStack) {
        this(entityType, level, recipeType);
        if (itemStack.getItem() instanceof BlockItem blockItem) {
            this.setDisplayBlockState(blockItem.getBlock().defaultBlockState());
        }
    }

    private static void createExperience(final ServerLevel level, final Vec3 vec3, final int recipeIndex,
            final float experience) {
        int i = Mth.floor(recipeIndex * experience);
        float f = Mth.frac(recipeIndex * experience);
        if (f != 0 && Math.random() < f) {
            ++i;
        }

        ExperienceOrb.award(level, vec3, i);
    }

    private static int getTotalCookTime(final Level pLevel, final AbstractFurnaceCompartmentEntity furnaceCompartment) {
        return furnaceCompartment.quickCheck.getRecipeFor(furnaceCompartment, pLevel)
                .map(AbstractCookingRecipe::getCookingTime).orElse(BURN_TIME_STANDARD);
    }

    @Override
    public void tick() {
        super.tick();
        final boolean isLitStart = this.isLit();
        if (this.isLit()) {
            --this.litTime;
        }

        if (this.level().isClientSide()) return;

        final ItemStack fuelStack = this.getItem(SLOT_FUEL);
        final boolean inputSlotEmpty = !this.getItem(SLOT_INPUT).isEmpty();
        final boolean fuelSlotEmpty = !fuelStack.isEmpty();
        if (this.isLit() || fuelSlotEmpty && inputSlotEmpty) {
            AbstractCookingRecipe recipe;
            if (inputSlotEmpty) {
                recipe = this.quickCheck.getRecipeFor(this, this.level()).orElse(null);
            } else {
                recipe = null;
            }

            final int maxStackSize = this.getMaxStackSize();
            if (!this.isLit() && this.canBurn(this.level().registryAccess(), recipe, this.getItemStacks(),
                    maxStackSize)) {
                this.litTime = this.getBurnDuration(fuelStack);
                this.litDuration = this.litTime;
                if (this.isLit()) {
                    if (fuelStack.hasCraftingRemainingItem()) {
                        this.setItem(SLOT_FUEL, fuelStack.getCraftingRemainingItem());
                    } else if (fuelSlotEmpty) {
                        fuelStack.shrink(1);
                        if (fuelStack.isEmpty()) this.setItem(SLOT_FUEL, fuelStack.getCraftingRemainingItem());
                    }
                }
            }

            if (this.isLit() && this.canBurn(this.level().registryAccess(), recipe, this.getItemStacks(),
                    maxStackSize)) {
                ++this.cookingProgress;
                if (this.cookingProgress == this.cookingTotalTime) {
                    this.cookingProgress = 0;
                    this.cookingTotalTime = getTotalCookTime(this.level(), this);
                    if (this.burn(this.level().registryAccess(), recipe, this.getItemStacks(), maxStackSize)) {
                        this.setRecipeUsed(recipe);
                    }
                }
            } else {
                this.cookingProgress = 0;
            }
        } else if (!this.isLit() && this.cookingProgress > 0) {
            this.cookingProgress = Mth.clamp(this.cookingProgress - BURN_COOL_SPEED, 0, this.cookingTotalTime);
        }

        // Switch blockstate if we changed our lit status
        if (isLitStart != this.isLit()) {
            this.setDisplayBlockState(this.getDisplayBlockState().setValue(AbstractFurnaceBlock.LIT, this.isLit()));
        }
    }

    @Override
    public void fillStackedContents(final StackedContents stackedContents) {
        for (final ItemStack itemstack : this.getItemStacks()) {
            stackedContents.accountStack(itemstack);
        }
    }

    @Override
    public void remove(final RemovalReason removalReason) {
        super.remove(removalReason);
    }

    @Override
    @Nullable
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void setRecipeUsed(final @Nullable Recipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation resourceLocation = recipe.getId();
            this.recipesUsed.addTo(resourceLocation, 1);
        }
    }

    private boolean burn(final RegistryAccess registryAccess, final @Nullable AbstractCookingRecipe cookingRecipe,
            final NonNullList<ItemStack> itemStacks, final int maxStackSize) {
        if (cookingRecipe == null || !this.canBurn(registryAccess, cookingRecipe, itemStacks, maxStackSize)) {
            return false;
        }

        final ItemStack inputStack = itemStacks.get(SLOT_INPUT);
        final ItemStack recipeOutput = cookingRecipe.assemble(this, registryAccess);
        final ItemStack outputSlot = itemStacks.get(SLOT_RESULT);
        if (outputSlot.isEmpty()) {
            itemStacks.set(SLOT_RESULT, recipeOutput.copy());
        } else if (outputSlot.is(recipeOutput.getItem())) {
            outputSlot.grow(recipeOutput.getCount());
        }

        if (inputStack.is(Blocks.WET_SPONGE.asItem()) && !itemStacks.get(SLOT_FUEL).isEmpty() && itemStacks.get(
                SLOT_FUEL).is(Items.BUCKET)) {
            itemStacks.set(SLOT_FUEL, Items.WATER_BUCKET.getDefaultInstance());
        }

        inputStack.shrink(1);
        return true;
    }

    protected int getBurnDuration(final ItemStack itemStack) {
        return ForgeHooks.getBurnTime(itemStack, this.recipeType);
    }

    private boolean canBurn(final RegistryAccess registryAccess, final @Nullable AbstractCookingRecipe cookingRecipe,
            final NonNullList<ItemStack> itemStacks, final int maxStackSize) {
        if (itemStacks.get(SLOT_INPUT).isEmpty() || cookingRecipe == null) return false;

        final ItemStack itemstack = cookingRecipe.assemble(this, registryAccess);
        if (itemstack.isEmpty()) {
            return false;
        }

        final ItemStack outputStack = itemStacks.get(SLOT_RESULT);
        if (outputStack.isEmpty()) {
            return true;
        }

        if (!ItemStack.isSameItem(outputStack, itemstack)) {
            return false;
        }

        // Forge fix: make furnace respect stack sizes in furnace recipes
        if (outputStack.getCount() + itemstack.getCount() <= maxStackSize && outputStack.getCount() + itemstack.getCount() <= outputStack.getMaxStackSize()) {
            return true;
        }

        // Forge fix: make furnace respect stack sizes in furnace recipes
        return outputStack.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize();
    }

    public void awardUsedRecipesAndPopExperience(final ServerPlayer player) {
        final List<Recipe<?>> list = this.getRecipesToAwardAndPopExperience(player.serverLevel(), player.position());
        player.awardRecipes(list);

        for (final Recipe<?> recipe : list) {
            if (recipe != null) {
                player.triggerRecipeCrafted(recipe, this.getItemStacks());
            }
        }

        this.recipesUsed.clear();
    }

    public List<Recipe<?>> getRecipesToAwardAndPopExperience(final ServerLevel level, final Vec3 vec3) {
        List<Recipe<?>> list = Lists.newArrayList();

        for (final Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                createExperience(level, vec3, entry.getIntValue(), ((AbstractCookingRecipe) recipe).getExperience());
            });
        }

        return list;
    }

    @Override
    public int[] getSlotsForFace(final Direction direction) {
        if (direction == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return direction == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(final int slotIndex, final ItemStack itemStack,
            final @Nullable Direction direction) {
        return this.canPlaceItem(slotIndex, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(final int slotIndex, final ItemStack itemStack, final Direction direction) {
        if (direction == Direction.DOWN && slotIndex == SLOT_FUEL) {
            return itemStack.is(Items.WATER_BUCKET) || itemStack.is(Items.BUCKET);
        }

        return true;
    }

    @Override
    protected void readAdditionalSaveData(final CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);

        this.litTime = compoundTag.getInt("BurnTime");
        this.cookingProgress = compoundTag.getInt("CookTime");
        this.cookingTotalTime = compoundTag.getInt("CookTimeTotal");
        this.litDuration = this.getBurnDuration(this.getItem(SLOT_FUEL));
        final CompoundTag compoundtag = compoundTag.getCompound("RecipesUsed");

        for (final String recipeKey : compoundtag.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(recipeKey), compoundtag.getInt(recipeKey));
        }
    }

    @Override
    protected void addAdditionalSaveData(final CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);

        compoundTag.putInt("BurnTime", this.litTime);
        compoundTag.putInt("CookTime", this.cookingProgress);
        compoundTag.putInt("CookTimeTotal", this.cookingTotalTime);
        final CompoundTag compoundtag = new CompoundTag();
        this.recipesUsed.forEach((recipeKey, integer) -> compoundtag.putInt(recipeKey.toString(), integer));
        compoundTag.put("RecipesUsed", compoundtag);
    }

    @Override
    public boolean canPlaceItem(final int slotIndex, final ItemStack itemStack) {
        if (slotIndex == SLOT_RESULT) {
            return false;
        }

        if (slotIndex == SLOT_FUEL) {
            final ItemStack itemstack = this.getItem(SLOT_FUEL);

            if (0 < ForgeHooks.getBurnTime(itemStack, this.recipeType)) {
                return true;
            }

            return itemStack.is(Items.BUCKET) && !itemstack.is(Items.BUCKET);
        }

        return true;
    }

    @Override
    public void setItem(final int slotIndex, final ItemStack itemStack) {
        final ItemStack currentStack = this.getItem(slotIndex);
        final boolean flag = !itemStack.isEmpty() && ItemStack.isSameItemSameTags(currentStack, itemStack);
        super.setItem(slotIndex, itemStack);
        if (itemStack.getCount() > this.getMaxStackSize()) {
            itemStack.setCount(this.getMaxStackSize());
        }

        if (slotIndex == 0 && !flag) {
            this.cookingTotalTime = getTotalCookTime(this.level(), this);
            this.cookingProgress = 0;
            this.setChanged();
        }
    }

    public boolean isLit() {
        return this.litTime > 0;
    }

    @Override
    protected abstract AbstractFurnaceCompartmentMenu createMenu(final int id, final Inventory playerInventory);
}