from mcresources import ResourceManager

import constants


def generate(manager: ResourceManager):
    # Tags with all wood types
    for wood in constants.TFC_WOODS.keys():
        manager.block_tag("canoe_component_blocks", f"wood/canoe_component_block/{wood}")
        manager.block_tag("can_make_canoe_unrestricted", f"tfc:wood/stripped_log/{wood}")
        manager.block_tag("wooden_watercraft_frames", f"wood/watercraft_frame_angled/{wood}",
                          f"wood/watercraft_frame_flat/{wood}")
        manager.entity_tag("firmaciv:sloops", f"firmaciv:sloop/{wood}")
        manager.entity_tag("firmaciv:dugout_canoes", f"firmaciv:dugout_canoe/{wood}")
        manager.entity_tag("firmaciv:rowboats", f"firmaciv:rowboat/{wood}")

    # Vehicle helpers such as our collision entities
    manager.entity_tag("vehicle_helpers", "firmaciv:vehicle_cleat", "firmaciv:vehicle_part_boat",
                       "firmaciv:vehicle_switch_windlass", "firmaciv:vehicle_switch_sail", "firmaciv:vehicle_collider",
                       "firmaciv:vehicle_mast")

    # Compartment Entities
    manager.entity_tag("firmaciv:compartments", "firmaciv:compartment_anvil", "firmaciv:compartment_barrel",
                       "firmaciv:compartment_blast_furnace", "firmaciv:compartment_cartography_table",
                       "firmaciv:compartment_chest", "firmaciv:compartment_empty", "firmaciv:compartment_ender_chest",
                       "firmaciv:compartment_furnace", "firmaciv:compartment_grindstone", "firmaciv:compartment_loom",
                       "firmaciv:compartment_shulker_box", "firmaciv:compartment_smithing_table",
                       "firmaciv:compartment_smoker", "firmaciv:compartment_stonecutter",
                       "firmaciv:compartment_tfcchest", "firmaciv:compartment_workbench")

    # Vanilla mining tags
    manager.block_tag("minecraft:mineable/axe", "watercraft_frame_angled", "watercraft_frame_flat",
                      "#firmaciv:canoe_component_blocks", "#firmaciv:wooden_watercraft_frames")
    manager.block_tag("minecraft:mineable/pickaxe", "oarlock")

    # TFC tags
    manager.block_tag("tfc:mineable_with_blunt_tool", "#firmaciv:canoe_component_blocks")
    manager.item_tag("tfc:usable_on_tool_rack", "canoe_paddle", "kayak_paddle", "oar", "kayak", "nav_clock", "sextant",
                     "barometer")

    # Carryon blacklist tags (as of writing carryon has a bug which means these are ignored)
    manager.block_tag("carryon:block_blacklist", "#firmaciv:canoe_component_blocks")
    manager.entity_tag("carryon:entity_blacklist", "firmaciv:cannonball", "firmaciv:kayak", "#firmaciv:dugout_canoes",
                       "#firmaciv:sloops", "#firmaciv:rowboats", "#firmaciv:vehicle_helpers", "#firmaciv:compartments",
                       [f"firmaciv:sloop_construction/{wood}" for wood in constants.TFC_WOODS.keys()])
