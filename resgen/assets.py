from mcresources import ResourceManager

import blockStates
import constants
import lootTables


def generate(rm: ResourceManager):
    for woodType, name in constants.TFC_WOODS.items():
        # Generate models from templates
        for shape in ["straight", "inner", "outer"]:
            for progress in ["first", "second", "third", "fourth"]:
                rm.block_model(f"wood/watercraft_frame_angled/{woodType}/{shape}/{progress}",
                               {"plank": f"tfc:block/wood/planks/{woodType}"},
                               f"firmaciv:block/watercraft_frame_angled/template/{shape}/{progress}")

        rm.blockstate_multipart(f"wood/watercraft_frame_angled/{woodType}",
                                *blockStates.getWoodFrameMultipart(woodType)).with_lang(
            f"{name} Shipwright's Scaffolding").with_block_loot(*lootTables.boat_frame(woodType))

        # Canoe components now
        canoe_component_textures = {"0": f"tfc:block/wood/stripped_log/{woodType}",
                                    "1": f"tfc:block/wood/stripped_log_top/{woodType}",
                                    "particle": f"tfc:block/wood/stripped_log/{woodType}"}

        # Models that are shared by the end and middle states
        for n in range(8):
            rm.block_model(f"wood/canoe_component_block/{woodType}/all/{n}", canoe_component_textures,
                           f"firmaciv:block/canoe_component_block/template/all/{n}")

        # End and Middle only models
        for n in range(8, 13):
            rm.block_model(f"wood/canoe_component_block/{woodType}/end/{n}", canoe_component_textures,
                           f"firmaciv:block/canoe_component_block/template/end/{n}")
            rm.block_model(f"wood/canoe_component_block/{woodType}/middle/{n}", canoe_component_textures,
                           f"firmaciv:block/canoe_component_block/template/middle/{n}")
            rm.blockstate(f"wood/canoe_component_block/{woodType}",
                          variants=blockStates.canoe_component(woodType)).with_lang(
                f"{name} Canoe Component").with_block_loot(f"tfc:wood/lumber/{woodType}")

    # Basic frame
    rm.blockstate("watercraft_frame_angled", variants=blockStates.angledWaterCraftFrame).with_lang(
        "Shipwright's Scaffolding").with_block_loot("firmaciv:watercraft_frame_angled")

    # Need to manually make the model
    rm.item_model("watercraft_frame_angled", parent="firmaciv:block/watercraft_frame_angled/straight",
                  no_textures=True)

    rm.blockstate("oarlock", variants={
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
    }).with_lang("Oarlock").with_block_loot("firmaciv:oarlock")
    rm.item_model("oarlock")

    # Items with generated models
    rm.item("unfinished_barometer").with_item_model().with_lang("Unfinished Barometer")
    rm.item("unfinished_nav_clock").with_item_model().with_lang("Unfinished Navigator's Timepiece")
    rm.item("unfinished_sextant").with_item_model().with_lang("Unfinished Sextant")
    rm.item("cannon").with_item_model().with_lang("Cannon")
    rm.item("cannonball").with_item_model().with_lang("Cannonball")

    #rm.item("sextant").with_item_model().with_lang("Sextant")
    rm.item("copper_bolt").with_item_model().with_lang("Copper Bolt")
    rm.item("kayak").with_item_model().with_lang("Kayak")
    rm.item("large_waterproof_hide").with_item_model().with_lang("Large Waterproof Hide")
    rm.item("nav_toolkit").with_item_model().with_lang("Navigator's Toolkit")

    # Items with custom models
    rm.item("barometer").with_lang("Barometer")
    rm.item("nav_clock").with_lang("Navigator's Timepiece")
    rm.item("firmaciv_compass").with_lang("Compass (Declination: True North)")

    rm.item("kayak_paddle").with_lang("Kayak Paddle")
    rm.item("canoe_paddle").with_lang("Canoe Paddle")
    rm.item("oar").with_lang("Oar")
