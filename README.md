# Our Villager Discounts

[![Build](https://github.com/Gremlite/MinecraftMod-OurVillagerDiscounts/actions/workflows/build.yml/badge.svg?branch=fabric)](https://github.com/Gremlite/MinecraftMod-OurVillagerDiscounts/actions/workflows/build.yml)

#### A Minecraft mod for sharing villager discounts across players

This branch is concerned with the Fabric launcher. Please navigate to the [main
branch](https://github.com/Gremlite/MinecraftMod-OurVillagerDiscounts/tree/main)
for more information.

## Updating

You will need to update the versions of the following in `gradle.properties` (The URL for this info is linked in the
file):

* `minecraftVersion`
* `yarnMappings`
* `fabricVersion`
* `modVersion`

Additionally, you need to specify the appropriate Minecraft version in the `fabric.mod.json` file.

## Releasing

To create a Fabric release, simply tag the commit of interest as follows:

```sh
git tag v<MINECRAFT_VERSION>+build.<BUILD_VERSION>
```

Then push the tag with:

```sh
git push origin <TAG>
```