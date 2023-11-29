import mcresources
from mcresources import ResourceManager

WOODS = {'acacia': "Acacia",
         'ash': "Ash",
         'aspen': "Aspen",
         'birch': "Birch",
         'blackwood': "Blackwood",
         'chestnut': "Chestnut",
         'douglas_fir': "Douglas Fir",
         'hickory': "Hickory",
         'kapok': "Kapok",
         'mangrove': "Mangrove",
         'maple': "Maple",
         'oak': "Oak",
         'palm': "Palm",
         'pine': "Pine",
         'rosewood': "Rosewood",
         'sequoia': "Sequoia",
         'spruce': "Spruce",
         'sycamore': "Sycamore",
         'willow': "Willow"}


def generateBlockModels(manager: ResourceManager):
    for wood, name in WOODS.items():
        # TODO multipart for woods
        block = manager.blockstate(f"wood/{wood}/watercraft_frame_angled", variants={})
        block.with_lang(f"{name} Boat Frame")

    block = manager.blockstate("watercraft_frame_angled", variants={
        "facing=north,half=bottom,shape=straight": {
            "model": "firmaciv:block/watercraft_frame_angled/straight",
            "y": 180
        },
        "facing=east,half=bottom,shape=straight": {
            "model": "firmaciv:block/watercraft_frame_angled/straight",
            "y": 270
        },
        "facing=south,half=bottom,shape=straight": {
            "model": "firmaciv:block/watercraft_frame_angled/straight",
        },
        "facing=west,half=bottom,shape=straight": {
            "model": "firmaciv:block/watercraft_frame_angled/straight",
            "y": 90
        },
        "facing=east,half=bottom,shape=inner_left": {
            "model": "firmaciv:block/watercraft_frame_angled/inner",
            "y": 180
        },
        "facing=east,half=bottom,shape=inner_right": {
            "model": "firmaciv:block/watercraft_frame_angled/inner",
            "y": 270
        },
        "facing=east,half=bottom,shape=outer_left": {
            "model": "firmaciv:block/watercraft_frame_angled/outer"
        },
        "facing=east,half=bottom,shape=outer_right": {
            "model": "firmaciv:block/watercraft_frame_angled/outer",
            "y": 90
        },
        "facing=north,half=bottom,shape=inner_left": {
            "model": "firmaciv:block/watercraft_frame_angled/inner",
            "y": 90
        },
        "facing=north,half=bottom,shape=inner_right": {
            "model": "firmaciv:block/watercraft_frame_angled/inner",
            "y": 180
        },
        "facing=north,half=bottom,shape=outer_left": {
            "model": "firmaciv:block/watercraft_frame_angled/outer",
            "y": 270
        },
        "facing=north,half=bottom,shape=outer_right": {
            "model": "firmaciv:block/watercraft_frame_angled/outer"
        },
        "facing=south,half=bottom,shape=inner_left": {
            "model": "firmaciv:block/watercraft_frame_angled/inner",
            "y": 270
        },
        "facing=south,half=bottom,shape=inner_right": {
            "model": "firmaciv:block/watercraft_frame_angled/inner"
        },
        "facing=south,half=bottom,shape=outer_left": {
            "model": "firmaciv:block/watercraft_frame_angled/outer",
            "y": 90
        },
        "facing=south,half=bottom,shape=outer_right": {
            "model": "firmaciv:block/watercraft_frame_angled/outer",
            "y": 180
        },
        "facing=west,half=bottom,shape=inner_left": {
            "model": "firmaciv:block/watercraft_frame_angled/inner"
        },
        "facing=west,half=bottom,shape=inner_right": {
            "model": "firmaciv:block/watercraft_frame_angled/inner",
            "y": 90
        },
        "facing=west,half=bottom,shape=outer_left": {
            "model": "firmaciv:block/watercraft_frame_angled/outer",
            "y": 180
        },
        "facing=west,half=bottom,shape=outer_right": {
            "model": "firmaciv:block/watercraft_frame_angled/outer",
            "y": 270
        }})
    block.with_lang("Angled Watercraft Frame")
    manager.item_model("watercraft_frame_angled", parent="firmaciv:block/watercraft_frame_angled/straight",
                       no_textures=True)


def main():
    resourceManager = mcresources.ResourceManager("firmaciv", "../src/main/resources")
    generateBlockModels(resourceManager)
    resourceManager.flush()
    print("Generated stuff!")


if __name__ == '__main__':
    main()
