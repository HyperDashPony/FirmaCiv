import mcresources

import Constants
import assets


def main():
    resourceManager = mcresources.ResourceManager("firmaciv", "../src/main/resources",
                                                  on_error=lambda file, e: print(f"Error writing {file}\n{e}"))
    resourceManager.lang(Constants.DEFAULT_LANG)
    assets.generate(resourceManager)
    resourceManager.flush()
    print("Generated stuff!")


if __name__ == '__main__':
    main()
