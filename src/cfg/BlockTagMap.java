package cfg;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Location;

public class BlockTagMap {
	private Map<String, BlockTag> save = new HashMap<String, BlockTag>();
	private Main plugin;

	BlockTagMap(Main plugin) {
		this.plugin = plugin;
	}

	public void add(Location loc, String player) {
		synchronized (this) {
			this.getChunk(loc).add(loc, player);
		}
	}

	public void remove(Location loc) {
		synchronized (this) {
			this.getChunk(loc).remove(loc);
		}
	}

	public boolean contains(Location loc) {
		synchronized (this) {
			return this.getChunk(loc).contains(loc);
		}
	}

	public String get(Location loc) {
		synchronized (this) {
			return this.getChunk(loc).get(loc);
		}
	}

	private BlockTag getChunk(Location loc) {
		int ChunkX = loc.getBlockX() >> 4;
		int ChunkZ = loc.getBlockZ() >> 4;
		String s = ChunkX + "." + ChunkZ;
		if (save.containsKey(s)) {
			return save.get(s);
		} else {
			BlockTag data = new BlockTag();
			data.read(this.plugin.getDataFolder().getAbsolutePath(), s);
			save.put(s, data);
			return save.get(s);
		}
	}

	public void Save() {
		synchronized (this) {
			for (Entry<String, BlockTag> tmp : save.entrySet()) {
				tmp.getValue().write(this.plugin.getDataFolder().getAbsolutePath(), tmp.getKey());
			}
			save.clear();
		}
	}
}