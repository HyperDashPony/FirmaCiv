import mcresources
from mcresources import ResourceManager

import BlockStates
import Constants


def generateBlockModels(manager: ResourceManager):
    for wood, name in Constants.TFC_WOODS.items():
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
    resourceManager.lang(Constants.DEFAULT_LANG)
    generateBlockModels(resourceManager)
    resourceManager.flush()
    print("Generated stuff!")


if __name__ == '__main__':
    main()
