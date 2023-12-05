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
