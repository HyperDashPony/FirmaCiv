from typing import Optional, Union

from mcresources import ResourceManager, utils, RecipeContext
from mcresources.type_definitions import ResourceIdentifier, Json

import constants


def generate(rm: ResourceManager):
    def disableRecipe(name_parts: ResourceIdentifier):
        rm.recipe(name_parts, None, {}, conditions="forge:false")

    # Disable TFC boat recipes
    for woodType in constants.TFC_WOODS.keys():
        disableRecipe(f"tfc:crafting/wood/{woodType}_boat")

    # Disable vanilla boat recipes
    for woodType in ["acacia", "birch", "cherry", "dark_oak", "jungle", "mangrove", "oak", "spruce"]:
        disableRecipe(f"minecraft:{woodType}_boat")
        disableRecipe(f"minecraft:{woodType}_chest_boat")
    # Bamboo raft as well
    disableRecipe("minecraft:bamboo_raft")

    rm.crafting_shaped("minecraft:compass", ["X", "Y", "Z"], {
        "X": {
            "item": "tfc:lens"
        },
        "Y": {
            "tag": "tfc:magnetic_rocks"
        },
        "Z": {
            "item": "minecraft:bowl"
        }}, "firmaciv:firmaciv_compass")

    rm.crafting_shaped("crafting/watercraft_frame_angled", [" LL", "LLL", "LL "], {"L": "#tfc:lumber"},
                       "firmaciv:watercraft_frame_angled").with_advancement("firmaciv:watercraft_frame_angled")

    # Boating items
    rm.crafting_shapeless("crafting/barometer",
                          ["firmaciv:unfinished_barometer", "tfc:brass_mechanisms", "#tfc:glass_bottles",
                           {"type": "tfc:fluid_item",
                            "fluid_ingredient": {
                                "ingredient": "minecraft:water",
                                "amount": 100
                            }}],
                          "firmaciv:barometer").with_advancement("firmaciv:barometer")

    rm.crafting_shapeless("crafting/nav_clock",
                          ["firmaciv:unfinished_nav_clock", *["tfc:lens" for _ in range(2)],
                           *["tfc:brass_mechanisms" for _ in range(3)]],
                          "firmaciv:nav_clock").with_advancement("firmaciv:nav_clock")

    rm.crafting_shapeless("crafting/sextant", ["firmaciv:unfinished_sextant", "tfc:lens", "tfc:brass_mechanisms"],
                          "firmaciv:sextant").with_advancement("firmaciv:sextant")

    rm.crafting_shaped("crafting/kayak", ["SSS", "HSH", "LLL"],
                       {"S": "#forge:string", "H": "firmaciv:large_waterproof_hide", "L": "#tfc:lumber"},
                       "firmaciv:kayak").with_advancement("firmaciv:kayak")

    rm.crafting_shapeless("crafting/large_waterproof_hide",
                          ["tfc:large_prepared_hide", *["firmalife:beeswax" for _ in range(4)]],
                          "firmaciv:large_waterproof_hide",
                          conditions={"type": "forge:mod_loaded", "modid": "firmalife"}).with_advancement(
        "firmaciv:large_waterproof_hide")

    # Oar/paddles
    rm.crafting_shaped("crafting/oar", ["  S", " S ", "L  "], {"S": "#forge:rods/wooden", "L": "#tfc:lumber"},
                       "firmaciv:oar").with_advancement("firmaciv:oar")
    rm.crafting_shaped("crafting/kayak_paddle", ["  L", " S ", "L  "], {"S": "#forge:rods/wooden", "L": "#tfc:lumber"},
                       "firmaciv:kayak_paddle").with_advancement("firmaciv:kayak_paddle")
    rm.crafting_shaped("crafting/canoe_paddle", [" S ", "L  "], {"S": "#forge:rods/wooden", "L": "#tfc:lumber"},
                       "firmaciv:canoe_paddle").with_advancement("firmaciv:canoe_paddle")

    heat_recipe(rm, "barometer", "firmaciv:barometer", 930, None, "200 tfc:metal/brass")
    heat_recipe(rm, "copper_bolt", "firmaciv:copper_bolt", 1080, None, "25 tfc:metal/copper")
    heat_recipe(rm, "nav_clock", "firmaciv:nav_clock", 930, None, "400 tfc:metal/brass")
    heat_recipe(rm, "oarlock", "firmaciv:oarlock", 1535, None, "200 tfc:metal/cast_iron")
    heat_recipe(rm, "sextant", "firmaciv:sextant", 930, None, "200 tfc:metal/brass")
    heat_recipe(rm, "unfinished_barometer", "firmaciv:unfinished_barometer", 930, None, "200 tfc:metal/brass")
    heat_recipe(rm, "unfinished_nav_clock", "firmaciv:unfinished_nav_clock", 930, None, "400 tfc:metal/brass")
    heat_recipe(rm, "unfinished_sextant", "firmaciv:unfinished_sextant", 930, None, "200 tfc:metal/brass")


