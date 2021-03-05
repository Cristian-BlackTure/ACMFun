package io.github.addoncommunity.galactifun.base.aliens;

import io.github.addoncommunity.galactifun.api.universe.world.Alien;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;

import javax.annotation.Nonnull;

public final class TitanAlien extends Alien {
    
    public TitanAlien() {
        super("TITAN", "Titan", EntityType.ILLUSIONER, 32);
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent e) {
        // TODO add drops
        e.getDrops().clear();
    }

    @Override
    protected int getSpawnChance() {
        return 16;
    }

}