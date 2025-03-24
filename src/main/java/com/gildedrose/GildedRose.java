package com.gildedrose;

abstract class ItemUpdater {
    protected Item item;

    public ItemUpdater(Item item) {
        this.item = item;
    }

    public abstract void update();

    protected void decreaseQuality() {
        if (item.quality > 0) {
            item.quality--;
        }
    }
}

class DefaultItemUpdater extends ItemUpdater {
    public DefaultItemUpdater(Item item) {
        super(item);
    }

    @Override
    public void update() {
        decreaseQuality();
        item.sellIn--;
        if (item.sellIn < 0) {
            decreaseQuality();
        }
    }

}

class AgedBrieUpdater extends ItemUpdater {
    public AgedBrieUpdater(Item item) {
        super(item);
    }

    @Override
    public void update() {
        increaseQuality();
        item.sellIn--;
        if (item.sellIn < 0) {
            increaseQuality();
        }
    }

    private void increaseQuality() {
        if (item.quality < 50) {
            item.quality++;
        }
    }
}

class ConjuredItemUpdater extends ItemUpdater {
    public ConjuredItemUpdater(Item item) {
        super(item);
    }

    @Override
    public void update() {
        decreaseQuality();
        decreaseQuality();
        item.sellIn--;
        if (item.sellIn < 0) {
            decreaseQuality();
            decreaseQuality();
        }
    }
}

class BackstagePassesUpdater extends ItemUpdater {
    public BackstagePassesUpdater(Item item) {
        super(item);
    }

    @Override
    public void update() {
        if (item.quality < 50) {
            item.quality++;
            if (item.sellIn < 11 && item.quality < 50) {
                item.quality++;
            }
            if (item.sellIn < 6 && item.quality < 50) {
                item.quality++;
            }
        }
        item.sellIn--;
        if (item.sellIn < 0) {
            item.quality = 0;
        }
    }
}

class SulfurasUpdater extends ItemUpdater {
    public SulfurasUpdater(Item item) {
        super(item);
    }

    @Override
    public void update() {
        // Sulfuras não altera qualidade nem sellIn
    }
}

class ItemUpdaterFactory {
    public static ItemUpdater getUpdater(Item item) {
        switch (item.name) {
            case "Aged Brie":
                return new AgedBrieUpdater(item);
            case "Backstage passes to a TAFKAL80ETC concert":
                return new BackstagePassesUpdater(item);
            case "Sulfuras, Hand of Ragnaros":
                return new SulfurasUpdater(item);
            case "Conjured Mana Cake":
                return new ConjuredItemUpdater(item);
            default:
                return new DefaultItemUpdater(item);
        }
    }
}

public class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            ItemUpdater updater = ItemUpdaterFactory.getUpdater(item);
            updater.update();
        }
    }
}