def heat_recipe(rm: ResourceManager, name_parts: ResourceIdentifier, ingredient: Json, temperature: float,
                result_item: Optional[Union[str, Json]] = None, result_fluid: Optional[str] = None,
                use_durability: Optional[bool] = None, chance: Optional[float] = None) -> RecipeContext:
    """
    Copied from tfc data gen
    """
    result_item = item_stack_provider(result_item) if isinstance(result_item, str) else result_item
    result_fluid = None if result_fluid is None else fluid_stack(result_fluid)
    return rm.recipe(('heating', name_parts), 'tfc:heating', {
        'ingredient': utils.ingredient(ingredient),
        'result_item': result_item,
        'result_fluid': result_fluid,
        'temperature': temperature,
        'use_durability': use_durability if use_durability else None,
        'chance': chance,
    })


def fluid_stack(data_in: Json) -> Json:
    """
    Copied from tfc data gen
    """
    if isinstance(data_in, dict):
        return data_in
    fluid, tag, amount, _ = utils.parse_item_stack(data_in, False)
    assert not tag, 'fluid_stack() cannot be a tag'
    return {
        'fluid': fluid,
        'amount': amount
    }


def item_stack_provider(
        data_in: Json = None,
        # Possible Modifiers
        copy_input: bool = False,
        copy_heat: bool = False,
        copy_food: bool = False,  # copies both decay and traits
        copy_oldest_food: bool = False,  # copies only decay, from all inputs (uses crafting container)
        reset_food: bool = False,  # rest_food modifier - used for newly created food from non-food
        add_glass: bool = False,  # glassworking specific
        add_powder: bool = False,  # glassworking specific
        add_heat: float = None,
        add_trait: str = None,  # applies a food trait and adjusts decay accordingly
        remove_trait: str = None,  # removes a food trait and adjusts decay accordingly
        empty_bowl: bool = False,  # replaces a soup with its bowl
        copy_forging: bool = False,
        add_bait_to_rod: bool = False,  # adds bait to the rod, uses crafting container
        dye_color: str = None,  # applies a dye color to leather dye-able armor
        meal: Json = None  # makes a meal from input specified in json
) -> Json:
    """
    Copied from tfc data gen
    """
    if isinstance(data_in, dict):
        return data_in
    stack = utils.item_stack(data_in) if data_in is not None else None
    modifiers = [k for k, v in (
        # Ordering is important here
        # First, modifiers that replace the entire stack (copy input style)
        # Then, modifiers that only mutate an existing stack
        ('tfc:empty_bowl', empty_bowl),
        ('tfc:copy_input', copy_input),
        ('tfc:copy_heat', copy_heat),
        ('tfc:copy_food', copy_food),
        ('tfc:copy_oldest_food', copy_oldest_food),
        ('tfc:reset_food', reset_food),
        ('tfc:copy_forging_bonus', copy_forging),
        ('tfc:add_bait_to_rod', add_bait_to_rod),
        ('tfc:add_glass', add_glass),
        ('tfc:add_powder', add_powder),
        ({'type': 'tfc:add_heat', 'temperature': add_heat}, add_heat is not None),
        ({'type': 'tfc:add_trait', 'trait': add_trait}, add_trait is not None),
        ({'type': 'tfc:remove_trait', 'trait': remove_trait}, remove_trait is not None),
        ({'type': 'tfc:dye_leather', 'color': dye_color}, dye_color is not None),
        ({'type': 'tfc:meal', **(meal if meal is not None else {})}, meal is not None),
    ) if v]
    if modifiers:
        return {
            'stack': stack,
            'modifiers': modifiers
        }
    return stack
