from mcresources import ResourceManager

import constants


def generate(manager: ResourceManager):
    # Tags with all wood types
    for wood in constants.TFC_WOODS.keys():
        manager.block_tag("canoe_component_blocks", f"canoe_component_block/{wood}")
        manager.block_tag("can_make_canoe_unrestricted", f"tfc:wood/stripped_log/{wood}")
        manager.block_tag("wooden_watercraft_frames", f"wood/{wood}/watercraft_frame_angled")

    # Vanilla mining tags
    manager.block_tag("minecraft:mineable/axe", "watercraft_frame_angled", "#firmaciv:canoe_component_blocks",
                      "#firmaciv:wooden_watercraft_frames")
    manager.block_tag("minecraft:mineable/pickaxe", "oarlock")

    # TFC tags
    manager.block_tag("tfc:mineable_with_blunt_tool", "#firmaciv:canoe_component_blocks")
    manager.item_tag("tfc:usable_on_tool_rack", "canoe_paddle", "kayak_paddle", "oar", "kayak", "nav_clock", "sextant",
                     "barometer")
