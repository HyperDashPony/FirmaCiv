from mcresources import loot_tables
from mcresources.type_definitions import Json


def boat_frame(wood: str) -> list[Json]:
    return [{"name": "firmaciv:watercraft_frame_angled"},
            [
                # Planks
                {"name": f"tfc:wood/planks/{wood}",
                 "conditions": [loot_tables.block_state_property(
                     f"firmaciv:wood/watercraft_frame_angled/{wood}[frame_processed=0]")]},

                {"name": f"tfc:wood/planks/{wood}", "functions": loot_tables.set_count(2),
                 "conditions": [loot_tables.block_state_property(
                     f"firmaciv:wood/watercraft_frame_angled/{wood}[frame_processed=1]")]},

                {"name": f"tfc:wood/planks/{wood}", "functions": loot_tables.set_count(3),
                 "conditions": [loot_tables.block_state_property(
                     f"firmaciv:wood/watercraft_frame_angled/{wood}[frame_processed=2]")]},

                *[{"name": f"tfc:wood/planks/{wood}", "functions": loot_tables.set_count(4),
                   "conditions": [loot_tables.block_state_property(
                       f"firmaciv:wood/watercraft_frame_angled/{wood}[frame_processed={n}]")]} for n in range(3, 8)]
            ],

            # Bolts
            [
                {"name": "firmaciv:copper_bolt",
                 "conditions": [loot_tables.block_state_property(
                     f"firmaciv:wood/watercraft_frame_angled/{wood}[frame_processed=4]")]},

                {"name": "firmaciv:copper_bolt", "functions": loot_tables.set_count(2),
                 "conditions": [loot_tables.block_state_property(
                     f"firmaciv:wood/watercraft_frame_angled/{wood}[frame_processed=5]")]},

                {"name": "firmaciv:copper_bolt", "functions": loot_tables.set_count(3),
                 "conditions": [loot_tables.block_state_property(
                     f"firmaciv:wood/watercraft_frame_angled/{wood}[frame_processed=6]")]},

                {"name": "firmaciv:copper_bolt", "functions": loot_tables.set_count(4),
                 "conditions": [loot_tables.block_state_property(
                     f"firmaciv:wood/watercraft_frame_angled/{wood}[frame_processed=7]")]}
            ]]
