from mcresources import ResourceManager
from mcresources.type_definitions import ResourceIdentifier

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
