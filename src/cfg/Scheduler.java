package cfg;

import org.bukkit.scheduler.BukkitRunnable;

public class Scheduler extends BukkitRunnable {
	private BlockTagMap saves;

	Scheduler(BlockTagMap saves) {
		this.saves = saves;
	}

	@Override
	public void run() {
		this.saves.Save();
	}
}