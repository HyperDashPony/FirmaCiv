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


def canoe_component(wood: str) -> Json:
    var: Json = {}

    for direction, rotation in {"north": None, "east": 90, "south": 180, "west": 270}.items():
        for n in range(8):
            var[f"facing={direction},canoe_carved={n + 1}"] = {
                "model": f"firmaciv:block/wood/canoe_component_block/{wood}/all/{n}",
                "y": rotation
            }

        for n in range(8, 13):
            var[f"facing={direction},canoe_carved={n + 1},end=false"] = {
                "model": f"firmaciv:block/wood/canoe_component_block/{wood}/middle/{n}",
                "y": rotation
            }
            var[f"facing={direction},canoe_carved={n + 1},end=true"] = {
                "model": f"firmaciv:block/wood/canoe_component_block/{wood}/end/{n}",
                "y": rotation
            }

    return var


def getWoodFrameFlatMultipart(wood: str) -> list[Json]:
    json = [{"model": "firmaciv:block/watercraft_frame/flat/frame"}]
    plankTemplateStates = {"first": "0|1|2|3|4|5|6|7", "second": "1|2|3|4|5|6|7", "third": "2|3|4|5|6|7",
                           "fourth": "3|4|5|6|7"}

    for template, processedStates in plankTemplateStates.items():
        json += [({"frame_processed": processedStates},
                  {"model": f"firmaciv:block/wood/watercraft_frame/flat/{wood}/{template}"})]

    boltTemplateStates = {"first": "4|5|6|7", "second": "5|6|7", "third": "6|7", "fourth": "7"}

    for template, processedStates in boltTemplateStates.items():
        json += [({"frame_processed": processedStates},
                  {"model": f"firmaciv:block/watercraft_frame/flat/bolt/{template}"})]

    return json


def getWoodFrameMultipart(wood: str) -> list[Json]:
    # Only the frames
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

    plankTemplateStates = {"first": "0|1|2|3|4|5|6|7", "second": "1|2|3|4|5|6|7", "third": "2|3|4|5|6|7",
                           "fourth": "3|4|5|6|7"}

    # Straight shape planks
    for template, processedStates in plankTemplateStates.items():
        # Wood progress states
        json += [
            ({"facing": "north", "shape": "straight", "frame_processed": processedStates},
             {"model": f"firmaciv:block/wood/watercraft_frame_angled/{wood}/straight/{template}",
              "uvlock": True,
              "y": 180}),
            ({"facing": "east", "shape": "straight", "frame_processed": processedStates},
             {"model": f"firmaciv:block/wood/watercraft_frame_angled/{wood}/straight/{template}",
              "uvlock": True,
              "y": 270}),
            ({"facing": "south", "shape": "straight", "frame_processed": processedStates},
             {"model": f"firmaciv:block/wood/watercraft_frame_angled/{wood}/straight/{template}"}),
            ({"facing": "west", "shape": "straight", "frame_processed": processedStates},
             {"model": f"firmaciv:block/wood/watercraft_frame_angled/{wood}/straight/{template}",
              "uvlock": True,
              "y": 90})
        ]

    inner = {"left": [90, 180, 270, None], "right": [180, 270, None, 90]}
    outer = {"left": [270, None, 90, 180], "right": [None, 90, 180, 270]}

    # Planks for inner and outer
    for shape, rotations in {"inner": inner, "outer": outer}.items():
        for side, rotation in rotations.items():
            for template, processedStates in plankTemplateStates.items():
                json += [
                    ({"facing": "north", "shape": f"{shape}_{side}", "frame_processed": processedStates},
                     {"model": f"firmaciv:block/wood/watercraft_frame_angled/{wood}/{shape}/{template}",
                      "uvlock": True,
                      "y": rotation[0]}),
                    ({"facing": "east", "shape": f"{shape}_{side}", "frame_processed": processedStates},
                     {"model": f"firmaciv:block/wood/watercraft_frame_angled/{wood}/{shape}/{template}",
                      "uvlock": True,
                      "y": rotation[1]}),
                    ({"facing": "south", "shape": f"{shape}_{side}", "frame_processed": processedStates},
                     {"model": f"firmaciv:block/wood/watercraft_frame_angled/{wood}/{shape}/{template}",
                      "uvlock": True,
                      "y": rotation[2]}),
                    ({"facing": "west", "shape": f"{shape}_{side}", "frame_processed": processedStates},
                     {"model": f"firmaciv:block/wood/watercraft_frame_angled/{wood}/{shape}/{template}",
                      "uvlock": True,
                      "y": rotation[3]})
                ]

    boltTemplateStates = {"first": "4|5|6|7", "second": "5|6|7", "third": "6|7", "fourth": "7"}

    # Bolt straight
    for template, processedStates in boltTemplateStates.items():
        # Bolt progress states
        json += [
            ({"facing": "north", "shape": "straight", "frame_processed": processedStates},
             {"model": f"firmaciv:block/watercraft_frame_angled/bolt/straight/{template}",
              "uvlock": True,
              "y": 180}),
            ({"facing": "east", "shape": "straight", "frame_processed": processedStates},
             {"model": f"firmaciv:block/watercraft_frame_angled/bolt/straight/{template}",
              "uvlock": True,
              "y": 270}),
            ({"facing": "south", "shape": "straight", "frame_processed": processedStates},
             {"model": f"firmaciv:block/watercraft_frame_angled/bolt/straight/{template}"}),
            ({"facing": "west", "shape": "straight", "frame_processed": processedStates},
             {"model": f"firmaciv:block/watercraft_frame_angled/bolt/straight/{template}",
              "uvlock": True,
              "y": 90})
        ]

    # Bolt for inner and outer
    for shape, rotations in {"inner": inner, "outer": outer}.items():
        for side, rotation in rotations.items():
            for template, processedStates in boltTemplateStates.items():
                json += [
                    ({"facing": "north", "shape": f"{shape}_{side}", "frame_processed": processedStates},
                     {"model": f"firmaciv:block/watercraft_frame_angled/bolt/{shape}/{template}",
                      "uvlock": True,
                      "y": rotation[0]}),
                    ({"facing": "east", "shape": f"{shape}_{side}", "frame_processed": processedStates},
                     {"model": f"firmaciv:block/watercraft_frame_angled/bolt/{shape}/{template}",
                      "uvlock": True,
                      "y": rotation[1]}),
                    ({"facing": "south", "shape": f"{shape}_{side}", "frame_processed": processedStates},
                     {"model": f"firmaciv:block/watercraft_frame_angled/bolt/{shape}/{template}",
                      "uvlock": True,
                      "y": rotation[2]}),
                    ({"facing": "west", "shape": f"{shape}_{side}", "frame_processed": processedStates},
                     {"model": f"firmaciv:block/watercraft_frame_angled/bolt/{shape}/{template}",
                      "uvlock": True,
                      "y": rotation[3]})
                ]

    return json
