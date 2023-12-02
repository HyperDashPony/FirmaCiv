from mcresources import ResourceManager
from mcresources import loot_tables

import blockStates
import constants

def generate(manager: ResourceManager):
    for wood, name in constants.TFC_WOODS.items():
        manager.block_loot(f"wood/{wood}/watercraft_frame_angled")