# Our Villager Discounts

[![Build](https://github.com/Gremlite/MinecraftMod-OurVillagerDiscounts/actions/workflows/build.yml/badge.svg?branch=forge)](https://github.com/Gremlite/MinecraftMod-OurVillagerDiscounts/actions/workflows/build.yml)

#### A Minecraft mod for sharing villager discounts across players

This branch is concerned with the Forge launcher. Please navigate to the [main
branch](https://github.com/Gremlite/MinecraftMod-OurVillagerDiscounts/tree/main)
for more information.

## Updating

Update the environment variables in the `gradle.properties` file.

## Releasing

To create a Forge release, simply tag the commit of interest as follows:

```sh
git tag v<MINECRAFT_VERSION>+build.<BUILD_VERSION>-forge
```

Then push the tag with:

```sh
git push origin <TAG>
```
