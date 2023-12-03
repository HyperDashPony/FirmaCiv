import mcresources

import assets
import constants


def main():
    resourceManager = mcresources.ResourceManager("firmaciv", "../src/main/resources",
                                                  on_error=lambda file, e: print(f"Error writing {file}\n{e}"))
    print("Starting resource generation.")
    resourceManager.lang(constants.DEFAULT_LANG)
    assets.generate(resourceManager)
    resourceManager.flush()

    print(f"Finished generating files!")
    print(f"New: {resourceManager.new_files}, Modified: {resourceManager.modified_files},"
          f" Unchanged: {resourceManager.unchanged_files}, Errors: {resourceManager.error_files}")


if __name__ == '__main__':
    main()
