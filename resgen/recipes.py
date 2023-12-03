from mcresources import ResourceManager
from mcresources.type_definitions import ResourceIdentifier

import constants


def generate(manager: ResourceManager):
    def disableRecipe(name_parts: ResourceIdentifier):
        manager.recipe(name_parts, None, {}, conditions="forge:false")

    # Disable TFC boat recipes
    for wood in constants.TFC_WOODS.keys():
        disableRecipe(f"tfc:crafting/wood/{wood}_boat")

    # Disable vanilla boat recipes
    for wood in ["acacia", "birch", "cherry", "dark_oak", "jungle", "mangrove", "oak", "spruce"]:
        disableRecipe(f"minecraft:{wood}_boat")
        disableRecipe(f"minecraft:{wood}_chest_boat")
    # Bamboo raft as well
    disableRecipe("minecraft:bamboo_raft")

    manager.crafting_shaped("minecraft:compass", ["X", "Y", "Z"], {
        "X": {
            "item": "tfc:lens"
        },
        "Y": {
            "tag": "tfc:magnetic_rocks"
        },
        "Z": {
            "item": "minecraft:bowl"
        }}, "firmaciv:firmaciv_compass")
