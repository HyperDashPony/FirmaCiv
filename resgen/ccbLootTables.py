import mcresources as mcr

from mcresources import ResourceManager as rm, ItemContext, utils, block_states, loot_tables


def ccbloot():

    WOODS = {'acacia', 'ash', 'aspen', 'birch', 'blackwood', 'chestnut', 'douglas_fir', 'hickory', 'kapok', 'maple', 'oak', 'palm', 'pine', 'rosewood', 'sequoia', 'spruce', 'sycamore', 'white_cedar', 'willow'}

    for wood in WOODS:
        templatefile = open("ccb_loot_template.json", "r")
        tfdata = templatefile.read()

        ccb_name = wood + '.json'

        tfdata = tfdata.replace('[wood]', wood)

        outfile = open(ccb_name, 'w')

        outfile.write(tfdata)

        templatefile.close()


ccbloot()