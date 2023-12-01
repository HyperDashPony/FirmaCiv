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
        manager.blockstate_multipart(f"wood/{wood}/watercraft_frame_angled",
                                     *BlockStates.getWoodFrameMultipart(wood)).with_lang(f"{name} Boat Frame")

        manager.blockstate("watercraft_frame_angled", variants=BlockStates.angledWaterCraftFrame).with_lang(
            "Angled Watercraft Frame")
        manager.item_model("watercraft_frame_angled", parent="firmaciv:block/watercraft_frame_angled/straight",
                           no_textures=True)


def main():
    resourceManager = mcresources.ResourceManager("firmaciv", "../src/main/resources")
    generateBlockModels(resourceManager)
    resourceManager.flush()
    print("Generated stuff!")


if __name__ == '__main__':
    main()
