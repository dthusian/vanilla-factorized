# Features

## Machines

| Name               | ModBlock          | CostBlock | Status |
|--------------------|-------------------|-----------|--------|
| Rancher            | White Glazed      | Gold      | Done   |
| Trader             | Orange Glazed     | Lodestone | Done   |
|                    | Magenta Glazed    |           |        |
|                    | Light Blue Glazed |           |        |
| Placer             | Yellow Glazed     | Diamond   | Done   |
| Breeder            | Lime Glazed       | Gold      | Done   |
| Transmuter         | Pink Glazed       | Diamond   | Done   |
|                    | Gray Glazed       |           |        |
|                    | Light Gray Glazed |           |        |
|                    | Cyan Glazed       |           |        |
|                    | Purple Glazed     |           |        |
| Cauldron Tap       | Blue Glazed       | Diamond   | Done   |
| Pulverizer         | Brown Glazed      | Gold      | Done   |
|                    | Green Glazed      |           |        |
| Metal Recycler     | Red Glazed        | Diamond   | Done   |
| Netherite Recycler | Black Glazed      | Lodestone | Done   |

## Summary

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
Notice that the block between the dispenser and terracotta doesn't have to be
air, it can be any block.

### Machine: Rancher
- Mod Block: White Glazed Terracotta
- Cost Block: Gold Block

When pulsed, milks cow in front of dispenser using a bucket inside the dispenser.

### Machine: Trader
- Mod Block: Orange Glazed Terracotta
- Cost Block: Lodestone

When pulsed, tries to execute a trade with the items in the dispenser.
A name tag in the dispenser configures the trader. Its format is as follows,
where `<>` denotes a required parameter and `[]` denotes an optional parameter:
```
<trade id>[,<max cost>]
```
- `trade id`: 0-indexed integer indicating which trade to make (i.e. 0 = first trade, 1 = second trade, etc.).
- `max cost`: Maximum cost to execute the trade at. If the cost exceeds this, the trade will not be performed.

Villager and player XP are not awarded. 

### Machine: Pulverizer
- Mod Block: Yellow Glazed Terracotta
- Cost Block: Gold Block

When pulsed, uses 1 durability from shovel inside the dispenser and performs one of the following conversions:

| Input       | Output   | Golden Shovel? |
|-------------|----------|----------------|
| Cobblestone | Gravel   | -              |
| Gravel      | Sand     | No             |
| Gravel      | Red Sand | Yes            |
| Coarse Dirt | Dirt     | -              |

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

### Machine: Cauldron Tap
- Mod Block: Blue Glazed Terracotta
- Cost Block: Diamond Block

When pulsed, buckets the contents of the cauldron in front of it.
Water, lava, and powder snow can be collected.

### Machine: Metal Recycler
- Mod Block: Red Glazed Terracotta
- Cost Block: Diamond Block

When pulsed, melts down iron and gold tools and armor into 50% of their original cost in nuggets.

### Machine: Netherite Recycler
- Mod Block: Black Glazed Terracotta
- Cost Block: Lodestone

When pulsed, melts down netherite gear into 4 netherite scraps.
