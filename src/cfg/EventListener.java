package cfg;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.ItemSpawnEvent;

public class EventListener implements Listener {
	private BlockTagMap saves;

	public EventListener(BlockTagMap saves) {
		this.saves = saves;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void on(ItemSpawnEvent e) {
		Location loc = e.getLocation();
		if (this.saves.contains(loc)) {
			this.saves.remove(loc);
			e.getEntity().remove();
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void on(EntityChangeBlockEvent e) {
		Location loc = e.getBlock().getLocation();
		if (this.saves.contains(loc)) {
			this.saves.remove(loc);
			e.getEntity().remove();
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void on(BlockPistonExtendEvent e) {
		for (Block block : e.getBlocks()) {
			Location loc = block.getLocation();
			if (this.saves.contains(loc)) {
				e.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void on(BlockPistonRetractEvent e) {
		for (Block block : e.getBlocks()) {
			Location loc = block.getLocation();
			if (this.saves.contains(loc)) {
				e.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void on(BlockPlaceEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			Location loc = e.getBlockPlaced().getLocation();
			this.saves.add(loc, e.getPlayer().getName());
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void on(BlockBreakEvent e) {
		Location loc = e.getBlock().getLocation();
		if (this.saves.contains(loc)) {
			this.saves.remove(loc);
			e.getBlock().setType(Material.AIR);
		}
	}
}