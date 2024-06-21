# Feature Implementation Progress

## Machines

| Name               | ModBlock          | CostBlock | Status  |
|--------------------|-------------------|-----------|---------|
| Rancher            | White Glazed      | Gold      | Done    |
| Trader             | Orange Glazed     | Lodestone | Planned |
|                    | Magenta Glazed    |           |         |
|                    | Light Blue Glazed |           |         |
|                    | Yellow Glazed     |           |         |
| Breeder            | Lime Glazed       | Gold      | Done    |
| Transmuter         | Pink Glazed       | Diamond   | Done    |
| Placer             | Gray Glazed       | Diamond   | Done    |
| Anvil              | Light Gray Glazed | Lodestone | Planned |
|                    | Cyan Glazed       |           |         |
| Enchanter          | Purple Glazed     | Lodestone | Planned |
| Cauldron Tap       | Blue Glazed       | Diamond   | Done    |
| Pulverizer         | Brown Glazed      | Gold      | Done    |
|                    | Green Glazed      |           |         |
| Metal Recycler     | Red Glazed        | Diamond   | Done    |
| Netherite Recycler | Black Glazed      | Lodestone | Done    |

### Summary

The primary features of this mod are the dispenser machines added.
You can turn any dispenser into a dispenser machine by adding
its mod block one blocks below, and its cost block immediately under that.

For example, this configuration creates a basic Placer machine.
```
[Dispenser]
[Air]
[Grey Glazed Terracotta]
[Diamond Block]
```

### Machine: Rancher
- Mod Block: White Glazed Terracotta
- Cost Block: Gold Block

When pulsed, milks cow in front of dispenser using a bucket inside the dispenser.

### Machine: Trader
- Mod Block: Orange Glazed Terracotta
- Cost Block: Lodestone

TODO

### Machine: Pulverizer
- Mod Block: Yellow Glazed Terracotta
- Cost Block: Gold Block

When pulsed, uses 1 durability from shovel inside the dispenser and converts a block of gravel in front of the dispenser into sand.
If the shovel is a gold shovel, convert it into red sand instead.

### Machine: Breeder
- Mod Block: Lime Glazed Terracotta
- Cost Block: Gold Block

When pulsed, feeds a random animal in the block in front of the dispenser.

### Machine: Transmuter
- Mod Block: Pink Glazed Terracotta
- Cost Block: Diamond Block

When pulsed, performs one of the following recipes. The first ingredient is a block in-world, the second is an item in the dispenser.

| Block       | Item           | Result Block |
|-------------|----------------|--------------|
| Cobblestone | Fire Charge    | Netherrack   |
| Cobblestone | Skulk Block    | Deepslate    |
| Deepslate   | Fire Charge    | Tuff         |
| Deepslate   | Amethyst Shard | Calcite      |

### Machine: Placer
- Mod Block: Grey Glazed Terracotta
- Cost Block: Diamond Block

When pulsed, places items as blocks.

### Machine: Enchanter
- Mod Block: Purple Glazed Terracotta
- Cost Block: Lodestone

When pulsed, combines two random items inside like an anvil and consumes 75 xp from a nearby player or xp orbs.

### Machine: Cauldron Tap
- Mod Block: Blue Glazed Terracotta
- Cost Block: Diamond Block

When pulsed, buckets the contents of the cauldron in front of it.

### Machine: Autocrafter
- Mod Block: Green Glazed Terracotta
- Cost Block: Lodestone

When pulsed, crafts using ingredients inside of any inventory  on top of 3x3 crafting table blocks above dispenser (top is north), and push items out of dispenser port.

### Machine: Metal Recycler
- Mod Block: Red Glazed Terracotta
- Cost Block: Diamond Block

When pulsed, melts down iron and gold tools and armor into 50% of their original cost in nuggets.

### Machine: Netherite Recycler
- Mod Block: Black Glazed Terracotta
- Cost Block: Lodestone

When pulsed, melts down netherite gear into 4 netherite scraps.

## Tweaks

| Name                         | Status  |
|------------------------------|---------|
| Villager Lv1 Enchanted Books | Planned |


### Tweak: Librarian Villagers

Librarian villagers will only sell level 1 books. This is used in conjunction
with the enchanter, which can combine books at no work cost, to
create books of any magnitude, but at a higher cost.


