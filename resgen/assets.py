from mcresources import ResourceManager

import BlockStates
import constants


def generate(manager: ResourceManager):
    for wood, name in constants.TFC_WOODS.items():
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
