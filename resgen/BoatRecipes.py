import mcresources as mcr

from mcresources import ResourceManager as rm, ItemContext, utils, block_states, loot_tables

def boatrecipes_vanilla():

    WOODS = {'oak', 'birch', 'spruce', 'jungle', 'acacia', 'dark_oak', 'mangrove', 'cherry', 'bamboo'}

    for wood in WOODS:
        templatefile = open("maple_boat.json", "r")
        tfdata = templatefile.read()

        recipe_name = wood + '_boat.json'

        tfdata = tfdata.replace('maple', wood)

        outfile = open(recipe_name, 'w')

        outfile.write(tfdata)

        templatefile.close()
def boatrecipes_tfc():

    WOODS = {'acacia', 'ash', 'aspen', 'birch', 'blackwood', 'chestnut', 'douglas_fir', 'hickory', 'kapok', 'maple', 'oak', 'palm', 'pine', 'rosewood', 'sequoia', 'spruce', 'sycamore', 'white_cedar', 'willow'}

    for wood in WOODS:
        templatefile = open("maple_boat.json", "r")
        tfdata = templatefile.read()

        recipe_name = wood + '_boat.json'

        tfdata = tfdata.replace('maple', wood)

        outfile = open(recipe_name, 'w')

        outfile.write(tfdata)

        templatefile.close()

def boatrecipes_florae():

    WOODS = {"african_padauk",
    "alder",
     "angelim",
     "argyle_eucalyptus",
     "bald_cypress",
     "baobab",
     "beech",
     "black_walnut",
     "black_willow",
     "brazilwood",
     "butternut",
     "buxus",
     "cocobolo",
     "common_oak",
     "cypress",
     "ebony",
     "fever",
     "ghaf",
     "ginkgo",
     "greenheart",
     "hawthorn",
     "hazel",
     "hemlock",
     "holly",
     "hornbeam",
     "iroko",
     "ironwood",
     "jabuticabeira",
     "joshua",
     "juniper",
     "kauri",
     "larch",
     "laurel",
     "limba",
     "locust",
     "logwood",
     "maclura",
     "mahoe",
     "mahogany",
     "marblewood",
     "medlar",
     "messmate",
     "mountain_ash",
     "mulberry",
     "nordmann_fir",
     "norway_spruce",
     "pear",
     "pink_cherry_blossom",
     "pink_ipe",
     "pink_ivory",
     "poplar",
     "purple_ipe",
     "purple_jacaranda",
     "purpleheart",
     "quince",
     "rainbow_eucalyptus",
     "red_cedar",
     "red_cypress",
     "red_elm",
     "red_mangrove",
     "redwood",
     "rowan",
     "rubber_fig",
     "sloe",
     "snow_gum_eucalyptus",
     "sorb",
     "sweetgum",
     "syzygium",
     "teak",
     "walnut",
     "wenge",
     "white_cherry_blossom",
     "white_elm",
     "white_ipe",
     "white_jacaranda",
     "white_mangrove",
     "whitebeam",
     "yellow_ipe",
     "yellow_jacaranda",
     "yellow_meranti",
     "yew",
     "zebrawood",
     "persimmon",
     "bamboo"}

    for wood in WOODS:
        templatefile = open("maple_boat.json", "r")
        tfdata = templatefile.read()

        recipe_name = wood + '_boat.json'

        tfdata = tfdata.replace('maple', wood)

        outfile = open(recipe_name, 'w')

        outfile.write(tfdata)

        templatefile.close()

boatrecipes_vanilla()