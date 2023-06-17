import mcresources as mcr

from mcresources import ResourceManager as rm, ItemContext, utils, block_states, loot_tables


def boatrecipes():

    WOODS = {'acacia', 'ash', 'aspen', 'birch', 'blackwood', 'chestnut', 'douglas_fir', 'hickory', 'kapok', 'maple', 'oak', 'palm', 'pine', 'rosewood', 'sequoia', 'spruce', 'sycamore', 'white_cedar', 'willow'}

    for wood in WOODS:
        templatefile = open("maple_boat.json", "r")
        tfdata = templatefile.read()

        recipe_name = wood + '_boat.json'

        tfdata = tfdata.replace('maple', wood)

        outfile = open(recipe_name, 'w')

        outfile.write(tfdata)

        templatefile.close()


boatrecipes()