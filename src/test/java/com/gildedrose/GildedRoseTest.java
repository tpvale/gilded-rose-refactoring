package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class GildedRoseTest {

    private void executeUpdateAndAssert(Item item, int expectedSellIn, int expectedQuality) {
        GildedRose app = new GildedRose(new Item[]{item});
        app.updateQuality();
        assertEquals(expectedSellIn, item.sellIn, "SellIn incorreto");
        assertEquals(expectedQuality, item.quality, "Quality incorreto");
    }

    @Test
    void textNormalItemBeforeSellIn() {
        executeUpdateAndAssert(new Item("+5 Dexterity Vest", 10, 20), 9, 19);
    }

    @Test
    void testNormalItemAfterSellIn() {
        executeUpdateAndAssert(new Item("+5 Dexterity Vest", 0, 20), -1, 18);
    }

    @Test
    void testAgedBrieIncreasesQuality() {
        executeUpdateAndAssert(new Item("Aged Brie", 2, 0), 1, 1);
    }

    @Test
    void testAgedBrieIncreasesTwiceAfterSellIn() {
        executeUpdateAndAssert(new Item("Aged Brie", 0, 10), -1, 12);
    }

    @Test
    void testBackstagePassesIncreaseQuality() {
        executeUpdateAndAssert(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20), 14, 21);
    }

    @Test
    void testBackstagePassesIncreaseTwice() {
        executeUpdateAndAssert(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20), 9, 22);
    }

    @Test
    void testBackstagePassesIncreaseThrice() {
        executeUpdateAndAssert(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20), 4, 23 );
    }

    @Test
    void testBackstagePassesDropToZero() {
        executeUpdateAndAssert(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20), -1, 0);
    }

    @Test
    void testSulfurasNeverChanges() {
        executeUpdateAndAssert(new Item("Sulfuras, Hand of Ragnaros", 0, 80), 0, 80);
    }

    @Test
    void testConjuredItem() {
        executeUpdateAndAssert(new Item("Conjured Mana Cake", 3, 6), 2, 4);
    }
}
