import mcresources as mcr

from mcresources import ResourceManager as rm, ItemContext, utils, block_states, loot_tables


def ccbmodels():

    WOODS = {'acacia', 'ash', 'aspen', 'birch', 'blackwood', 'chestnut', 'douglas_fir', 'hickory', 'kapok', 'maple', 'oak', 'palm', 'pine', 'rosewood', 'sequoia', 'spruce', 'sycamore', 'white_cedar', 'willow'}



    for wood in WOODS:
        for middle in range(1, 14):
            templatefile = open("maple_boat.json", "r")
            tfdata = templatefile.read()

            ccb_namespace = 'tfc:recipes/crafting/' + wood + '_' + str(middle)

            ccb_name = wood + '_' + str(middle) + '.json'

            side_texture = particle_texture = 'tfc:block/wood/stripped_log/' + wood
            end_texture = 'tfc:block/wood/stripped_log_top/' + wood

            textures = {'0': side_texture, '1': end_texture, 'particle': particle_texture}

            parent_model = 'firmaciv:block/canoe_component_block/template/middle_end_' + str(middle)

            tfdata = tfdata.replace('[side_texture]', side_texture)
            tfdata = tfdata.replace('[end_texture]', end_texture)
            tfdata = tfdata.replace('[particle_texture]', particle_texture)

            tfdata = tfdata.replace('[parent_model]', parent_model)

            outfile = open(ccb_name, 'w')

            outfile.write(tfdata)

            templatefile.close()




ccbmodels()