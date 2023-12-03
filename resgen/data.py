from mcresources import ResourceManager

import constants


def generate(manager: ResourceManager):
    # Tags with all wood types
    for wood in constants.TFC_WOODS.keys():
        manager.tag("canoe_component_blocks", "blocks", f"canoe_component_block/{wood}")
        manager.tag("can_make_canoe_unrestricted", "blocks", f"tfc:wood/stripped_log/{wood}")
        manager.tag("wooden_watercraft_frames", "blocks", f"wood/{wood}/watercraft_frame_angled")
        manager.tag("tfc:mineable_with_blunt_tool", "blocks", f"canoe_component_block/{wood}")
