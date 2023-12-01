from mcresources.type_definitions import Json

angledWaterCraftFrame = {
    "facing=north,shape=straight": {
        "model": "firmaciv:block/watercraft_frame_angled/straight",
        "y": 180
    },
    "facing=east,shape=straight": {
        "model": "firmaciv:block/watercraft_frame_angled/straight",
        "y": 270
    },
    "facing=south,shape=straight": {
        "model": "firmaciv:block/watercraft_frame_angled/straight",
    },
    "facing=west,shape=straight": {
        "model": "firmaciv:block/watercraft_frame_angled/straight",
        "y": 90
    },
    "facing=north,shape=inner_left": {
        "model": "firmaciv:block/watercraft_frame_angled/inner",
        "y": 90
    },
    "facing=east,shape=inner_left": {
        "model": "firmaciv:block/watercraft_frame_angled/inner",
        "y": 180
    },
    "facing=south,shape=inner_left": {
        "model": "firmaciv:block/watercraft_frame_angled/inner",
        "y": 270
    },
    "facing=west,shape=inner_left": {
        "model": "firmaciv:block/watercraft_frame_angled/inner"
    },
    "facing=north,shape=outer_left": {
        "model": "firmaciv:block/watercraft_frame_angled/outer",
        "y": 270
    },
    "facing=east,shape=outer_left": {
        "model": "firmaciv:block/watercraft_frame_angled/outer"
    },
    "facing=south,shape=outer_left": {
        "model": "firmaciv:block/watercraft_frame_angled/outer",
        "y": 90
    },
    "facing=west,shape=outer_left": {
        "model": "firmaciv:block/watercraft_frame_angled/outer",
        "y": 180
    },
    "facing=north,shape=inner_right": {
        "model": "firmaciv:block/watercraft_frame_angled/inner",
        "y": 180
    },
    "facing=east,shape=inner_right": {
        "model": "firmaciv:block/watercraft_frame_angled/inner",
        "y": 270
    },
    "facing=south,shape=inner_right": {
        "model": "firmaciv:block/watercraft_frame_angled/inner"
    },
    "facing=west,shape=inner_right": {
        "model": "firmaciv:block/watercraft_frame_angled/inner",
        "y": 90
    },
    "facing=north,shape=outer_right": {
        "model": "firmaciv:block/watercraft_frame_angled/outer"
    },
    "facing=east,shape=outer_right": {
        "model": "firmaciv:block/watercraft_frame_angled/outer",
        "y": 90
    },
    "facing=south,shape=outer_right": {
        "model": "firmaciv:block/watercraft_frame_angled/outer",
        "y": 180
    },
    "facing=west,shape=outer_right": {
        "model": "firmaciv:block/watercraft_frame_angled/outer",
        "y": 270
    }}


def getWoodFrameMultipart(wood: str) -> list[Json]:
    json = [
        ({"facing": "north", "shape": "straight"},
         {"model": "firmaciv:block/watercraft_frame_angled/straight", "y": 180}),
        ({"facing": "east", "shape": "straight"},
         {"model": "firmaciv:block/watercraft_frame_angled/straight", "y": 270}),
        ({"facing": "south", "shape": "straight"},
         {"model": "firmaciv:block/watercraft_frame_angled/straight"}),
        ({"facing": "west", "shape": "straight"},
         {"model": "firmaciv:block/watercraft_frame_angled/straight", "y": 90}),
        ({"facing": "north", "shape": "inner_left"},
         {"model": "firmaciv:block/watercraft_frame_angled/inner", "y": 90}),
        ({"facing": "east", "shape": "inner_left"},
         {"model": "firmaciv:block/watercraft_frame_angled/inner", "y": 180}),
        ({"facing": "south", "shape": "inner_left"},
         {"model": "firmaciv:block/watercraft_frame_angled/inner", "y": 270}),
        ({"facing": "west", "shape": "inner_left"},
         {"model": "firmaciv:block/watercraft_frame_angled/inner"}),
        ({"facing": "north", "shape": "outer_left"},
         {"model": "firmaciv:block/watercraft_frame_angled/outer", "y": 270}),
        ({"facing": "east", "shape": "outer_left"},
         {"model": "firmaciv:block/watercraft_frame_angled/outer"}),
        ({"facing": "south", "shape": "outer_left"},
         {"model": "firmaciv:block/watercraft_frame_angled/outer", "y": 90}),
        ({"facing": "west", "shape": "outer_left"},
         {"model": "firmaciv:block/watercraft_frame_angled/outer", "y": 180}),
        ({"facing": "north", "shape": "inner_right"},
         {"model": "firmaciv:block/watercraft_frame_angled/inner", "y": 180}),
        ({"facing": "east", "shape": "inner_right"},
         {"model": "firmaciv:block/watercraft_frame_angled/inner", "y": 270}),
        ({"facing": "south", "shape": "inner_right"},
         {"model": "firmaciv:block/watercraft_frame_angled/inner"}),
        ({"facing": "west", "shape": "inner_right"},
         {"model": "firmaciv:block/watercraft_frame_angled/inner", "y": 90}),
        ({"facing": "north", "shape": "outer_right"},
         {"model": "firmaciv:block/watercraft_frame_angled/outer"}),
        ({"facing": "east", "shape": "outer_right"},
         {"model": "firmaciv:block/watercraft_frame_angled/outer", "y": 90}),
        ({"facing": "south", "shape": "outer_right"},
         {"model": "firmaciv:block/watercraft_frame_angled/outer", "y": 180}),
        ({"facing": "west", "shape": "outer_right"},
         {"model": "firmaciv:block/watercraft_frame_angled/outer", "y": 270})
    ]

    return json
