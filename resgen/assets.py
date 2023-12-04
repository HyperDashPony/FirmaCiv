from mcresources import ResourceManager

import blockStates
import constants


def generate(manager: ResourceManager):
    for wood, name in constants.TFC_WOODS.items():
        # Generate models from templates
        for shape in ["straight", "inner", "outer"]:
            for progress in ["first", "second", "third", "fourth"]:
                manager.block_model(f"wood/watercraft_frame_angled/{wood}/{shape}/{progress}",
                                    {"plank": f"tfc:block/wood/planks/{wood}"},
                                    f"firmaciv:block/watercraft_frame_angled/template/{shape}/{progress}")

        manager.blockstate_multipart(f"wood/watercraft_frame_angled/{wood}",
                                     *blockStates.getWoodFrameMultipart(wood)).with_lang(
            f"{name} Shipwright's Scaffolding").with_block_loot("firmaciv:watercraft_frame_angled")

        # Canoe components now
        canoe_component_textures = {"0": f"tfc:block/wood/stripped_log/{wood}",
                                    "1": "tfc:block/wood/stripped_log_top/acacia",
                                    "particle": "tfc:block/wood/stripped_log/acacia"}
        # Models that are shared by the end and middle states
        for n in range(8):
            manager.block_model(f"wood/canoe_component_block/{wood}/all/{n}", canoe_component_textures,
                                f"firmaciv:block/canoe_component_block/template/all/{n}")
        # End and Middle only models
        for n in range(8, 13):
            manager.block_model(f"wood/canoe_component_block/{wood}/end/{n}", canoe_component_textures,
                                f"firmaciv:block/canoe_component_block/template/end/{n}")
            manager.block_model(f"wood/canoe_component_block/{wood}/middle/{n}", canoe_component_textures,
                                f"firmaciv:block/canoe_component_block/template/middle/{n}")
            manager.blockstate(f"wood/canoe_component_block/{wood}",
                               variants=blockStates.canoe_component(wood)).with_lang(
                f"{name} Canoe Component").with_block_loot(f"tfc:wood/lumber/{wood}")

    # Basic frame
    manager.blockstate("watercraft_frame_angled", variants=blockStates.angledWaterCraftFrame).with_lang(
        "Shipwright's Scaffolding").with_block_loot("firmaciv:watercraft_frame_angled")
    # Need to manually make the model
    manager.item_model("watercraft_frame_angled", parent="firmaciv:block/watercraft_frame_angled/straight",
                       no_textures=True)

    manager.blockstate("oarlock", variants={
        "facing=east": {
            "model": "firmaciv:block/oarlock",
            "y": 90
        },
        "facing=north": {
            "model": "firmaciv:block/oarlock"
        },
        "facing=south": {
            "model": "firmaciv:block/oarlock",
            "y": 180
        },
        "facing=west": {
            "model": "firmaciv:block/oarlock",
            "y": 270
        }
    }).with_lang("Oarlock").with_block_loot("oarlock")
    manager.item_model("oarlock")

    # Items with generated models
    manager.item("unfinished_barometer").with_item_model().with_lang("Unfinished Barometer")
    manager.item("unfinished_nav_clock").with_item_model().with_lang("Unfinished Navigator's Timepiece")
    manager.item("unfinished_sextant").with_item_model().with_lang("Unfinished Sextant")
    manager.item("sextant").with_item_model().with_lang("Sextant")
    manager.item("copper_bolt").with_item_model().with_lang("Copper Bolt")
    manager.item("kayak").with_item_model().with_lang("Kayak")
    manager.item("large_waterproof_hide").with_item_model().with_lang("Large Waterproof Hide")
    manager.item("nav_toolkit").with_item_model().with_lang("Navigator's Toolkit")

    # Items with custom models
    manager.item("barometer").with_lang("Barometer")
    manager.item("nav_clock").with_lang("Navigator's Timepiece")
    manager.item("firmaciv_compass").with_lang("Compass (Declination: True North)")

    manager.item("kayak_paddle").with_lang("Kayak Paddle")
    manager.item("canoe_paddle").with_lang("Canoe Paddle")
    manager.item("oar").with_lang("Oar")
