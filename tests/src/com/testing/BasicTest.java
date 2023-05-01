package com.testing;

import com.szakdoga.game.entities.towers.ArcherTower;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class BasicTest {
    @Test
    public void test(){
        ArcherTower ac = new ArcherTower(1,1,"Archer");
        Assert.assertEquals(10,ac.getPrice());
        Assert.assertEquals(10,ac.getPrice());
    }



}
