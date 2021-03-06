package mariculture.fishery.fish;

import static mariculture.core.lib.MCLib.dropletFlux;
import static mariculture.core.lib.MCLib.dropletMagnesium;
import static mariculture.core.lib.MCLib.dropletPoison;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;

public class FishMagnesium extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 8;
    }

    @Override
    public int getTemperatureTolerance() {
        return 3;
    }

    @Override
    public Salinity getSalinityBase() {
        return Salinity.BRACKISH;
    }

    @Override
    public int getSalinityTolerance() {
        return 1;
    }

    @Override
    public boolean isDominant() {
        return true;
    }

    @Override
    public int getLifeSpan() {
        return 3;
    }

    @Override
    public int getFertility() {
        return 235;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletMagnesium, 12.5D);
        addProduct(dropletPoison, 8.5D);
        addProduct(dropletFlux, 5D);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }
}
