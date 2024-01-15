from mcresources import ResourceManager

import constants


def generate(manager: ResourceManager):
    # Tags with all wood types
    for wood in constants.TFC_WOODS.keys():
        manager.block_tag("canoe_component_blocks", f"wood/canoe_component_block/{wood}")
        manager.block_tag("can_make_canoe_unrestricted", f"tfc:wood/stripped_log/{wood}")
        manager.block_tag("wooden_watercraft_frames", f"wood/watercraft_frame_angled/{wood}")
        manager.entity_tag("firmaciv:sloops", f"firmaciv:sloop/{wood}")
        manager.entity_tag("firmaciv:dugout_canoes", f"firmaciv:dugout_canoe/{wood}")
        manager.entity_tag("firmaciv:rowboats", f"firmaciv:rowboat/{wood}")

    manager.entity_tag("firmaciv:vehicle_helpers", "firmaciv:vehicle_cleat",
                       "firmaciv:vehicle_part_boat", "firmaciv:vehicle_switch_windlass", "firmaciv:vehicle_switch_sail",
                       "firmaciv:vehicle_collider", "firmaciv:vehicle_mast", )

    manager.entity_tag("firmaciv:compartments", "firmaciv:compartment_workbench",
                       "firmaciv:compartment_anvil", "firmaciv:compartment_chest", "firmaciv:compartment_empty")

    # Vanilla mining tags
    manager.block_tag("minecraft:mineable/axe", "watercraft_frame_angled", "#firmaciv:canoe_component_blocks",
                      "#firmaciv:wooden_watercraft_frames")
    manager.block_tag("minecraft:mineable/pickaxe", "oarlock")

    # TFC tags
    manager.block_tag("tfc:mineable_with_blunt_tool", "#firmaciv:canoe_component_blocks")
    manager.item_tag("tfc:usable_on_tool_rack", "canoe_paddle", "kayak_paddle", "oar", "kayak", "nav_clock", "sextant",
                     "barometer")

    # Carryon
    manager.block_tag("carryon:block_blacklist", "#firmaciv:canoe_component_blocks")
    manager.entity_tag("carryon:entity_blacklist", "#firmaciv:dugout_canoes", "#firmaciv:sloops",
                       "#firmaciv:rowboats", "firmaciv:kayak",
                       "#firmaciv:vehicle_helpers", "#firmaciv:compartments", "firmaciv:cannonball")
