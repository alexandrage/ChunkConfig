package cfg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;

import ru.minecraft.nbt.CompressedStreamTools;
import ru.minecraft.nbt.NBTTagCompound;

public class BlockTag {
	private Map<String, String> locs = new HashMap<String, String>();

	private String convert(Location loc) {
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		int tmpx = x >> 4;
		int tmpx2 = tmpx << 4;
		int tmpz = z >> 4;
		int tmpz2 = tmpz << 4;
		return new String(new char[] { (char) (x - tmpx2), (char) y, (char) (z - tmpz2) });
	}

	public void add(Location loc, String player) {
		locs.put(convert(loc), player);
	}

	public void remove(Location loc) {
		locs.remove(convert(loc));
	}

	public boolean contains(Location loc) {
		return locs.containsKey(convert(loc));
	}

	public String get(Location loc) {
		if (contains(loc)) {
			return locs.get(convert(loc));
		}
		return null;
	}

	public void read(String folder, String name) {
		NBTTagCompound nbt = new NBTTagCompound();
		File file = new File(folder, name + ".dat");
		if (file.exists()) {
			try {
				nbt = CompressedStreamTools.readCompressed(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (String n : nbt.func_150296_c()) {
			locs.put(n, nbt.getString(n));
		}
	}

	public void write(String folder, String name) {
		NBTTagCompound nbt = new NBTTagCompound();
		for (Entry<String, String> loc : locs.entrySet()) {
			nbt.setString(loc.getKey(), loc.getValue());
		}
		File file = new File(folder, name + ".dat");
		try {
			if (locs.size() != 0) {
				CompressedStreamTools.writeCompressed(nbt, new FileOutputStream(file));
			} else {
				file.delete();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}