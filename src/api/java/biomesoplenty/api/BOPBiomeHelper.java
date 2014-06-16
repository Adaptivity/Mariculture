package biomesoplenty.api;

import java.util.HashMap;
import java.util.List;

import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.BiomeGenBase;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class BOPBiomeHelper 
{
	public static HashMap<String, BOPBiomeEntry>[] biomeLists = new HashMap[256];
	
	public static void init()
	{
		biomeLists[-1 + 1] = new HashMap();
		biomeLists[0 + 1] = new HashMap();
	}
	
	public static void registerBiome(BOPBiomeEntry biome, int dimID, String name)
	{
		biomeLists[dimID + 1].put(name, biome);
	}
	
	public static void registerBiome(BOPBiomeEntry biome, String name)
	{
		registerBiome(biome, 0, name);
	}
	
	public static BiomeGenBase get(int dimID, String name)
	{
		return biomeLists[dimID + 1].get("biomesoplenty:" + name).biome;
	}
	
	public static BiomeGenBase get(String name)
	{
		return get(0, name);
	}
	
	public static String convertBiomeName(String originalName)
	{
		return StringUtils.remove(StringUtils.uncapitalize(WordUtils.capitalize(originalName)), " ");
	}
	
	public static List<BOPBiomeEntry> getCorrespondingTemperatureTypeList(TemperatureType type)
	{
		return null;
	}
	
	public enum TemperatureType
	{
		HOT, WARM, COOL, ICY;
	}
	
	public static class BOPBiomeEntry extends WeightedRandom.Item
	{
		public BiomeGenBase biome;
		public TemperatureType temperatureType;
		
		public BOPBiomeEntry(BiomeGenBase biome, TemperatureType temperatureType, int weight)
		{
			super(weight);
			this.biome = biome;
			this.temperatureType = temperatureType;
		}
		
		public BOPBiomeEntry(BiomeGenBase biome, int weight)
		{
			this(biome, TemperatureType.WARM, weight);
		}
		
		public void addToCorrespondingTemperatureTypeList()
		{
			BOPBiomeHelper.getCorrespondingTemperatureTypeList(temperatureType).add(this);
		}
	}
}
