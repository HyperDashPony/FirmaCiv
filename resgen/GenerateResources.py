import mcresources
from mcresources import ResourceManager

import BlockStates

WOODS = {"acacia": "Acacia",
         "ash": "Ash",
         "aspen": "Aspen",
         "birch": "Birch",
         "blackwood": "Blackwood",
         "chestnut": "Chestnut",
         "douglas_fir": "Douglas Fir",
         "hickory": "Hickory",
         "kapok": "Kapok",
         "mangrove": "Mangrove",
         "maple": "Maple",
         "oak": "Oak",
         "palm": "Palm",
         "pine": "Pine",
         "rosewood": "Rosewood",
         "sequoia": "Sequoia",
         "spruce": "Spruce",
         "sycamore": "Sycamore",
         "white_cedar": "White Cedar",
         "willow": "Willow"}


def generateBlockModels(manager: ResourceManager):
    for wood, name in WOODS.items():
        # Generate models from templates
        for shape in ["straight", "inner", "outer"]:
            for progress in ["first", "second", "third", "fourth"]:
                manager.block_model(f"watercraft_frame_angled/wood/{shape}/{progress}/{wood}",
                                    {"plank": f"tfc:block/wood/planks/{wood}"},
                                    f"firmaciv:block/watercraft_frame_angled/template/{shape}/{progress}")

        manager.blockstate_multipart(f"wood/{wood}/watercraft_frame_angled",
                                     *BlockStates.getWoodFrameMultipart(wood)).with_lang(f"{name} Boat Frame")

        manager.blockstate("watercraft_frame_angled", variants=BlockStates.angledWaterCraftFrame).with_lang(
            "Angled Watercraft Frame")
        manager.item_model("watercraft_frame_angled", parent="firmaciv:block/watercraft_frame_angled/straight",
                           no_textures=True)


def main():
    resourceManager = mcresources.ResourceManager("firmaciv", "../src/main/resources",
                                                  on_error=lambda file, e: print(f"Error writing {file}\n{e}"))
    generateBlockModels(resourceManager)
    resourceManager.flush()
    print("Generated stuff!")


if __name__ == '__main__':
    main()
